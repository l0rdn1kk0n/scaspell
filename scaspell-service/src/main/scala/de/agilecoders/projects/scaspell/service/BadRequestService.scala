package de.agilecoders.projects.scaspell.service

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future

/**
 * TODO miha: document class purpose
 *
 * @author miha
 */
case class BadRequestService() extends Service[Request, Response] {


    def apply(request: Request): Future[Response] = Future {
        val res = Response()
        res.setStatusCode(402)

        res
    }
}
