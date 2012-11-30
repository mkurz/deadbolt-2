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

import be.objectify.deadbolt.core.DeadboltHandler;
import be.objectify.deadbolt.core.DynamicResourceHandler;
import be.objectify.deadbolt.java.DeadboltViewSupport;
import be.objectify.deadbolt.core.DeadboltAnalyzer;
import play.mvc.Http;
import play.mvc.Result;

import java.util.regex.Pattern;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class DeadboltPatternAction extends AbstractRestrictiveAction<DeadboltPattern>
{
    @Override
    public Result applyRestriction(Http.Context ctx,
                                   DeadboltHandler deadboltHandler) throws Throwable
    {
        Result result;

        switch (configuration.patternType())
        {
            case EQUALITY:
                result = equality(ctx,
                                  deadboltHandler);
                break;
            case REGEX:
                result = regex(ctx,
                               deadboltHandler);
                break;
            case CUSTOM:
                result = custom(ctx,
                                deadboltHandler);
                break;
            default:
                throw new RuntimeException("Unknown pattern type: " + configuration.patternType());
        }

        return result;
    }

    private Result custom(Http.Context ctx,
                          DeadboltHandler deadboltHandler) throws Throwable
    {
        DynamicResourceHandler resourceHandler = deadboltHandler.getDynamicResourceHandler(ctx);
        Result result;

        if (resourceHandler == null)
        {
            throw new RuntimeException("A custom permission type is specified but no dynamic resource handler is provided");
        }
        else
        {
            if (resourceHandler.checkPermission(configuration.value(),
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

    private Result equality(Http.Context ctx,
                            DeadboltHandler deadboltHandler) throws Throwable
    {
        Result result;

        final String patternValue = configuration.value();

        if (DeadboltAnalyzer.checkPatternEquality(getRoleHolder(ctx,
                                                                deadboltHandler),
                                                  patternValue))
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

        return result;
    }

    /**
     * Checks access to the resource based on the regex
     *
     * @param ctx             the HTTP context
     * @param deadboltHandler the Deadbolt handler
     * @return the necessary result
     * @throws Throwable if something needs throwing
     */
    private Result regex(Http.Context ctx,
                         DeadboltHandler deadboltHandler) throws Throwable
    {
        Result result;

        final String patternValue = configuration.value();
        Pattern pattern = DeadboltViewSupport.getPattern(patternValue);

        if (DeadboltAnalyzer.checkRegexPattern(getRoleHolder(ctx,
                                                             deadboltHandler),
                                               pattern))
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

        return result;
    }

    @Override
    public Class<? extends DeadboltHandler> getDeadboltHandlerClass()
    {
        return configuration.handler();
    }
}
