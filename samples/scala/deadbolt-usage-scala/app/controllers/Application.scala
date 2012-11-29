package controllers

import play.api.mvc.{Action, Controller}

object Application extends Controller
{
  def index = Action
              {
                implicit request =>
                Ok(views.html.index())
              }
}