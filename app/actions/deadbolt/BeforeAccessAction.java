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
package actions.deadbolt;

import controllers.deadbolt.DeadboltHandler;
import controllers.deadbolt.DynamicResourceHandler;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Invokes beforeRoleCheck on the global or a specific {@link DeadboltHandler}.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public class BeforeAccessAction extends AbstractDeadboltAction<BeforeAccess>
{
    /** {@inheritDoc} */
    @Override
    public Result call(Http.Context ctx) throws Throwable
    {
        Result result;
        if (isActionAuthorised(ctx) && !configuration.alwaysExecute())
        {
            result = delegate.call(ctx);
        }
        else
        {
            DeadboltHandler deadboltHandler = getDeadboltHandler(configuration.value());
            result = deadboltHandler.beforeRoleCheck();

            if (result == null)
            {
                result = delegate.call(ctx);
            }
        }
        return result;
    }
}
