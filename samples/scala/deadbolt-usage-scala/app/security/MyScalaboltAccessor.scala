package security

import be.objectify.deadbolt.scalabolt.ScalaboltHandlerAccessor

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */

class MyScalaboltAccessor extends ScalaboltHandlerAccessor {
  def getInstance() = new MyScalaboltHandler(new MyDynamicResourceHandler)
}