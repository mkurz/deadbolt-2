package controllers

import views.html.accessOk
import play.api.mvc.{Action, Controller}
import be.objectify.deadbolt.scalabolt.Scalabolt
import security.MyScalaboltHandler


/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
object RestrictionsController extends Controller with Scalabolt
{
  def restrictionsOne = SBRestrictions(List(Array("foo", "bar")), new MyScalaboltHandler)
                        {
                          Action
                          {
                            Ok(accessOk())
                          }
                        }

  def restrictionsTwo = SBRestrictions(List(Array("foo"), Array("bar")), new MyScalaboltHandler)
                        {
                          Action
                          {
                            Ok(accessOk())
                          }
                        }

  def restrictionsThree = SBRestrictions(List(Array("hurdy", "gurdy"), Array("foo")), new MyScalaboltHandler)
                          {
                            Action
                            {
                              Ok(accessOk())
                            }
                          }

  def restrictionsFour = SBRestrictions(List(Array("foo"), Array("!bar")), new MyScalaboltHandler)
                         {
                           Action
                           {
                             Ok(accessOk())
                           }
                         }

  def restrictionsFive = SBRestrictions(List(Array("hurdy", "foo")), new MyScalaboltHandler)
                         {
                           Action
                           {
                             Ok(accessOk())
                           }
                         }
}