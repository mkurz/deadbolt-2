package security

import java.lang.System
import be.objectify.deadbolt.scala.{DynamicResourceHandler, DeadboltHandler}
import collection.immutable.Map
import play.api.mvc.Request

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
class MyDynamicResourceHandler extends DynamicResourceHandler
{
  def isAllowed[A](name: String, meta: String, handler: DeadboltHandler, request: Request[A]) =
  {
    MyDynamicResourceHandler.handlers(name).isAllowed(name,
                                                      meta,
                                                      handler,
                                                      request)
  }

  def checkPermission[A](permissionValue: String, deadboltHandler: DeadboltHandler, request: Request[A]) =
  {
    // todo implement this when demonstrating permissions
    false
  }
}

object MyDynamicResourceHandler
{
  val handlers: Map[String, DynamicResourceHandler] =
    Map(
         "pureLuck" -> new DynamicResourceHandler()
         {
           def isAllowed[A](name: String, meta: String, deadboltHandler: DeadboltHandler, request: Request[A]) =
           {
             System.currentTimeMillis() % 2 == 0
           }

           def checkPermission[A](permissionValue: String, deadboltHandler: DeadboltHandler, request: Request[A]) = false
         }
       )
}