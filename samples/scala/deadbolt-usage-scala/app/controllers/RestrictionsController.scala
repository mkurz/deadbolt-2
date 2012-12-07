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
  def restrictionsOne = Restrictions(List(Array("foo", "bar")), new MyDeadboltHandler) {
                          Action {
                            Ok(accessOk())
                          }
                        }

  def restrictionsTwo = Restrictions(List(Array("foo"), Array("bar")), new MyDeadboltHandler) {
                          Action {
                            Ok(accessOk())
                          }
                        }

  def restrictionsThree = Restrictions(List(Array("hurdy", "gurdy"), Array("foo")), new MyDeadboltHandler) {
                            Action {
                              Ok(accessOk())
                            }
                          }

  def restrictionsFour = Restrictions(List(Array("foo"), Array("!bar")), new MyDeadboltHandler) {
                           Action {
                             Ok(accessOk())
                           }
                         }

  def restrictionsFive = Restrictions(List(Array("hurdy", "foo")), new MyDeadboltHandler) {
                           Action {
                             Ok(accessOk())
                           }
                         }
}