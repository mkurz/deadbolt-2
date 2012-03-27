package models

import be.objectify.deadbolt.models.Role

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

class SecurityRole(val roleName: String) extends Role
{
  def getRoleName: String = {
    roleName;
  }
}