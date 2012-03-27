package security

import java.lang.System
import be.objectify.deadbolt.api.{DynamicResourceHandler, DeadboltHandler}


/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

class MyAlternativeDynamicResourceHandler extends DynamicResourceHandler
{
  def isAllowed(name: String, meta: String, handler: DeadboltHandler) = false
}