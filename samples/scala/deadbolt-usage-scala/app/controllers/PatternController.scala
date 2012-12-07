package controllers

import security.MyDeadboltHandler
import play.api.mvc.{Action, Controller}
import views.html.accessOk
import be.objectify.deadbolt.scala.DeadboltActions
import be.objectify.deadbolt.core.PatternType

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
object PatternController extends Controller with DeadboltActions
{
  def printersEdit = Pattern("printers.edit", PatternType.EQUALITY, new MyDeadboltHandler) {
                   Action {
                     Ok(accessOk())
                   }
                 }

  def printersFoo = Pattern("printers.foo", PatternType.EQUALITY, new MyDeadboltHandler) {
                   Action {
                     Ok(accessOk())
                   }
                 }

  def printersRegex = Pattern("(.)*\\.edit", PatternType.REGEX, new MyDeadboltHandler) {
                   Action {
                     Ok(accessOk())
                   }
                 }
}
