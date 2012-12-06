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
  def Restrict[A](roleNames: Array[String],
                  scalaboltHandler: DeadboltHandler)(action: Action[A]): Action[A] = {
    Action(action.parser) { implicit request =>
      scalaboltHandler.getRoleHolder(request) match {
        case Some(roleHolder) if DeadboltAnalyzer.checkRole(roleHolder, roleNames) => action(request)
        case _ => scalaboltHandler.onAccessFailure(request)
      }
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
  def Restrictions[A](roleGroups: List[Array[String]],
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
   * @param deadboltHandler
   * @param action
   * @tparam A
   * @return
   */
  def Dynamic[A](name: String,
                 meta: String = "",
                 deadboltHandler: DeadboltHandler)
                (action: Action[A]): Action[A] = {
    Action(action.parser) { implicit request =>
      deadboltHandler.getDynamicResourceHandler(request) match {
        case Some(dynamicHandler) => {
          if (dynamicHandler.isAllowed(name, meta, deadboltHandler, request)) action(request)
          else deadboltHandler.onAccessFailure(request)
        }
        case None =>
          throw new RuntimeException("A dynamic resource is specified but no dynamic resource handler is provided")
      }
    }
  }

  /**
   * Denies access to the action if there is no role holder present.
   *
   * @param deadboltHandler
   * @param action
   * @tparam A
   * @return
   */
  def RoleHolderPresent[A](deadboltHandler: DeadboltHandler)(action: Action[A]): Action[A] = {
    Action(action.parser) { implicit request =>
      deadboltHandler.getRoleHolder(request) match {
        case Some(handler) => action(request)
        case None => deadboltHandler.onAccessFailure(request)
      }
    }
  }

  /**
   * Denies access to the action if there is a role holder present.
   *
   * @param deadboltHandler
   * @param action
   * @tparam A
   * @return
   */
  def RoleHolderNotPresent[A](deadboltHandler: DeadboltHandler)(action: Action[A]): Action[A] = {
    Action(action.parser) { implicit request =>
      deadboltHandler.getRoleHolder(request) match {
        case Some(roleHolder) => deadboltHandler.onAccessFailure(request)
        case None => action(request)
      }
    }
  }
}
