package com.rednavis.micronaut.petstore

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-20
 */
@OpenAPIDefinition(
    servers = [Server(
        url = "http://localhost:8080"
    )],
    info = Info(
        title = "Swagger Petstore",
        version = "0.0",
        description = "Example of implementation of [http://petstore.swagger.io/](http://petstore.swagger.io/) in Kotlin/Micronaut"
    )
)
object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.run(Application.javaClass)
    }
}
