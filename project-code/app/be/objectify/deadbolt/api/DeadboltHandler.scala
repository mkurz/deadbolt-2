package be.objectify.deadbolt.api

import be.objectify.deadbolt.models.RoleHolder
import play.api.mvc.{Result, Action}

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

trait DeadboltHandler
{
  /**
   * Gets the current role holder e.g. the current user.
   *
   * @return the current role holder
   */
  def getRoleHolder: RoleHolder

  /**
   * Invoked when an access failure is detected on <i>controllerClassName</i>.
   *
   * @return the action result
   */
  def onAccessFailure(): Result

  /**
   * Gets the handler used for dealing with resources restricted to specific users/groups.
   *
   * @return the handler for restricted resources. May be null.
   */
  def getDynamicResourceHandler: DynamicResourceHandler
}