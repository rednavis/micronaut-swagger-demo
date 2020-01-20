package com.rednavis.micronaut.petclinic

import io.micronaut.runtime.Micronaut

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-20
 */
object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.run(Application.javaClass)
    }
}
