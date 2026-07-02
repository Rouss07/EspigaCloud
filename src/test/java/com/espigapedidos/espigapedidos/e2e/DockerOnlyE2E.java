package com.espigapedidos.espigapedidos.e2e;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Tag("e2e")
@Target(TYPE)
@Retention(RUNTIME)
@interface DockerOnlyE2E {
}
