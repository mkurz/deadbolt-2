package be.objectify.deadbolt.actions

import play.api.mvc.Action
import be.objectify.deadbolt.api.{DynamicResourceHandler, DeadboltHandler}


/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

object ScalaBoltActions
{
  def DynamicAction[A](name: String,
                       meta: String = "",
                       deadboltHandler: DeadboltHandler)
                      (action: Action[A]): Action[A] =
  {
    val dynamicHandler: DynamicResourceHandler = deadboltHandler.getDynamicResourceHandler
    if (dynamicHandler == null)
    {
      throw new RuntimeException("A dynamic resource is specified but no dynamic resource handler is provided")
    }
    else
    {
      if (dynamicHandler.isAllowed(name,
                                   meta,
                                   deadboltHandler))
      {
        action
      }
      else
      {
        // how to turn this into an action?
        // deadboltHandler.onAccessFailure()
        action
      }
    }
  }
}