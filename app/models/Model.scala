package models

import play.api.libs.json._

import scala.slick.driver.PostgresDriver.simple._

/**
 * books
 *
 * User: Alexandros Bantis
 * Date: 8/13/13
 * Time: 12:04 PM
 */
case class BookWithTag(bookId: Int, title: String, author: String, released: String, coverImage: String,
                        keywords: List[String])

object BooksWithTags {

  def selectAll()(implicit s: Session): List[BookWithTag] = {
    val books: List[Book] = Books.selectAll()
    val tags: Map[Int, List[String]] = BookTags.selectAllBookTagsWithName()
    tags.getOrElse(books.head.bookId, Nil)
    val booksWithTags: List[BookWithTag] = books.map(b => BookWithTag(b.bookId, b.title, b.author,
      b.released, b.coverImage, tags.getOrElse(b.bookId, Nil)))
    booksWithTags
  }
  implicit val bookWithTagsFormat = Format(Json.reads[BookWithTag], Json.writes[BookWithTag])
}
case class ValidBook(bookId: Int, title: String, author: String, released: String, coverImage: String)

case class Book(bookId: Int, title: String, author: String, released: String, coverImage: String)

case class PutBook(bookId: Option[Int], title: String, author: String, released: String, coverImage: String)

object Books extends Table[Book]("books") {
  def bookId = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def author = column[String]("author")
  def released = column[String]("released")
  def coverImage = column[String]("image")
  def * = bookId ~ title ~ author ~ released ~ coverImage <> (Book, Book.unapply _)
  def autoInc = title ~ author ~ released ~ coverImage returning bookId

  def selectAll()(implicit s: Session): List[Book] = (for (b <- Books) yield b).to[List]

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


case class Tag(tagId: Int, name: String)

object Tags extends Table[Tag]("tags") {
  def tagId = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def * = tagId ~ name <> (Tag, Tag.unapply _)
  def autoInc = name returning tagId

  implicit val tagFormat = Format(Json.reads[Tag], Json.writes[Tag])

  def insertTag(tag: Tag)(implicit s: Session): Int = Tags.insert(tag)

}

case class BookTag(bId: Int, tId: Int)

object BookTags extends Table[BookTag]("book_tags") {
  def bId = column[Int]("book_id")
  def tId = column[Int]("tag_id")
  def * = bId ~ tId <> (BookTag, BookTag.unapply _)
  def pk = primaryKey("pk_a", (bId, tId))
  def book = foreignKey("book_id", bId, Books)(_.bookId)
  def tag = foreignKey("tag_id", tId, Tags)(_.tagId)

  def selectAllBookTagsWithName()(implicit s: Session): Map[Int, List[String]] = {
    val query = for {
      t <- Tags
      bt <- BookTags if t.tagId === bt.tId
    } yield (bt.bId, t.name)
    query.to[List].groupBy(_._1).mapValues(x => x.map(y => y._2))
  }

  def insertBookTag(bookTag: BookTag)(implicit s: Session) = BookTags.insert(bookTag)

  implicit val bookTagFormat = Format(Json.reads[BookTag], Json.writes[BookTag])
}