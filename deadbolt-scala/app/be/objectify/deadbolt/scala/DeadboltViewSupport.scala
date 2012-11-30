package be.objectify.deadbolt.scala

import be.objectify.deadbolt.core.models.RoleHolder
import be.objectify.deadbolt.core.DeadboltAnalyzer
import play.api.mvc.Request

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

object DeadboltViewSupport {
  /**
   * Used for restrict tags in the template.
   *
   * @param roles a list of String arrays.  Within an array, the roles are ANDed.  The arrays in the list are OR'd, so
   *              the first positive hit will allow access.
   * @param deadboltHandler application hook
   * @return true if the view can be accessed, otherwise false
   */
  def viewRestrict(roles: List[Array[String]],
                   deadboltHandler: DeadboltHandler,
                   request: Request[Any]): Boolean =
  {
    def check(roleHolder: RoleHolder, current: Array[String], remaining: List[Array[String]]): Boolean = {
      if (DeadboltAnalyzer.checkRole(roleHolder, current)) true
      else if (remaining.isEmpty) false
      else check(roleHolder, remaining.head, remaining.tail)
    }

    if (roles.headOption.isDefined) check(deadboltHandler.getRoleHolder(request), roles.head, roles.tail)
    else false
  }

}