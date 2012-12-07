package controllers

import views.html.accessOk
import play.api.mvc.{Action, Controller}
import security.{MyUserlessDeadboltHandler, MyDeadboltHandler}
import be.objectify.deadbolt.scala.DeadboltActions

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
object RoleHolderNotPresentController extends Controller with DeadboltActions
{

  def loggedIn = RoleHolderNotPresent(new MyDeadboltHandler) {
                   Action {
                     Ok(accessOk())
                   }
                 }

  def notLoggedIn = RoleHolderNotPresent(new MyUserlessDeadboltHandler) {
                      Action {
                        Ok(accessOk())
                      }
                    }
}