package de.agilecoders.projects.scaspell

import com.twitter.finagle.http._
import java.net.InetSocketAddress
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.http.path._
import de.agilecoders.projects.scaspell.api.Aspell
import de.agilecoders.projects.scaspell.util.RequestAwareRoutingService
import de.agilecoders.projects.scaspell.service._
import com.twitter.finagle.http.path./
import com.twitter.finagle.http.RichHttp

/**
 * TODO miha: document class purpose
 *
 * @author miha
 */
object Server extends App {
    private lazy val root = Root / "api" / "v1"
    private lazy val spellchecker = Aspell()
    private lazy val getMethodService = GetModesService(spellchecker)
    private lazy val getLanguageService = GetLanguagesService(spellchecker)
    private lazy val getVersionService = GetVersionService(spellchecker)
    private lazy val checkService = CheckService(spellchecker)
    private lazy val badRequest = BadRequestService()

    val routing: RoutingService[Request] = RequestAwareRoutingService.byRequest[Request] {
        case r: Request => (r.getMethod(), Path(r.path)) match {
            case Method.Get -> `root` / "mode" => getMethodService
            case Method.Get -> `root` / "language" => getLanguageService
            case Method.Get -> `root` / "version" => getVersionService
            case Method.Post -> `root` / "check" => checkService
            case Method.Get -> `root` / "check" => checkService
            case _ => badRequest
        }
        case _ => badRequest
    }

    val httpServer = ServerBuilder()
                     .codec(RichHttp[Request](Http()))
                     .bindTo(new InetSocketAddress(8080))
                     .name("scaspell-service")
                     .build(routing)
}




