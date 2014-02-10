package de.agilecoders.projects.scaspell.api

import com.twitter.util.Future

/**
 * TODO miha: document class purpose
 *
 * @author miha
 */
trait Spellchecker {

    def availableLanguages(filter: Option[String] = None): Future[Seq[String]]

    def checkWords(words: Seq[String], lang: Option[String] = None, limit: Option[Int] = None): Future[Map[String, Seq[String]]]

    def checkHtml(html: String, lang: Option[String] = None, limit: Option[Int] = None): Future[Map[String, Seq[String]]]
}
