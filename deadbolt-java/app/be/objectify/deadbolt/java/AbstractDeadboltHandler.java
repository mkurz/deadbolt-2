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
package be.objectify.deadbolt.java;

import be.objectify.deadbolt.core.models.RoleHolder;
import play.mvc.Result;
import play.mvc.Results;

/**
 * Abstract implementation of {@link DeadboltHandler} that gives a standard unauthorised result when access fails.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public abstract class AbstractDeadboltHandler extends Results implements DeadboltHandler
{
    /**
     * {@inheritDoc}
     */
    public RoleHolder getRoleHolder()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Result onAccessFailure(String content)
    {
        return unauthorized(views.html.defaultpages.unauthorized.render());
    }

    /**
     * {@inheritDoc}
     */
    public DynamicResourceHandler getDynamicResourceHandler()
    {
        return null;
    }
}