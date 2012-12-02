package security

import be.objectify.deadbolt.scala.{DynamicResourceHandler, DeadboltHandler}
import play.api.mvc.{Request, Result, Results}
import be.objectify.deadbolt.core.models.RoleHolder
import models.User

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
class MyDeadboltHandler(dynamicResourceHandler: DynamicResourceHandler = null) extends DeadboltHandler
{
  override def getDynamicResourceHandler[A](request: Request[A]): DynamicResourceHandler =
  {
    if (dynamicResourceHandler != null) dynamicResourceHandler
    else new MyDynamicResourceHandler()
  }

  override def getRoleHolder[A](request: Request[A]): RoleHolder =
  {
    // e.g. request.session.get("user")
    new User("steve")
  }

  def onAccessFailure[A](request: Request[A]): Result =
  {
    Results.Forbidden(views.html.accessFailed())
  }
}