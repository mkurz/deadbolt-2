package controllers

import views.html.accessOk
import play.api.mvc.{Action, Controller}
import security.MyDeadboltHandler
import be.objectify.deadbolt.scala.DeadboltActions


/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
object RestrictionsController extends Controller with DeadboltActions
{
  def restrictionsOne = SBRestrictions(List(Array("foo", "bar")), new MyDeadboltHandler)
                        {
                          Action
                          {
                            Ok(accessOk())
                          }
                        }

  def restrictionsTwo = SBRestrictions(List(Array("foo"), Array("bar")), new MyDeadboltHandler)
                        {
                          Action
                          {
                            Ok(accessOk())
                          }
                        }

  def restrictionsThree = SBRestrictions(List(Array("hurdy", "gurdy"), Array("foo")), new MyDeadboltHandler)
                          {
                            Action
                            {
                              Ok(accessOk())
                            }
                          }

  def restrictionsFour = SBRestrictions(List(Array("foo"), Array("!bar")), new MyDeadboltHandler)
                         {
                           Action
                           {
                             Ok(accessOk())
                           }
                         }

  def restrictionsFive = SBRestrictions(List(Array("hurdy", "foo")), new MyDeadboltHandler)
                         {
                           Action
                           {
                             Ok(accessOk())
                           }
                         }
}