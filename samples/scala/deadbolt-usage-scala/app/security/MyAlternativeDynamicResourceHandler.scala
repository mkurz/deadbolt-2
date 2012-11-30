package security

import play.api.mvc.Request
import be.objectify.deadbolt.scala.{DeadboltHandler, DynamicResourceHandler}


/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

object MyAlternativeDynamicResourceHandler extends DynamicResourceHandler
{
  def isAllowed[A](name: String,
                   meta: String,
                   handler: DeadboltHandler,
                   request: Request[A]) = false

  def checkPermission[A](permissionValue: String,
                         deadboltHandler: DeadboltHandler,
                         request: Request[A]) = false
}