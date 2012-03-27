package be.objectify.deadbolt.api

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
  def isAllowed(name: String, meta: String, deadboltHandler: DeadboltHandler): Boolean
}