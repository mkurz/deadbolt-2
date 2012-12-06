package controllers

import views.html.accessOk
import play.api.mvc.{Action, Controller}
import security.MyDeadboltHandler
import be.objectify.deadbolt.scala.DeadboltActions

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
object RestrictController extends Controller with DeadboltActions
{
  def index = Action
              {
                Ok(accessOk())
              }

  def restrictOne = Restrict(Array("foo"), new MyDeadboltHandler)
                    {
                      Action
                      {
                        Ok(accessOk())
                      }
                    }

  def restrictTwo = Restrict(Array("foo", "bar"), new MyDeadboltHandler)
                    {
                      Action
                      {
                        Ok(accessOk())
                      }
                    }

  def restrictThree = Restrict(Array("foo", "!bar"), new MyDeadboltHandler)
                      {
                        Action
                        {
                          Ok(accessOk())
                        }
                      }

  def restrictFour = Restrict(Array("hurdy"), new MyDeadboltHandler)
                     {
                       Action
                       {
                         Ok(accessOk())
                       }
                     }
}