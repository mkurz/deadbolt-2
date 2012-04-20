package security

import be.objectify.deadbolt.scalabolt.{DynamicResourceHandler, ScalaboltHandler}


/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

class MyAlternativeDynamicResourceHandler extends DynamicResourceHandler
{
  def isAllowed(name: String, meta: String, handler: ScalaboltHandler) = false

  def checkPermission(permissionValue: String, deadboltHandler: ScalaboltHandler) = false
}