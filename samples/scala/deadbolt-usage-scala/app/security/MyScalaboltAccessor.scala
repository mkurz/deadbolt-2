package security

import be.objectify.deadbolt.scalabolt.ScalaboltHandlerAccessor

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

object MyScalaboltAccessor extends ScalaboltHandlerAccessor {
  def getInstance() = new MyScalaboltHandler(new MyDynamicResourceHandler)
}