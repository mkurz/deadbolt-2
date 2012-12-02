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
package security;

import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.core.models.RoleHolder;
import play.mvc.Http;
import play.mvc.Result;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class NoUserDeadboltHandler extends AbstractDeadboltHandler
{
    public Result beforeRoleCheck(Http.Context context)
    {
        return null;
    }

    public RoleHolder getRoleHolder(Http.Context context)
    {
        return null;
    }

    public Result onAccessFailure(Http.Context context, String content)
    {
        return forbidden();
    }

    public DynamicResourceHandler getDynamicResourceHandler(Http.Context context)
    {
        return new MyAlternativeDynamicResourceHandler();
    }
}
