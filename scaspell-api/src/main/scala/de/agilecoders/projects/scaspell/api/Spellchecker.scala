package de.agilecoders.projects.scaspell.api

import com.twitter.util.Future

/**
 * TODO miha: document class purpose
 *
 * @author miha
 */
trait Spellchecker {

    /**
     * Lists available modes
     *
     * @return
     */
    def availableModes(): Future[Map[String, String]]

    /**
     * Lists available dictionaries
     *
     * @param filter
     * @return
     */
    def availableLanguages(filter: Option[String] = None): Future[Seq[String]]

    /**
     * Print version number of Aspell Library and Utility
     *
     * @return
     */
    def version(): Future[String]

    /**
     * Produce a list of misspelled words
     *
     * @param input
     * @param mode
     * @param lang
     * @param limit
     * @return
     */
    def check(input: String,
              mode: Option[String],
              lang: Option[String] = None,
              limit: Option[Int] = None): Future[Map[String, Seq[String]]]

    /**
     * Produce a list of misspelled words
     *
     * @param words
     * @param lang
     * @param limit
     * @return
     */
    def checkWords(words: Seq[String], lang: Option[String] = None, limit: Option[Int] = None): Future[Map[String, Seq[String]]]

    /**
     * Output the soundslike equivalent of each word entered.
     *
     * @param words
     * @param lang
     * @param limit
     * @return
     */
    def soundslike(words: Seq[String], lang: Option[String] = None, limit: Option[Int] = None): Future[Map[String, Seq[String]]] = ???

    /**
     * Generate possible root words and affixes from an input list of words.
     *
     * @param words
     * @param lang
     * @param limit
     * @return
     */
    def munch(words: Seq[String], lang: Option[String] = None, limit: Option[Int] = None): Future[Map[String, Seq[String]]] = ???

    /**
     * Produce a list of misspelled words
     *
     * @param html
     * @param lang
     * @param limit
     * @return
     */
    def checkHtml(html: String, lang: Option[String] = None, limit: Option[Int] = None): Future[Map[String, Seq[String]]]
}
