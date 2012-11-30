package models

import be.objectify.deadbolt.core.models.Permission

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

class UserPermission(val value: String) extends Permission
{
  def getValue: String = {
    value;
  }
}
