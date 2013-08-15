package controllers

import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Book Library -- Your First RESTful Backbone.js App"))
  }
  
}