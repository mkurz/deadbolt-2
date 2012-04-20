package security

import java.lang.System
import be.objectify.deadbolt.scalabolt.{DynamicResourceHandler, ScalaboltHandler}
import collection.immutable.Map

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
class MyDynamicResourceHandler extends DynamicResourceHandler
{
  def isAllowed(name: String, meta: String, handler: ScalaboltHandler) =
  {
    MyDynamicResourceHandler.handlers(name).isAllowed(name,
                                                      meta,
                                                      handler);
  }

  def checkPermission(permissionValue: String, deadboltHandler: ScalaboltHandler) =
  {
    // todo implement this when demonstrating permissions
    false
  }
}

object MyDynamicResourceHandler
{
  val handlers: Map[String, DynamicResourceHandler] = Map(
                                                           "pureLuck" -> new DynamicResourceHandler()
                                                           {
                                                             def isAllowed(name: String, meta: String, deadboltHandler: ScalaboltHandler) =
                                                             {
                                                               System.currentTimeMillis() % 2 == 0
                                                             }

                                                             def checkPermission(permissionValue: String, deadboltHandler: ScalaboltHandler) = false
                                                           }
                                                         )
}