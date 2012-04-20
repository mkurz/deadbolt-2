package controllers

import be.objectify.deadbolt.actions.Restrict
import views.html.accessOk
import play.api.mvc.{Action, Controller}
import be.objectify.deadbolt.scalabolt.Scalabolt
import security.MyScalaboltHandler

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
@Restrict(Array("foo", "bar"))
object RestrictController extends Controller with Scalabolt
{
  def index = Action
              {
                Ok(accessOk())
              }

  def restrictOne = SBRestrict(Array("foo"), new MyScalaboltHandler)
                    {
                      Action
                      {
                        Ok(accessOk())
                      }
                    }

  def restrictTwo = SBRestrict(Array("foo", "bar"), new MyScalaboltHandler)
                    {
                      Action
                      {
                        Ok(accessOk())
                      }
                    }

  def restrictThree = SBRestrict(Array("foo", "!bar"), new MyScalaboltHandler)
                      {
                        Action
                        {
                          Ok(accessOk())
                        }
                      }

  def restrictFour = SBRestrict(Array("hurdy"), new MyScalaboltHandler)
                     {
                       Action
                       {
                         Ok(accessOk())
                       }
                     }
}