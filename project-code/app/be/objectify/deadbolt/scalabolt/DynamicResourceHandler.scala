package be.objectify.deadbolt.scalabolt

import play.api.mvc.Request

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

trait DynamicResourceHandler
{
  /**
   * Check the access of the named resource.
   *
   * @param name the resource name
   * @param meta additional information on the resource
   * @param deadboltHandler the current { @link DeadboltHandler}
   * @param request the current request
   * @return true if access to the resource is allowed, otherwise false
   */
  def isAllowed[A](name: String,
                   meta: String,
                   deadboltHandler: ScalaboltHandler,
                   request: Request[A]): Boolean

  /**
   * Invoked when a {@link be.objectify.deadbolt.actions.DeadboltPattern} with a {@link PatternType#CUSTOM} type is
   * used.
   *
   * @param permissionValue the permission value
   * @param deadboltHandler the current { @link DeadboltHandler}
   * @param request the current request
   * @return true if access based on the permission is  allowed, otherwise false
   */
  def checkPermission[A](permissionValue: String,
                         deadboltHandler: ScalaboltHandler,
                         request: Request[A]): Boolean
}