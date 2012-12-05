package be.objectify.deadbolt.scala

import play.api.mvc.{Action, Results, BodyParsers}
import be.objectify.deadbolt.core.DeadboltAnalyzer

/**
 *
 * @author Steve Chaloner
 */
trait DeadboltActions extends Results with BodyParsers
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
                    scalaboltHandler: DeadboltHandler)(action: Action[A]): Action[A] = {
    Action(action.parser) { implicit request =>
      val roleHolder = scalaboltHandler.getRoleHolder(request)
      if (roleHolder.isDefined  && DeadboltAnalyzer.checkRole(roleHolder.get, roleNames)) action(request)
      else scalaboltHandler.onAccessFailure(request)
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
                        scalaboltHandler: DeadboltHandler)
                       (action: Action[A]): Action[A] = {
    Action(action.parser) { implicit request =>
      // todo implement
      val roleHolder = scalaboltHandler.getRoleHolder(request)
      if (roleHolder.isDefined) action(request)
      else scalaboltHandler.onAccessFailure(request)
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
                   scalaboltHandler: DeadboltHandler)
                  (action: Action[A]): Action[A] = {
    Action(action.parser) { implicit request =>
      val dynamicHandler = scalaboltHandler.getDynamicResourceHandler(request)
      if (!dynamicHandler.isDefined) throw new RuntimeException("A dynamic resource is specified but no dynamic resource handler is provided")
      else {
        if (dynamicHandler.get.isAllowed(name, meta, scalaboltHandler, request)) action(request)
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
  def SBRoleHolderPresent[A](scalaboltHandler: DeadboltHandler)(action: Action[A]): Action[A] = {
    Action(action.parser) { implicit request =>
      val roleHolder = scalaboltHandler.getRoleHolder(request)
      if (roleHolder.isDefined) action(request)
      else scalaboltHandler.onAccessFailure(request)
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
  def SBRoleHolderNotPresent[A](scalaboltHandler: DeadboltHandler)(action: Action[A]): Action[A] = {
    Action(action.parser) { implicit request =>
      val roleHolder = scalaboltHandler.getRoleHolder(request)
      if (roleHolder.isDefined) scalaboltHandler.onAccessFailure(request)
      else action(request)
    }
  }
}
