package models

import play.api.libs.json._
//import scala.slick.driver.PostgresDriver.simple._
import scala.slick.driver.H2Driver.simple._
import play.Logger

/**
 * books
 *
 * User: Alexandros Bantis
 * Date: 8/13/13
 * Time: 12:04 PM
 */

case class Book(bookId: Option[Int], title: String, author: String, released: Int, coverImage: String)

case class putBook(title: String, author: String, released: Int, coverImage: String)

object Books extends Table[Book]("books") {
  def bookId = column[Int]("book_id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def author = column[String]("author")
  def released = column[Int]("released")
  def coverImage = column[String]("cover_image")
  def * = bookId.? ~ title ~ author ~ released ~ coverImage <> (Book, Book.unapply _)
  def autoInc = title ~ author ~ released ~ coverImage returning bookId

  def selectAll()(implicit s: Session): Seq[Book] = (for (b <- Books) yield b).to[Seq]

  def selectBook(id: Int)(implicit s: Session): Option[Book] =
    Query(Books).where(_.bookId === id).firstOption

  def insertBook(nb: putBook)(implicit s: Session): Int = {
//    val q = Books.insertStatementFor(forInsert.returning(Books.bookId).insert(newBook))
//    Logger.debug(q)
//    val bookId: Int = Books.forInsert.returning(Books.bookId).insert(newBook)
    // http://stackoverflow.com/questions/17634152/scala-play-slick-postgresql-auto-increment
    val bookId: Int = Books.autoInc.insert(nb.title, nb.author, nb.released, nb.coverImage)
    bookId
  }

  def updateBook(id: Int, book: Book)(implicit s: Session) {
    Books.where(_.bookId === book.bookId).update(book)
  }

  def deleteBook(id: Int)(implicit s: Session) {
    Books.where(_.bookId === id).delete
  }

  def countBooks()(implicit s: Session) = Query(Books.length).first

  implicit val bookFormat = Format(Json.reads[Book], Json.writes[Book])
  implicit val putBookFormat = Format(Json.reads[putBook], Json.writes[putBook])
}


case class Tag(tagId: Option[Int], name: String)

object Tags extends Table[Tag]("tags") {
  def id = column[Int]("tag_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def * = id.? ~ name <> (Tag, Tag.unapply _)
  def autoInc = name returning id

  implicit val tagFormat = Format(Json.reads[Tag], Json.writes[Tag])

}

case class BookTag(bId: Option[Int], tId: Option[Int])

object BookTags extends Table[BookTag]("book_tags") {
  def bId = column[Int]("b_id")
  def tId = column[Int]("t_id")
  def * = bId.? ~ tId.? <> (BookTag, BookTag.unapply _)
  def pk = primaryKey("pk_a", (bId, tId))
  def book = foreignKey("b_id", bId, Books)(_.bookId)
  def tag = foreignKey("t_id", tId, Tags)(_.id)


  implicit val bookTagFormat = Format(Json.reads[BookTag], Json.writes[BookTag])
}