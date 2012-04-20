package controllers

import play.api.mvc.{Action, Controller}
import be.objectify.deadbolt.actions.RoleHolderPresent
import be.objectify.deadbolt.scalabolt.Scalabolt
import views.html.accessOk
import security.{MyAlternativeDynamicResourceHandler, MyScalaboltHandler}

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
@RoleHolderPresent
object DynamicRestrictionsController extends Controller with Scalabolt
{
  def pureLuck = SBDynamic("pureLuck", "", new MyScalaboltHandler)
                 {
                   Action
                   {
                     Ok(accessOk())
                   }
                 }

  def noWayJose = SBDynamic("pureLuck", "", new MyScalaboltHandler(new MyAlternativeDynamicResourceHandler))
                  {
                    Action
                    {
                      Ok(accessOk())
                    }
                  }
}
