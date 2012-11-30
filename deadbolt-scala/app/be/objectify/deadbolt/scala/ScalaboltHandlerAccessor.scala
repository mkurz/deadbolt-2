package be.objectify.deadbolt.scala

/**
 * Accessor for the default {@link DeadboltHandler}.  This allows classes or objects to be used.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
trait ScalaboltHandlerAccessor {

  def getInstance(): DeadboltHandler

}