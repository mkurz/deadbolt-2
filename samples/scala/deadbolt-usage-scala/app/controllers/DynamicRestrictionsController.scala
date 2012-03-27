package controllers

import play.api.mvc.{Action, Controller}
import be.objectify.deadbolt.actions.RoleHolderPresent
import be.objectify.deadbolt.actions.ScalaBoltActions
import views.html.accessOk
import security.MyDeadboltHandler

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
@RoleHolderPresent
object DynamicRestrictionsController extends Controller
{
  def index = Action
              {
                Ok(accessOk())
              }

  def pureLuck = ScalaBoltActions.DynamicAction("pureLuck", "", new MyDeadboltHandler)
                 {
                   Action
                   {
                     Ok(accessOk())
                   }
                 }

  @be.objectify.deadbolt.actions.Dynamic(value = "pureLuck")
  def noWayJose = ScalaBoltActions.DynamicAction("pureLuck", "", new MyDeadboltHandler)
                  {
                    Action
                    {
                      Ok(accessOk())
                    }
                  }
}
