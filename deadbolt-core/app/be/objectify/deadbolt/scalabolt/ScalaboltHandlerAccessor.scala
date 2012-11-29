package be.objectify.deadbolt.scalabolt

/**
 * Accessor for the default {@link ScalaboltHandler}.  This allows classes or objects to be used.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
trait ScalaboltHandlerAccessor {

  def getInstance(): ScalaboltHandler

}