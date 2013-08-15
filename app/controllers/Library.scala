package controllers

import play.api.mvc.{Action, Controller}
import models.{PutBook, Books, Book}
import models.Books.{bookFormat, putBookFormat}
import play.api.libs.json.{JsObject, JsError, Json, JsArray}
import play.api.Play.current
import play.api.db.DB
import scala.slick.driver.PostgresDriver.simple._

/**
 * books
 *
 * User: Alexandros Bantis
 * Date: 8/13/13
 * Time: 1:32 PM
 */
object Library extends Controller {

  lazy val db = Database.forDataSource(DB.getDataSource())

  def listBooks() = Action {
    db.withSession { implicit s: Session =>
      val books = Books.selectAll().map(x => Json.toJson(x))
      Ok(JsArray(books))
    }
  }

  def getBook(id: Int) = Action {
    db.withSession { implicit s: Session =>
      val book = Books.selectBook(id)
      book match {
        case Some(b) => Ok(Json.toJson(b))
        case None => NotFound
      }
    }
  }

  def postBook() = Action(parse.json) { request =>
    request.body.validate[PutBook].map {
      case book: PutBook => {
        db.withSession { implicit s: Session =>
          val id: Int = Books.insertBook(book)
          Ok(Json.toJson(id))
        }
      }
    }.recoverTotal {
      e => BadRequest("Detected Error: " + JsError.toFlatJson(e))
    }
  }

  def putBook(id: Int) = Action(parse.json) { request =>
    request.body.validate[Book].map {
      case book: Book if book.id == Some(id) => {
        db.withSession { implicit s: Session =>
          val count = Books.updateBook(id, book)
          if (count == 1) Ok("Update Successful")
          else BadRequest("Unable to Save changes")
        }
      }
    }.recoverTotal {
      e => BadRequest("Detected Error: " + JsError.toFlatJson(e))
    }
  }

  def deleteBook(id: Int) = Action {
    db.withSession { implicit s: Session =>
      val count = Books.deleteBook(id)
      if (count == 1) Ok("Delete Successful")
      else BadRequest("Unable to perform delete")
    }
  }

}
