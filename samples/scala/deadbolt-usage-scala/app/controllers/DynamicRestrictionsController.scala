package controllers

import play.api.mvc.{Action, Controller}
import views.html.accessOk
import security.{MyAlternativeDynamicResourceHandler, MyDeadboltHandler}
import be.objectify.deadbolt.scala.DeadboltActions

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
object DynamicRestrictionsController extends Controller with DeadboltActions
{
  def pureLuck = SBDynamic("pureLuck", "", new MyDeadboltHandler)
                 {
                   Action
                   {
                     Ok(accessOk())
                   }
                 }

  def noWayJose = SBDynamic("pureLuck", "", new MyDeadboltHandler(MyAlternativeDynamicResourceHandler))
                  {
                    Action
                    {
                      Ok(accessOk())
                    }
                  }
}
