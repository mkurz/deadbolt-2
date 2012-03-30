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
package be.objectify.deadbolt.actions;

import be.objectify.deadbolt.DeadboltHandler;
import be.objectify.deadbolt.models.RoleHolder;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Implements the {@link RoleHolderPresent} functionality, i.e. a {@link RoleHolder} must be provided by the
 * {@link be.objectify.deadbolt.DeadboltHandler} to have access to the resource, but no role checks are performed.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public class RoleHolderPresentAction extends AbstractDeadboltAction<RoleHolderPresent>
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Result call(Http.Context ctx) throws Throwable
    {
        Result result;
        if (isActionUnauthorised(ctx))
        {
            result = onAccessFailure(getDeadboltHandler(configuration.handler()),
                                     configuration.content(),
                                     ctx);
        }
        else
        {
            DeadboltHandler deadboltHandler = getDeadboltHandler(configuration.handler());
            RoleHolder roleHolder = getRoleHolder(ctx,
                                                  deadboltHandler);

            if (roleHolder != null)
            {
                markActionAsAuthorised(ctx);
                result = delegate.call(ctx);
            }
            else
            {
                markActionAsUnauthorised(ctx);
                result = onAccessFailure(deadboltHandler,
                                         configuration.content(),
                                         ctx);
            }
        }

        return result;
    }
}
