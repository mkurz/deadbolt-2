package be.objectify.deadbolt.scalabolt

import be.objectify.deadbolt.models.RoleHolder
import play.api.mvc.{Session, Result}

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

trait ScalaboltHandler
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
  def onAccessFailure: Result

  /**
   * Gets the handler used for dealing with resources restricted to specific users/groups.
   *
   * @return the handler for restricted resources. May be null.
   */
  def getDynamicResourceHandler: DynamicResourceHandler
}