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
import play.mvc.Results;

/**
 * @author Steve Chaloner
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
     * @param content
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