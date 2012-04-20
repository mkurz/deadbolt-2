package security

import be.objectify.deadbolt.models.RoleHolder
import models.User
import be.objectify.deadbolt.scalabolt.{DynamicResourceHandler, ScalaboltHandler}
import play.api.mvc.{Result, Results}

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
class MyScalaboltHandler(dynamicResourceHandler: DynamicResourceHandler = null) extends ScalaboltHandler
{
  override def getDynamicResourceHandler: DynamicResourceHandler =
  {
    if (dynamicResourceHandler != null)
    {
      dynamicResourceHandler
    }
    else
    {
      new MyDynamicResourceHandler()
    };
  }

  override def getRoleHolder: RoleHolder =
  {
    new User("steve");
  }

  def onAccessFailure: Result =
  {
    Results.Forbidden(views.html.accessFailed())
  }
}