package be.objectify.deadbolt.scalabolt

import be.objectify.deadbolt.DeadboltAnalyzer
import play.api.mvc._
import be.objectify.deadbolt.models.RoleHolder

/**
 *
 * @author Steve Chaloner
 */
trait Scalabolt extends Results with BodyParsers
{

  def SBRestrict[A](roleNames: Array[String],
                    scalaboltHandler: ScalaboltHandler)
                   (action: Action[AnyContent]): Action[AnyContent] =
  {

    val roleHolder = scalaboltHandler.getRoleHolder;
    if (roleHolder == null || !DeadboltAnalyzer.checkRole(roleHolder, roleNames))
    {
      Action(scalaboltHandler.onAccessFailure);
    }
    else
    {
      action
    }
  }

  def SBRestrictions[A](roleGroups: List[Array[String]],
                        scalaboltHandler: ScalaboltHandler)
                       (action: Action[AnyContent]): Action[AnyContent] =
  {
    // todo implement
    action
  }

  def SBDynamic[A](name: String,
                   meta: String = "",
                   scalaboltHandler: ScalaboltHandler)
                  (action: Action[AnyContent]): Action[AnyContent] =
  {

    val dynamicHandler: DynamicResourceHandler = scalaboltHandler.getDynamicResourceHandler
    if (dynamicHandler == null)
    {
      throw new RuntimeException("A dynamic resource is specified but no dynamic resource handler is provided")
    }
    else
    {
      if (dynamicHandler.isAllowed(name,
                                   meta,
                                   scalaboltHandler))
      {
        action
      }
      else
      {
        Action(scalaboltHandler.onAccessFailure);
      }
    }
  }

  def SBRoleHolderPresent[A](scalaboltHandler: ScalaboltHandler)
                            (action: Action[AnyContent]): Action[AnyContent] =
  {

    val roleHolder: RoleHolder = scalaboltHandler.getRoleHolder
    if (roleHolder != null)
    {
      action
    }
    else
    {
      Action(scalaboltHandler.onAccessFailure);
    }
  }
}
