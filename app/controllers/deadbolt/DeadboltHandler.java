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
package controllers.deadbolt;

import models.deadbolt.RoleHolder;
import play.mvc.Result;

/**
 * @author Steve Chaloner
 */
public interface DeadboltHandler
{
    /**
     * Invoked immediately before controller or view restrictions are checked. This forms the integration with any
     * authentication actions that may need to occur.
     */
    void beforeRoleCheck();

    /**
     * Gets the current {@link RoleHolder}, e.g. the current user.
     *
     * @return the current role holder
     */
    RoleHolder getRoleHolder();

    /**
     * Invoked when an access failure is detected on <i>controllerClassName</i>.
     * @param content
     */
    Result onAccessFailure(String content);

    /**
     * Gets the handler used for dealing with resources restricted to specific users/groups.
     *
     * @return the handler for restricted resources. May be null.
     */
    DynamicResourceHandler getDynamicResourceHandler();
}