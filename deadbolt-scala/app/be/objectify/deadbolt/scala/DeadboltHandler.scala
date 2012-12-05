package be.objectify.deadbolt.scala

import play.api.mvc.{Request, Result}
import be.objectify.deadbolt.core.models.RoleHolder

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

trait DeadboltHandler
{

  /**
   * Invoked immediately before controller or view restrictions are checked. This forms the integration with any
   * authentication actions that may need to occur.
   */
  def beforeRoleCheck[A](request:Request[A]) : Result

  /**
   * Gets the current role holder e.g. the current user.
   *
   * @return the current role holder
   */
  def getRoleHolder[A](request: Request[A]): Option[RoleHolder]

  /**
   * Invoked when an access failure is detected on <i>controllerClassName</i>.
   *
   * @return the action
   */
  def onAccessFailure[A](request: Request[A]): Result

  /**
   * Gets the handler used for dealing with resources restricted to specific users/groups.
   *
   * @return the handler for restricted resources. May be null.
   */
  def getDynamicResourceHandler[A](request: Request[A]): Option[DynamicResourceHandler]
}