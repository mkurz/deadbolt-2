package security

import be.objectify.deadbolt.models.RoleHolder
import models.User
import be.objectify.deadbolt.api.{DynamicResourceHandler, DeadboltHandler}
import play.api.mvc.{Result, Action, Results}

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
class MyDeadboltHandler(dynamicResourceHandler: DynamicResourceHandler = null) extends DeadboltHandler
{
  override def getDynamicResourceHandler: DynamicResourceHandler = {
    if (dynamicResourceHandler != null)
    {
      dynamicResourceHandler
    }
    else
    {
      new MyDynamicResourceHandler()
    };
  }

  override def getRoleHolder: RoleHolder = {
    new User("steve");
  }

  def onAccessFailure(): Result = {
    Results.Ok(views.html.accessFailed())
  }
}