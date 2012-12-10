/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package controllers;

import actions.CustomRestrictions;
import actions.RoleGroup;
import be.objectify.deadbolt.java.actions.And;
import be.objectify.deadbolt.java.actions.Restrictions;
import play.mvc.Controller;
import play.mvc.Result;
import security.MyRoles;
import views.html.accessOk;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Restrictions({@And("foo"),
               @And("bar")})
public class RestrictionsController extends Controller
{
    public static Result index()
    {
        return ok(accessOk.render());
    }

    @Restrictions({@And({"foo", "bar"})})
    public static Result restrictionsOne()
    {
        return ok(accessOk.render());
    }

    @Restrictions({@And({"hurdy", "gurdy"}), @And("foo")})
    public static Result restrictionsTwo()
    {
        return ok(accessOk.render());
    }

    @Restrictions({@And("foo"), @And("!bar")})
    public static Result restrictionsThree()
    {
        return ok(accessOk.render());
    }

    @Restrictions(@And({"hurdy", "foo"}))
    public static Result restrictionsFour()
    {
        return ok(accessOk.render());
    }

    @Restrictions(@And({"foo", "!bar"}))
    public static Result restrictionsFive()
    {
        return ok(accessOk.render());
    }


    @CustomRestrictions(value = { @RoleGroup({MyRoles.foo, MyRoles.bar}),
                                  @RoleGroup(MyRoles.hurdy)},
                        config = @Restrictions({}))
    public static Result customRestrictionOne()
    {
        return ok(accessOk.render());
    }

    @CustomRestrictions(value = { @RoleGroup({MyRoles.hurdy, MyRoles.foo}),
                                  @RoleGroup({MyRoles.hurdy, MyRoles.bar})},
                        config = @Restrictions({}))
    public static Result customRestrictionTwo()
    {
        return ok(accessOk.render());
    }
}
