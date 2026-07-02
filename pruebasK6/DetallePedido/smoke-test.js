import http from 'k6/http';
import { check, sleep, group } from 'k6';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';
const PEDIDO_ID = __ENV.PEDIDO_ID || '1';
const PRODUCTO_ID = __ENV.PRODUCTO_ID || '1';

export const options = {
    vus: 20,
    duration: '20s',
    thresholds: {
        http_req_duration: ['p(95)<5000'],
        http_req_failed: ['rate<0.2'],
    },
};

// Misma función setup que load-test
export function setup() {
    const loginPage = http.get(`${BASE_URL}/login`);
    const csrfMatch = loginPage.body.match(/name="_csrf"[^>]*value="([^"]+)"/);
    const csrfToken = csrfMatch ? csrfMatch[1] : null;
    const cookies = loginPage.headers['Set-Cookie'];
    const jsessionid = cookies ? cookies.match(/JSESSIONID=([^;]+)/)[1] : null;

    const loginRes = http.post(`${BASE_URL}/login`, {
        username: 'admin',
        password: __ENV.ADMIN_PASSWORD || '1234',
        _csrf: csrfToken
    }, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Cookie': `JSESSIONID=${jsessionid}`
        },
        redirects: 0
    });

    const authCookie = loginRes.headers['Set-Cookie']
        ? loginRes.headers['Set-Cookie'].match(/JSESSIONID=([^;]+)/)[1]
        : jsessionid;

    const sessionCookie = `JSESSIONID=${authCookie}`;
    const formHeaders = {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Cookie': sessionCookie
    };

    const obtenerCsrf = (ruta) => {
        const response = http.get(`${BASE_URL}${ruta}`, {
            headers: { 'Cookie': sessionCookie }
        });
        const match = response.body.match(/name="_csrf"[^>]*value="([^"]+)"/);
        return { response, token: match ? match[1] : null };
    };

    // El entorno CI usa una base temporal; crear las relaciones que requiere el detalle.
    const tiendaForm = obtenerCsrf('/tiendas/nuevo');
    if (tiendaForm.response.status !== 200 || !tiendaForm.token) {
        return { authenticated: false };
    }
    http.post(`${BASE_URL}/tiendas/guardar`, {
        nombre: 'Tienda K6',
        direccion: 'Av. Pruebas 123',
        telefono: '999888777',
        estado: 'Activo',
        _csrf: tiendaForm.token
    }, { headers: formHeaders, redirects: 0 });

    const productoForm = obtenerCsrf('/productos/nuevo');
    if (productoForm.response.status !== 200 || !productoForm.token) {
        return { authenticated: false };
    }
    http.post(`${BASE_URL}/productos/guardar`, {
        nombre: 'Producto K6',
        categoria: 'Pruebas',
        precio: '5.50',
        stock: '10000',
        _csrf: productoForm.token
    }, { headers: formHeaders, redirects: 0 });

    const pedidoForm = obtenerCsrf('/pedidos/nuevo');
    if (pedidoForm.response.status !== 200 || !pedidoForm.token) {
        return { authenticated: false };
    }
    http.post(`${BASE_URL}/pedidos/guardar`, {
        fecha: new Date().toISOString().slice(0, 10),
        estado: 'Pendiente',
        tienda: '1',
        _csrf: pedidoForm.token
    }, { headers: formHeaders, redirects: 0 });

    return {
        sessionCookie,
        authenticated: true
    };
}

export default function (data) {
    if (!data.authenticated) return;

    const headers = {
        'Cookie': data.sessionCookie,
        'Content-Type': 'application/x-www-form-urlencoded'
    };

    const operation = Math.random();

    group('DetallePedido - Smoke Test', () => {

        if (operation < 0.4) {
            // Listar detalles
            let listRes = http.get(`${BASE_URL}/detalle-pedido/${PEDIDO_ID}`, {
                headers: { 'Cookie': data.sessionCookie }
            });
            check(listRes, { 'GET lista - 200': (r) => r.status === 200 });

        } else if (operation < 0.7) {
            // Ver formulario
            let formRes = http.get(`${BASE_URL}/detalle-pedido/nuevo/${PEDIDO_ID}`, {
                headers: { 'Cookie': data.sessionCookie }
            });
            check(formRes, { 'GET form - 200': (r) => r.status === 200 });

        } else {
            // Crear detalle (necesita CSRF fresco)
            let formRes = http.get(`${BASE_URL}/detalle-pedido/nuevo/${PEDIDO_ID}`, {
                headers: { 'Cookie': data.sessionCookie }
            });
            const csrfMatch = formRes.body.match(/name="_csrf"[^>]*value="([^"]+)"/);
            const csrf = csrfMatch ? csrfMatch[1] : '';

            const formData = {
                cantidad: Math.floor(Math.random() * 5) + 1,
                precioUnitario: (Math.random() * 50 + 10).toFixed(2),
                pedidoId: PEDIDO_ID,
                productoId: PRODUCTO_ID,
                _csrf: csrf
            };

            let createRes = http.post(`${BASE_URL}/detalle-pedido/guardar`, formData, {
                headers: headers
            });
            check(createRes, { 'POST - ok': (r) => r.status === 302 || r.status === 200 });
        }

        sleep(Math.random() * 2 + 0.5);
    });
}
