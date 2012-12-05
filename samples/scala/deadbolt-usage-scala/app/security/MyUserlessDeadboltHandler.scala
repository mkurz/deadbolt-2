package security

import be.objectify.deadbolt.scala.DynamicResourceHandler
import play.api.mvc.Request
import be.objectify.deadbolt.core.models.RoleHolder

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
class MyUserlessDeadboltHandler(dynamicResourceHandler: DynamicResourceHandler = null) extends MyDeadboltHandler
{
  override def getRoleHolder[A](request: Request[A]): Option[RoleHolder] = None
}