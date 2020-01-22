package com.rednavis.micronaut.petstore.util

import io.reactivex.Maybe

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-22
 */
fun <T> T?.toMaybe(): Maybe<T> = when (this) {
    null -> Maybe.empty<T>()
    else -> Maybe.just(this)
}
