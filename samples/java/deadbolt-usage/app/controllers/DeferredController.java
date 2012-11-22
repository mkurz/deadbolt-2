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

import actions.UpdateContext;
import be.objectify.deadbolt.actions.And;
import be.objectify.deadbolt.actions.DeadboltPattern;
import be.objectify.deadbolt.actions.DeferredDeadbolt;
import be.objectify.deadbolt.actions.Dynamic;
import be.objectify.deadbolt.actions.Restrict;
import be.objectify.deadbolt.actions.Restrictions;
import be.objectify.deadbolt.actions.RoleHolderNotPresent;
import be.objectify.deadbolt.actions.RoleHolderPresent;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.accessOk;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@UpdateContext("Added before deferred Deadbolt actions")
@DeferredDeadbolt
public class DeferredController extends Controller
{
    @Restrict(value="foo", deferred=true)
    public static Result deferredRestrict()
    {
        return ok(accessOk.render());
    }

    @Restrictions(value={@And({"foo", "bar"})}, deferred=true)
    public static Result deferredRestrictions()
    {
        return ok(accessOk.render());
    }

    @DeadboltPattern(value = "printers.detonate", deferred=true)
    public static Result deferredPattern()
    {
        return ok(accessOk.render());
    }

    @Dynamic(value="pureLuck", deferred=true)
    public static Result deferredDynamic()
    {
        return ok(accessOk.render());
    }

    @RoleHolderPresent(deferred=true)
    public static Result deferredRoleHolderPresent()
    {
        return ok(accessOk.render());
    }

    @RoleHolderNotPresent(deferred=true)
    public static Result deferredRoleHolderNotPresent()
    {
        return ok(accessOk.render());
    }
}
