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
case class BookWithTag(bookId: Option[Int], title: String, author: String, released: Int, coverImage: String,
                        keywords: List[String])

object BooksWithTags {

  def selectAll()(implicit s: Session): Seq[BookWithTag] = s.withTransaction {
    val joinTagsWithBookTags = for {
      t <- Tags
      bt <- BookTags
    } yield (bt.bId, t.name)
    val bookTagTuples =
      for {
        tags <- joinTagsWithBookTags.to[List[(Int, String)]].groupBy(_._1)
        book: Book <- Books.selectAll()
        if tags._1 == book.bookId
      } yield (book, tags)
    val output =
      for {
        tuple <- bookTagTuples
        book: Book = tuple._1
        tag1 = tuple._2
        tag2 = tag1._2.map(x => x._2)
        bookWithTags = BookWithTag(book.bookId, book.title, book.author, book.released, book.coverImage, tag2)
      } yield bookWithTags
    output.to[Seq[BookWithTag]]
  }

  def selectBookWithTag(id: Int)(implicit s: Session): Option[BookWithTag] = s.withTransaction {
    val joinTagsWithBookTags = for {
      t <- Query(Tags)
      bt <- Query(BookTags)
    } yield (bt.bId, t.name)
    val output =
      (for {
        tags: List[String] <- joinTagsWithBookTags.groupBy(_._1)
        b: Book <- Query(Books)
        if b.bookId == id && tags._1 == id
        bookWithTags = BookWithTag(b.bookId, b.title, b.author, b.released, b.coverImage, tags)
      } yield bookWithTags).to[Seq[BookWithTag]]
    output.headOption
  }
}


case class Book(bookId: Option[Int], title: String, author: String, released: Int, coverImage: String)

case class PutBook(title: String, author: String, released: Int, coverImage: String)

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

  def insertBook(nb: PutBook)(implicit s: Session): Int = {
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
  implicit val putBookFormat = Format(Json.reads[PutBook], Json.writes[PutBook])
}


case class Tag(tagId: Option[Int], name: String)

object Tags extends Table[Tag]("tags") {
  def tagId = column[Int]("tag_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def * = tagId.? ~ name <> (Tag, Tag.unapply _)
  def autoInc = name returning tagId

  implicit val tagFormat = Format(Json.reads[Tag], Json.writes[Tag])

}

case class BookTag(bId: Option[Int], tId: Option[Int])

object BookTags extends Table[BookTag]("book_tags") {
  def bId = column[Int]("b_id")
  def tId = column[Int]("t_id")
  def * = bId.? ~ tId.? <> (BookTag, BookTag.unapply _)
  def pk = primaryKey("pk_a", (bId, tId))
  def book = foreignKey("b_id", bId, Books)(_.bookId)
  def tag = foreignKey("t_id", tId, Tags)(_.tagId)

  implicit val bookTagFormat = Format(Json.reads[BookTag], Json.writes[BookTag])
}