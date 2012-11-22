package security

import java.lang.System
import be.objectify.deadbolt.scalabolt.{DynamicResourceHandler, ScalaboltHandler}
import collection.immutable.Map
import play.api.mvc.Request

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
class MyDynamicResourceHandler extends DynamicResourceHandler
{
  def isAllowed[A](name: String, meta: String, handler: ScalaboltHandler, request: Request[A]) =
  {
    MyDynamicResourceHandler.handlers(name).isAllowed(name,
                                                      meta,
                                                      handler,
                                                      request)
  }

  def checkPermission[A](permissionValue: String, deadboltHandler: ScalaboltHandler, request: Request[A]) =
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
                                                             def isAllowed[A](name: String, meta: String, deadboltHandler: ScalaboltHandler, request: Request[A]) =
                                                             {
                                                               System.currentTimeMillis() % 2 == 0
                                                             }

                                                             def checkPermission[A](permissionValue: String, deadboltHandler: ScalaboltHandler, request: Request[A]) = false
                                                           }
                                                         )
}