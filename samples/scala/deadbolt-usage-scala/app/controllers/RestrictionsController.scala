package controllers

import be.objectify.deadbolt.actions.{Restrictions, And}
import views.html.accessOk
import play.api.mvc.{Action, Controller}


/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
@Restrictions(Array(new And(Array("foo")),
                    new And(Array("bar"))))
object RestrictionsController extends Controller
{
  def index = Action
              {
                Ok(accessOk())
              }

  @Restrictions(Array(new And(Array("foo", "bar"))))
  def restrictionsOne = Action
                        {
                          Ok(accessOk())
                        }

  @Restrictions(Array(new And(Array("foo")), new And(Array("foo"))))
  def restrictionsTwo = Action
                        {
                          Ok(accessOk())
                        }

  @Restrictions(Array(new And(Array("hurdy", "gurdy")), new And(Array("foo"))))
  def restrictionsThree = Action
                          {
                            Ok(accessOk())
                          }

  @Restrictions(Array(new And(Array("foo")), new And(Array("!bar"))))
  def restrictionsFour = Action
                         {
                           Ok(accessOk())
                         }

  @Restrictions(Array(new And(Array("hurdy", "foo"))))
  def restrictionsFive = Action
                         {
                           Ok(accessOk())
                         }
}