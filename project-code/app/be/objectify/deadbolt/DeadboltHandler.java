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
package be.objectify.deadbolt;

import be.objectify.deadbolt.models.RoleHolder;
import play.mvc.Result;

/**
 * DeadboltHandler implementations are the main hook into the Deadbolt system.  Here, you can apply authentication
 * checks using {@link DeadboltHandler#beforeRoleCheck}, get the current user, decide what to do when access fails and
 * provide implementations for dynamic security.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public interface DeadboltHandler
{
    /**
     * Invoked immediately before controller or view restrictions are checked. This forms the integration with any
     * authentication actions that may need to occur.
     *
     * @return the action result if an action other than the delegate must be taken, otherwise null. For a case where
     * the user is authenticated (or whatever your test condition is), this will be null otherwise the restriction
     * won't be applied.
     */
    Result beforeRoleCheck();

    /**
     * Gets the current {@link RoleHolder}, e.g. the current user.
     *
     * @return the current role holder
     */
    RoleHolder getRoleHolder();

    /**
     * Invoked when an access failure is detected on <i>controllerClassName</i>.
     *
     * @param content the content type hint.  This can be used to return a response in the appropriate content
     * type, e.g. JSON
     * @return the action result
     */
    Result onAccessFailure(String content);

    /**
     * Gets the handler used for dealing with resources restricted to specific users/groups.
     *
     * @return the handler for restricted resources. May be null.
     */
    DynamicResourceHandler getDynamicResourceHandler();
}