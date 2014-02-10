package de.agilecoders.projects.scaspell

import com.twitter.finagle.http._
import java.net.InetSocketAddress
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.Service
import com.twitter.finagle.http.RichHttp

/**
 * TODO miha: document class purpose
 *
 * @author miha
 */
object Server extends App {
    /*val routing = RequestAwareRoutingService.byRequest { request =>
        (request., Path(request.path)) match {
            case Method.Get -> Root / "api1" / Integer(userId) => myShowService
            case Method.Post -> Root / "api2" / Integer(userId) => new SearchService(userId)
        }
    }        */

    val httpServer = ServerBuilder()
                     .codec(RichHttp[Request](Http()))
                     .bindTo(new InetSocketAddress(8080))
                     .name("scaspell-service")
                     .build(AspellService())
}

object RequestAwareRoutingService {
    def byRequest[REQUEST](routes: PartialFunction[Request, Service[REQUEST, Response]]) =
        new RoutingService(
            new PartialFunction[Request, Service[REQUEST, Response]] {
                def apply(request: Request) = routes(request)

                def isDefinedAt(request: Request) = routes.isDefinedAt(request)
            })
}

