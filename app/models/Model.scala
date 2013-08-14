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

object Books extends Table[Book]("BOOKS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def title = column[String]("TITLE")
  def author = column[String]("AUTHOR")
  def released = column[Int]("RELEASE_DATE")
  def keywords = column[String]("KEYWORDS")
  def coverImage = column[String]("COVER_IMAGE")
  def * = id ~ title ~ author ~ released ~ keywords ~ coverImage <> (Book, Book.unapply _)
//  def forInsert = title ~ author ~ released ~ keywords ~ coverImage <> (
//      { t => Book(None, t._1, t._2, t._3, t._4, t._5)},
//      { (b: Book) => Some((b.title, b.author, b.releaseDate, b.keywords, b.coverImage))})



  def selectAll()(implicit s: Session) = Query(Books).list()

  def selectBook(id: Int)(implicit s: Session): Option[Book] =
    Query(Books).where(_.id === id).firstOption

  def insertBook(newBook: Book)(implicit s: Session): Int = {
//    val bookId: Int = Books.forInsert.returning(Books.id).insert(newBook)
//    bookId
    1
  }

  def updateBook(id: Int, book: Book)(implicit s: Session) {
    Books.where(_.id === book.id).update(book)
  }

  def deleteBook(id: Int)(implicit s: Session) {
    Books.where(_.id === id).delete
  }

  def countBooks()(implicit s: Session) = Query(Books.length).first

  implicit val bookFormat = Format(Json.reads[Book], Json.writes[Book])
}

case class User(email: String, password: String)

object Users extends Table[User]("USERS") {
  def email = column[String]("ID", O.PrimaryKey)
  def password = column[String]("PASSWORD")
  def * = email ~ password <> (User, User.unapply _)
}







