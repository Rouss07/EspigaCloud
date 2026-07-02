const { test, expect } = require('@playwright/test');

const adminPassword = process.env.ADMIN_PASSWORD || 'Admin123*';

async function ensureAdmin(page) {
  await page.goto('/setup-admin');
  await expect(page.locator('body')).toContainText(/admin/i);
}

async function loginAsAdmin(page) {
  await page.goto('/login');
  await page.locator('[name="username"]').fill('admin');
  await page.locator('[name="password"]').fill(adminPassword);
  await page.getByRole('button', { name: 'Ingresar' }).click();
  await expect(page).toHaveURL(/\/$/);
}

test('login y dashboard principal funcionan', async ({ page }) => {
  await ensureAdmin(page);
  await loginAsAdmin(page);
  await expect(page.locator('body')).toContainText(/EspigaPedidos|Productos|Pedidos/i);
});

test('validacion HTML5 impide guardar producto sin parametros requeridos', async ({ page }) => {
  await ensureAdmin(page);
  await loginAsAdmin(page);
  await page.goto('/productos/nuevo');
  const nombre = page.locator('[name="nombre"]');
  const categoria = page.locator('[name="categoria"]');
  const precio = page.locator('[name="precio"]');
  const stock = page.locator('[name="stock"]');

  await expect(nombre).toHaveAttribute('required', '');
  await expect(categoria).toHaveAttribute('required', '');
  await expect(precio).toHaveAttribute('required', '');
  await expect(stock).toHaveAttribute('required', '');
  await expect(precio).toHaveAttribute('min', '0.01');
  await expect(stock).toHaveAttribute('min', '1');
});

test('producto rechaza precio cero, negativo y letras', async ({ page }) => {
  await ensureAdmin(page);
  await loginAsAdmin(page);
  await page.goto('/productos/nuevo');

  const precio = page.locator('[name="precio"]');
  await precio.fill('0');
  await expect(precio).not.toBeValid();
  await precio.fill('-1');
  await expect(precio).not.toBeValid();
  await precio.fill('abc');
  await expect(precio).toHaveValue('');
});

test('stock y cantidad solo aceptan enteros positivos', async ({ page }) => {
  await ensureAdmin(page);
  await loginAsAdmin(page);
  await page.goto('/productos/nuevo');

  const stock = page.locator('[name="stock"]');
  await stock.fill('0');
  await expect(stock).not.toBeValid();
  await stock.fill('-2');
  await expect(stock).not.toBeValid();
  await stock.fill('1.5');
  await expect(stock).not.toBeValid();
});

test('flujo basico de producto crea y lista un registro', async ({ page }) => {
  await ensureAdmin(page);
  await loginAsAdmin(page);
  const suffix = Date.now().toString();
  const productName = `Pan QA ${suffix}`;

  await page.goto('/productos/nuevo');
  await page.locator('[name="nombre"]').fill(productName);
  await page.locator('[name="categoria"]').fill('QA');
  await page.locator('[name="precio"]').fill('5.50');
  await page.locator('[name="stock"]').fill('12');
  await page.getByRole('button', { name: 'Guardar' }).click();

  await expect(page).toHaveURL(/\/productos$/);
  await expect(page.locator('body')).toContainText(productName);
});
