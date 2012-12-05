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
   * Gets the current role holder e.g. the current user.
   *
   * @return an option containing the current role holder
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
   * @return an option containing the handler for restricted resources
   */
  def getDynamicResourceHandler[A](request: Request[A]): Option[DynamicResourceHandler]
}