package security

import be.objectify.deadbolt.models.RoleHolder
import models.User
import be.objectify.deadbolt.scalabolt.{DynamicResourceHandler, ScalaboltHandler}
import play.api.mvc.{Result, Results}

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
class MyUserlessScalaboltHandler(dynamicResourceHandler: DynamicResourceHandler = null) extends MyScalaboltHandler
{
  override def getRoleHolder: RoleHolder = null
}