
//import models._
//import play.api.db.DB
//import play.api.{Logger, GlobalSettings, Application}
//import play.api.Play.current
//import scala.slick.driver.H2Driver.simple._
//import scala.slick.driver.H2Driver.simple.Database
//import scala.slick.driver.PostgresDriver.simple._
//import Database.threadLocalSession

import models._
import play.api.db.DB
import play.api.Play.current
import play.api.{Application, GlobalSettings, Logger}
import scala.slick.driver.H2Driver.simple._
//import Database.threadLocalSession



object Global extends GlobalSettings {

  override def onStart(app: Application) {

//  lazy val db = Database.forDataSource(DB.getDataSource())

//    db.withSession {
//      val ddl = Books.ddl ++ Tags.ddl ++ BookTags.ddl
//      ddl.create
//      InitialData.insert()
//    }
////    InitialData.insert()
  }
}

object InitialData {

  def insert()(implicit s: Session) {
//    Logger.debug("Starting up database")
//    Seq(
//      Book(1, "JavaScript: The Good Parts", "Douglas Crockford", 2008, "pic1.png"),
//      Book(2, "The Little Book on CoffeeScript", "Alex MacCaw", 2012, "pic2.png"),
//      Book(3, "Scala for the Impatient", "Cay S. Horstmann", 2012, "pic3.png"),
//      Book(4, "Guns, Germs, and Steel", "Jared Diamond", 1999, "pic4.png"),
//      Book(5, "Eloquent JavaScript", "Marijn Haverbeke", 2011, "pic5.png")
//    ).foreach(Books.insertBook)
//
//    Seq(
//      Tag(1, "Javascript"),
//      Tag(2, "CoffeeScript"),
//      Tag(3, "Scala"),
//      Tag(4, "Teddy Bears"),
//      Tag(5, "Hundred Acre Wood"),
//      Tag(6, "Fiction"),
//      Tag(7, "Programming")
//    ).foreach(Tags.insertTag)
//
//    Seq(
//      BookTag(1,1),
//      BookTag(1,7),
//      BookTag(2,2),
//      BookTag(2,7),
//      BookTag(3,3),
//      BookTag(4,4),
//      BookTag(4,5),
//      BookTag(4,6),
//      BookTag(5,1),
//      BookTag(5,7)
//    ).foreach(BookTags.insertBookTag)

  }
}
