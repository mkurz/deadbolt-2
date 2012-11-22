package be.objectify.deadbolt.scalabolt

import be.objectify.deadbolt.DeadboltAnalyzer
import play.api.mvc._

/**
 *
 * @author Steve Chaloner
 */
trait Scalabolt extends Results with BodyParsers
{
  /**
   * Restrict access to an action to users that have all the specified roles.
   *
   * @param roleNames
   * @param scalaboltHandler
   * @param action
   * @tparam A
   * @return
   */
  def SBRestrict[A](roleNames: Array[String],
                    scalaboltHandler: ScalaboltHandler)(action: Action[A]): Action[A] = {
    Action(action.parser) { request =>
      val roleHolder = scalaboltHandler.getRoleHolder(request)
      if (roleHolder == null || !DeadboltAnalyzer.checkRole(roleHolder, roleNames)) scalaboltHandler.onAccessFailure(request)
      else action(request)
    }
  }

  /**
   *
   * @param roleGroups
   * @param scalaboltHandler
   * @param action
   * @tparam A
   * @return
   */
  def SBRestrictions[A](roleGroups: List[Array[String]],
                        scalaboltHandler: ScalaboltHandler)
                       (action: Action[A]): Action[A] = {
    Action(action.parser) { request =>
      // todo implement
      action(request)
    }
  }

  /**
   *
   * @param name
   * @param meta
   * @param scalaboltHandler
   * @param action
   * @tparam A
   * @return
   */
  def SBDynamic[A](name: String,
                   meta: String = "",
                   scalaboltHandler: ScalaboltHandler)
                  (action: Action[A]): Action[A] = {
    Action(action.parser) { request =>
      val dynamicHandler: DynamicResourceHandler = scalaboltHandler.getDynamicResourceHandler(request)
      if (dynamicHandler == null) throw new RuntimeException("A dynamic resource is specified but no dynamic resource handler is provided")
      else
      {
        if (dynamicHandler.isAllowed(name, meta, scalaboltHandler, request)) action(request)
        else scalaboltHandler.onAccessFailure(request)
      }
    }
  }

  /**
   * Denies access to the action if there is no role holder present.
   *
   * @param scalaboltHandler
   * @param action
   * @tparam A
   * @return
   */
  def SBRoleHolderPresent[A](scalaboltHandler: ScalaboltHandler)(action: Action[A]): Action[A] = {
    Action(action.parser) { request =>
      val roleHolder = scalaboltHandler.getRoleHolder(request)
      if (roleHolder == null) scalaboltHandler.onAccessFailure(request)
      else action(request)
    }
  }

  /**
   * Denies access to the action if there is a role holder present.
   *
   * @param scalaboltHandler
   * @param action
   * @tparam A
   * @return
   */
  def SBRoleHolderNotPresent[A](scalaboltHandler: ScalaboltHandler)(action: Action[A]): Action[A] = {
    Action(action.parser) { request =>
      val roleHolder = scalaboltHandler.getRoleHolder(request)
      if (roleHolder != null) scalaboltHandler.onAccessFailure(request)
      else action(request)
    }
  }

}
