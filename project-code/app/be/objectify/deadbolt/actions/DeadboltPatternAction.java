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

import be.objectify.deadbolt.Deadbolt;
import be.objectify.deadbolt.DeadboltAnalyzer;
import be.objectify.deadbolt.DeadboltHandler;
import be.objectify.deadbolt.models.Permission;
import be.objectify.deadbolt.models.RoleHolder;
import play.cache.Cache;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
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
            case REGEX:
                result = regex(ctx,
                               deadboltHandler);
                break;
            case TREE:
                result = tree(ctx,
                              deadboltHandler);
                break;
            default:
                throw new RuntimeException("Unknown pattern type: " + configuration.patternType());
        }

        return result;
    }

    /**
     * Checks access to the resource based on the regex
     * 
     * @param ctx the HTTP context
     * @param deadboltHandler the Deadbolt handler
     * @return the necessary result
     * @throws Throwable if something needs throwing
     */
    private Result regex(Http.Context ctx, 
                         DeadboltHandler deadboltHandler) throws Throwable
    {
        Result result;
        
        final String patternValue = configuration.value();
        Pattern pattern = Deadbolt.getPattern(patternValue);

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

    /**
     * Checks access to the resource based on the permission tree
     *
     * @param ctx the HTTP context
     * @param deadboltHandler the Deadbolt handler
     * @return the necessary result
     * @throws Throwable if something needs throwing
     */
    private Result tree(Http.Context ctx,
                        DeadboltHandler deadboltHandler) throws Throwable
    {
        throw new UnsupportedOperationException("Tree patterns are not yet supported");
    }
    
    @Override
    public Class<? extends DeadboltHandler> getDeadboltHandlerClass()
    {
        return configuration.handler();
    }
}
