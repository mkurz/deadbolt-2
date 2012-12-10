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
package be.objectify.deadbolt.java.actions;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import play.mvc.Http;
import play.mvc.Result;

/**
 * A dynamic restriction is user-defined, and so completely arbitrary.  Hence, no checks on role holders, etc, occur
 * here.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public class DynamicAction extends AbstractRestrictiveAction<Dynamic>
{
    @Override
    public Result applyRestriction(Http.Context ctx,
                                   DeadboltHandler deadboltHandler) throws Throwable
    {
        DynamicResourceHandler resourceHandler = deadboltHandler.getDynamicResourceHandler(ctx);
        Result result;

        if (resourceHandler == null)
        {
            throw new RuntimeException("A dynamic resource is specified but no dynamic resource handler is provided");
        }
        else
        {
            if (resourceHandler.isAllowed(getValue(),
                                          getMeta(),
                                          deadboltHandler,
                                          ctx))
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

    public String getMeta()
    {
        return configuration.meta();
    }

    public String getValue()
    {
        return configuration.value();
    }

    @Override
    public Class<? extends DeadboltHandler> getDeadboltHandlerClass()
    {
        return configuration.handler();
    }
}
