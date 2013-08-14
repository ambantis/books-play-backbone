package controllers

import play.api.mvc.{Action, Controller}
import models.Books
import models.Books.bookFormat
import models.Book
import play.api.libs.json.{JsObject, JsError, Json, JsArray}
import play.api.Play.current
import scala.slick.session.Session
import org.codehaus.jackson.annotate.JsonValue

/**
 * books
 *
 * User: Alexandros Bantis
 * Date: 8/13/13
 * Time: 1:32 PM
 */
object Library extends Controller {

  val db = play.api.db.slick.DB

  def listBooks() = Action {
    db.withSession { implicit s: Session =>
      val books = Books.selectAll().map(x => Json.toJson(x)).toSeq
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

  def putBook() = Action(parse.json) { request =>
    request.body.validate[Book].map {
      case book: Book => {
        db.withSession { implicit s: Session =>
          val id: Int = Books.insertBook(book)
          Ok(Json.toJson(id))
        }
      }
    }.recoverTotal {
      e => BadRequest("Detected Error: " + JsError.toFlatJson(e))
    }
  }
}
