package security

import java.lang.System
import be.objectify.deadbolt.api.{DynamicResourceHandler, DeadboltHandler}


/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

class MyDynamicResourceHandler extends DynamicResourceHandler
{
  def isAllowed(name: String, meta: String, handler: DeadboltHandler) = {
    MyDynamicResourceHandler.handlers(name).isAllowed(name,
                                                      meta,
                                                      handler);
  }
}

object MyDynamicResourceHandler
{
  val handlers = Map[String, DynamicResourceHandler](
                                                      "pureLuck" -> new DynamicResourceHandler()
                                                      {
                                                        def isAllowed(name: String, meta: String, deadboltHandler: DeadboltHandler) =
                                                        {
                                                          System.currentTimeMillis() % 2 == 0
                                                        }
                                                      }
                                                    );
}