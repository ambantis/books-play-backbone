package models

import play.api.db.slick.Config.driver.simple._
import play.api.libs.json._

/**
 * books
 *
 * User: Alexandros Bantis
 * Date: 8/13/13
 * Time: 12:04 PM
 */

case class Book(id: Int, title: String, author: String, releaseDate: Int,
  keywords: String, coverImage: String)

object Books extends Table[Book]("BOOK") {
  def id = column[Int]("ID", O.PrimaryKey)
  def title = column[String]("TITLE")
  def author = column[String]("AUTHOR")
  def released = column[Int]("RELEASE_DATE")
  def keywords = column[String]("KEYWORDS")
  def coverImage = column[String]("COVER_IMAGE")
  def * = id ~ title ~ author ~ released ~ keywords ~ coverImage <> (Book, Book.unapply _)

  def selectAll()(implicit s: Session) = Query(Books).list()

  def selectBook(id: Int)(implicit s: Session): Option[Book] = Query(Books).where(_.id === id).list().headOption

  def insertBook(newBook: Book)(implicit s: Session) {
    Books.insert(newBook)
  }

  def updateBook(id: Int, book: Book)(implicit s: Session) {
    val bookToUpdate = Book(book.id, book.title, book.author, book.releaseDate, book.keywords, book.coverImage)
    Books.where(_.id === book.id).update(book)
  }

  def deleteBook(id: Int)(implicit s: Session) {
    Books.where(_.id === id).delete
  }

  def countBooks()(implicit s: Session) = Query(Books.length).first

  implicit val bookFormat = Format(Json.reads[Book], Json.writes[Book])
}






