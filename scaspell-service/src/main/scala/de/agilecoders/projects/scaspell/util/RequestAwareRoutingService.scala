package de.agilecoders.projects.scaspell.util

import com.twitter.finagle.http.{Response, Request}
import com.twitter.finagle.Service
import com.twitter.finagle.http.service.RoutingService

/**
 * TODO miha: document class purpose
 *
 * @author miha
 */
object RequestAwareRoutingService {
    def byRequest[REQUEST <: Request](routes: PartialFunction[Request, Service[REQUEST, Response]]) =
        new RoutingService(
            new PartialFunction[Request, Service[REQUEST, Response]] {
                def apply(request: Request) = routes(request)

                def isDefinedAt(request: Request) = routes.isDefinedAt(request)
            })
}
