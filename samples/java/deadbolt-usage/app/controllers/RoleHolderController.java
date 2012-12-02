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

import be.objectify.deadbolt.java.actions.RoleHolderNotPresent;
import be.objectify.deadbolt.java.actions.RoleHolderPresent;
import play.mvc.Controller;
import play.mvc.Result;
import security.NoUserDeadboltHandler;
import views.html.accessOk;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class RoleHolderController extends Controller
{
    public static Result index()
    {
        return ok(accessOk.render());
    }

    @RoleHolderPresent
    public static Result roleHolderPresent()
    {
        return ok(accessOk.render());
    }

    @RoleHolderPresent(handler = NoUserDeadboltHandler.class)
    public static Result roleHolderPresent_notLoggedIn()
    {
        return ok(accessOk.render());
    }

    @RoleHolderNotPresent(handler = NoUserDeadboltHandler.class)
    public static Result roleHolderNotPresent()
    {
        return ok(accessOk.render());
    }

    @RoleHolderNotPresent
    public static Result roleHolderNotPresent_loggedIn()
    {
        return ok(accessOk.render());
    }
}
