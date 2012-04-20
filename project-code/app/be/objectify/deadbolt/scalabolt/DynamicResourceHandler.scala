package be.objectify.deadbolt.scalabolt

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
   * @return true if access to the resource is allowed, otherwise false
   */
  def isAllowed(name: String,
                meta: String,
                deadboltHandler: ScalaboltHandler): Boolean

  /**
   * Invoked when a {@link be.objectify.deadbolt.actions.DeadboltPattern} with a {@link PatternType#CUSTOM} type is
   * used.
   *
   * @param permissionValue the permission value
   * @param deadboltHandler the current { @link DeadboltHandler}
   * @return true if access based on the permission is  allowed, otherwise false
   */
  def checkPermission(permissionValue: String,
                      deadboltHandler: ScalaboltHandler): Boolean
}