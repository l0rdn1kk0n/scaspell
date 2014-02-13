package de.agilecoders.projects.scaspell.service

import de.agilecoders.projects.scaspell.api.Spellchecker
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Response, Request}

/**
 * TODO miha: document class purpose
 *
 * @author miha
 */
case class GetLanguagesService(spellchecker: Spellchecker) extends Service[Request, Response] {

    import spray.json._
    import DefaultJsonProtocol._

    override def apply(req: Request) = spellchecker.availableLanguages(req.params.get("filter")) map {
        modes =>
            val res = Response()
            res.setContentString(modes.toJson.compactPrint)
            res.setContentType("application/json")
            res.setStatusCode(200)

            res
    }
}
