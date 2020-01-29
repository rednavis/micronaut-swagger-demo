package com.rednavis.micronaut.petstore.exceptions

import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-22
 */
@Produces
@Singleton
@Requirements(
    Requires(classes = [ExceptionHandler::class, NotFoundException::class])
)
class NotFoundExceptionHandler : ExceptionHandler<NotFoundException, HttpResponse<*>> {
    override fun handle(request: HttpRequest<*>, exception: NotFoundException): HttpResponse<*> =
        HttpResponse.notFound(exception.message).contentType(MediaType.TEXT_PLAIN_TYPE)
}
