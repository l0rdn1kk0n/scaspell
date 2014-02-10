package de.agilecoders.projects.scaspell

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Response, Request}
import de.agilecoders.projects.scaspell.api.Aspell
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.HttpMethod
import de.agilecoders.projects.scaspell.util.Params
import spray.json._
import DefaultJsonProtocol._

/**
 * TODO miha: document class purpose
 *
 * @author miha
 */
case class AspellService() extends Service[Request, Response] {

    import scala.collection.JavaConversions._

    private lazy val aspell = Aspell()

    def apply(req: Request): Future[Response] = {
        val res = Response()
        res.setStatusCode(404)

        req.getMethod() match {
            case HttpMethod.GET => req.getUri() match {
                case uri: String if uri.startsWith("/words") =>
                    val params = Params(req.getUri())

                    req.getParams("q").toSeq match {
                        case Some(w: Seq[String]) => aspell.checkWords(w, params.get("lang"), params.getInt("limit")) map {
                            words =>
                                val content = words.toJson.compactPrint
                                res.setContentString(content)
                                res.setContentType("application/json")
                                res.setStatusCode(200)

                                res
                        }
                        case _ => Future.value(res)
                    }
                case uri: String if uri.startsWith("/html") =>
                    val params = Params(req.getUri())

                    params.get("q") match {
                        case Some(html) => aspell.checkHtml(html, params.get("lang"), params.getInt("limit")) map {
                            words =>
                                val content = words.toJson.compactPrint
                                res.setContentString(content)
                                res.setContentType("application/json")
                                res.setStatusCode(200)

                                res
                        }
                        case _ => Future.value(res)
                    }
                case uri: String if uri.startsWith("/availableLanguages") =>
                    val params = Params(req.getUri())

                    aspell.availableLanguages(params.get("filter")) map {
                        languages =>
                            val content = languages.toJson.compactPrint
                            res.setContentString(content)
                            res.setContentType("application/json")
                            res.setStatusCode(200)

                            res
                    }
                case _ => Future.value(res)
            }
            case _ => Future.value(res)
        }
    }
}


