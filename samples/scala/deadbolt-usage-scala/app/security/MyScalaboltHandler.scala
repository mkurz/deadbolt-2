package security

import be.objectify.deadbolt.models.RoleHolder
import models.User
import be.objectify.deadbolt.scalabolt.{DynamicResourceHandler, ScalaboltHandler}
import play.api.mvc.{Request, Result, Results}

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
class MyScalaboltHandler(dynamicResourceHandler: DynamicResourceHandler = null) extends ScalaboltHandler
{
  override def getDynamicResourceHandler[A](request: Request[A]): DynamicResourceHandler =
  {
    if (dynamicResourceHandler != null) dynamicResourceHandler
    else new MyDynamicResourceHandler()
  }

  override def getRoleHolder[A](request: Request[A]): RoleHolder =
  {
    // request.session.get...
    new User("steve")
  }

  def onAccessFailure[A](request: Request[A]): Result =
  {
    Results.Forbidden(views.html.accessFailed())
  }
}