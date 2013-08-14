
import models._
import play.api.db.DB
import play.api.{Logger, GlobalSettings, Application}
import play.api.Play.current
import scala.slick.driver.H2Driver.simple._
//import scala.slick.driver.PostgresDriver.simple._
import Database.threadLocalSession

object Global extends GlobalSettings {

  override def onStart(app: Application) {

    lazy val db = Database.forDataSource(DB.getDataSource())

//    db.withSession {
//      val ddl = Books.ddl
//      ddl.create
//    }


//    InitialData.insert()
  }
}

object InitialData {

//  def insert() {
//    DB.withSession { implicit session: Session =>
//      Logger.debug("Starting up database")
////      Logger.debug(Books.ddl.createStatements.mkString)
////      (Books.ddl ++ Users.ddl).create
//      Logger.debug("No books in database, initiating import of default records")
//      Seq(
//        Book(None, "JavaScript: The Good Parts", "Douglas Crockford", 2008,
//          "JavaScript Programming", "images/eloquent_javascript.png"),
//        Book(None, "The Little Book on CoffeeScript", "Alex MacCaw", 2012,
//          "CoffeeScript Programming", "images/eloquent/javascript.png"),
//        Book(None, "Scala for the Impatient", "Cay S. Horstmann", 2012,
//          "Scala Programming", "images/eloquent/javascript.png"),
//        Book(None, "American Psycho", "Bret Easton Ellis", 1991,
//          "Crazy Bloodbath", "images/eloquent/javascript.png"),
//        Book(None, "Eloquent JavaScript", "Marijn Haverbeke", 2011,
//          "JavaScript Programming", "images/eloquent/javascript.png")
//      ).foreach(Books.insertBook)
//      Logger.debug("Completed import of default records")
//    }
//  }
}
