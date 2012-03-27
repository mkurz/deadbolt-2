package controllers

import be.objectify.deadbolt.actions.Restrict
import views.html.accessOk
import play.api.mvc.{Action, Controller}

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
@Restrict(Array("foo", "bar"))
object RestrictController extends Controller
{
  def index = Action
  {
    Ok(accessOk())
  }

  @Restrict(Array("foo"))
  def restrictOne = Action
  {
    Ok(accessOk())
  }

  @Restrict(Array("foo", "bar"))
  def restrictTwo = Action
  {
    Ok(accessOk())
  }

  @Restrict(Array("foo", "!bar"))
  def restrictThree = Action
  {
    Ok(accessOk())
  }

  @Restrict(Array("hurdy"))
  def restrictFour = Action
  {
    Ok(accessOk())
  }
}