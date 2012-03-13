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
import play.mvc.Http;
import play.mvc.Result;

/**
 * Convenience class for checking if an qction has already been authorised before applying the restrictions.
 * 
 * @author Steve Chaloner (steve@objectify.be)
 */
public abstract class AbstractRestrictiveAction<T> extends AbstractDeadboltAction<T>
{
    @Override
    public Result call(Http.Context ctx) throws Throwable
    {
        Result result;
        if (isActionAuthorised(ctx))
        {
            result = delegate.call(ctx);
        }
        else
        {
            DeadboltHandler deadboltHandler = getDeadboltHandler(getDeadboltHandlerClass());
            result = deadboltHandler.beforeRoleCheck();

            if (result == null)
            {
                result = applyRestriction(ctx,
                                          deadboltHandler);
            }
        }
        return result;
    }

    public abstract Class<? extends DeadboltHandler> getDeadboltHandlerClass();

    public abstract Result applyRestriction(Http.Context ctx,
                                            DeadboltHandler deadboltHandler) throws Throwable;
}
