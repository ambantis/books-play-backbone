package models

import play.api.libs.json._
import scala.slick.driver.PostgresDriver.simple._
import play.Logger

/**
 * books
 *
 * User: Alexandros Bantis
 * Date: 8/13/13
 * Time: 12:04 PM
 */
case class putBook(title: String, author: String, released: Int, keywords: String, coverImage: String)

case class Book(id: Option[Int], title: String, author: String, released: Int,
  keywords: String, coverImage: String)

object Books extends Table[Book]("books") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def author = column[String]("author")
  def released = column[Int]("released")
  def keywords = column[String]("keywords")
  def coverImage = column[String]("cover_image")
  def * = id.? ~ title ~ author ~ released ~ keywords ~ coverImage <> (Book, Book.unapply _)
  def autoInc = title ~ author ~ released ~ keywords ~ coverImage returning id

  def selectAll()(implicit s: Session): Seq[Book] = (for (b <- Books) yield b).to[Seq]

  def selectBook(id: Int)(implicit s: Session): Option[Book] =
    Query(Books).where(_.id === id).firstOption

  def insertBook(nb: putBook)(implicit s: Session): Int = {
//    val q = Books.insertStatementFor(forInsert.returning(Books.id).insert(newBook))
//    Logger.debug(q)
//    val bookId: Int = Books.forInsert.returning(Books.id).insert(newBook)
    // http://stackoverflow.com/questions/17634152/scala-play-slick-postgresql-auto-increment
    val bookId: Int = Books.autoInc.insert(nb.title, nb.author, nb.released, nb.keywords, nb.coverImage)
    bookId
  }

  def updateBook(id: Int, book: Book)(implicit s: Session) {
    Books.where(_.id === book.id).update(book)
  }

  def deleteBook(id: Int)(implicit s: Session) {
    Books.where(_.id === id).delete
  }

  def countBooks()(implicit s: Session) = Query(Books.length).first

  implicit val bookFormat = Format(Json.reads[Book], Json.writes[Book])
  implicit val putBookFormat = Format(Json.reads[putBook], Json.writes[putBook])
}
