package de.agilecoders.projects.scaspell.api

import com.twitter.util.{Await, Future}

/**
 * TODO miha: document class purpose
 *
 * @author miha
 */
object Aspell {

}

case class Aspell() extends Spellchecker {

    import scala.sys.process._

    private lazy val _availableLanguages: Seq[String] = {
        Await.result(availableLanguages())
    }

    def availableLanguages(filter: Option[String] = None): Future[Seq[String]] = Future {
        val languages = Process("aspell dump dicts").lines.map(_.trim)

        filter.map {
            case "main" =>
                languages.filter(!_.contains("-"))
            case _ => languages
        }.getOrElse(languages)
    }

    def checkWords(words: Seq[String], lang: Option[String] = None, limit: Option[Int] = None): Future[Map[String, Seq[String]]] = Future {
        exec(lang, limit) {
            Strings.toWordsList(words)
        }
    }

    private lazy val resultRegEx = """&\s([^\s]*)\s(\d*)\s(\d*):(.*)""".r

    private def parse(result: String, limit: Int): Option[(String, Seq[String])] = {
        resultRegEx.findAllIn(result).matchData.map(m => {
            (m.group(1), m.group(4).split(',').map(_.trim).take(limit).toSeq)
        }).toSeq.headOption
    }

    def checkHtml(html: String, lang: Option[String] = None, limit: Option[Int] = None): Future[Map[String, Seq[String]]] = Future {
        exec(lang, limit) {
            Strings.toHtml(html)
        }
    }

    private def exec(lang: Option[String] = None, limit: Option[Int] = None)(f: => String): Map[String, Seq[String]] = {
        val l = Strings.toLanguageParameter(lang.filter(_availableLanguages.contains))
        val echo = s"echo $f"
        val aspell = s"aspell pipe --encoding=utf-8 $l"
        val grep = """grep -v "\*|\@|^$""""

        val results = (echo #| aspell #| grep #| "uniq").lines_!

        results.filter(r => !r.isEmpty && r(0) == '&')
        results.map(parse(_, limit.getOrElse(25))).filter(_.isDefined).map(_.get).toMap
    }
}

object Strings {

    def toWordsList(words: Seq[String]): String = {
        words.map(_.replaceAll(",", " ").replaceAll("|", " ").trim).filter(!_.isEmpty).mkString(",")
    }

    def toHtml(html: String): String = {
        html.replaceAll("|", " ")
    }

    def toLanguageParameter(lang: Option[String]): String = {
        lang.filter(!_.isEmpty).map(s => "--lang=" + s).getOrElse("")
    }


}