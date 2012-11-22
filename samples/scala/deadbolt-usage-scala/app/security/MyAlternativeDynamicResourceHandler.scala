package security

import be.objectify.deadbolt.scalabolt.{DynamicResourceHandler, ScalaboltHandler}
import play.api.mvc.Request


/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

object MyAlternativeDynamicResourceHandler extends DynamicResourceHandler
{
  def isAllowed[A](name: String,
                   meta: String,
                   handler: ScalaboltHandler,
                   request: Request[A]) = false

  def checkPermission[A](permissionValue: String,
                         deadboltHandler: ScalaboltHandler,
                         request: Request[A]) = false
}