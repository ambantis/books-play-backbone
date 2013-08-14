
import java.text.SimpleDateFormat
import models.{Book, Books}
import play.api.{GlobalSettings, Application}
import play.api.db.slick.{DB, Session}
import play.api.Play.current


object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }
}

object InitialData {

  val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def insert() {
    DB.withSession { implicit session: Session =>
      if (Books.countBooks == 0)
      Seq(
        Book(1, "JavaScript: The Good Parts", "Douglas Crockford", 2008, "JavaScript Programming",
            "images/eloquent_javascript.png"),
        Book(2, "The Little Book on CoffeeScript", "Alex MacCaw", 2012, "CoffeeScript Programming",
          "images/eloquent/javascript.png"),
        Book(3, "Scala for the Impatient", "Cay S. Horstmann", 2012, "Scala Programming",
          "images/eloquent/javascript.png"),
        Book(4, "American Psycho", "Bret Easton Ellis", 2012, "CoffeeScript Programming",
          "images/eloquent/javascript.png"),
        Book(5, "Eloquent JavaScript", "Marijn Haverbeke", 2011, "JavaScript Programming",
          "images/eloquent/javascript.png")
      ).foreach(Books.insertBook)
    }
  }
}
