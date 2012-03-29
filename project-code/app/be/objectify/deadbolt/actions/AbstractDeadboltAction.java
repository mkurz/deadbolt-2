/*
 * Copyright 2010-2011 Steve Chaloner
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
import be.objectify.deadbolt.models.RoleHolder;
import be.objectify.deadbolt.utils.PluginUtils;
import be.objectify.deadbolt.utils.RequestUtils;
import play.Configuration;
import play.Logger;
import play.Play;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Provides some convenience methods for concrete Deadbolt actions, such as getting the correct {@link DeadboltHandler},
 * etc.  Extend this if you want to save some time if you create your own action.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public abstract class AbstractDeadboltAction<T> extends Action<T> 
{
    private static final String ACTION_AUTHORISED = "deadbolt.action-authorised";

    private static final String ACTION_UNAUTHORISED = "deadbolt.action-unauthorised";

    /**
     * Gets the current {@link DeadboltHandler}.  This can come from one of two places:
     * - a class name is provided in the annotation.  A new instance of that class will be created. This has the highest priority.
     * - the global handler defined in the application.conf by deadbolt.handler
     *
     * @param deadboltHandlerClass the DeadboltHandler class, if any, coming from the annotation. May be null.
     * @param <C> the actual class of the DeadboltHandler
     * @return an instance of DeadboltHandler.
     */
    protected <C extends DeadboltHandler> DeadboltHandler getDeadboltHandler(Class<C> deadboltHandlerClass) throws Throwable
    {
        DeadboltHandler deadboltHandler;
        if (deadboltHandlerClass != null
            && !deadboltHandlerClass.isInterface())
        {
            try
            {
                deadboltHandler = deadboltHandlerClass.newInstance();
            }
            catch (Exception e)
            {
                throw new RuntimeException("Error creating Deadbolt handler",
                                           e);
            }
        }
        else
        {
            deadboltHandler = PluginUtils.getHandler();
        }
        return deadboltHandler;
    }

    /**
     *
     * @param roleHolder
     * @param roleNames
     * @return
     */
    protected boolean checkRole(RoleHolder roleHolder,
                                String[] roleNames)
    {
        return DeadboltAnalyzer.checkRole(roleHolder,
                                          roleNames);
    }

    /**
     *
     * @param roleHolder
     * @param roleNames
     * @return
     */
    protected boolean hasAllRoles(RoleHolder roleHolder,
                                  String[] roleNames)
    {
        return DeadboltAnalyzer.hasAllRoles(roleHolder,
                                            roleNames);
    }

    /**
     * Wrapper for {@link DeadboltHandler#onAccessFailure} to ensure the access failure is logged.
     *
     * @param deadboltHandler the Deadbolt handler
     * @param content the content type hint
     * @param ctx th request context
     * @return the result of {@link DeadboltHandler#onAccessFailure}
     */
    protected Result onAccessFailure(DeadboltHandler deadboltHandler,
                                     String content,
                                     Http.Context ctx)
    {
        Logger.warn(String.format("Deadbolt: Access failure on [%s]",
                                  ctx.request().uri()));
        return deadboltHandler.onAccessFailure(ctx,
                                               content);
    }

    /**
     * Gets the {@link RoleHolder} from the {@link DeadboltHandler}, and logs an error if it's not present. Note that
     * at least one actions ({@link Unrestricted} does not not require a RoleHolder to be present.
     *
     * @param ctx the request context
     * @param deadboltHandler the Deadbolt handler
     * @return the RoleHolder, if any
     */
    protected RoleHolder getRoleHolder(Http.Context ctx,
                                       DeadboltHandler deadboltHandler)
    {
        RoleHolder roleHolder = RequestUtils.getRoleHolder(deadboltHandler,
                                                           ctx);
        if (roleHolder == null)
        {
            Logger.error(String.format("Access to [%s] requires a RoleHolder, but no RoleHolder is present.",
                                       ctx.request().uri()));
        }

        return roleHolder;
    }

    /**
     * Marks the current action as authorised.  This allows method-level annotations to override controller-level annotations.
     *
     * @param ctx the request context
     */
    protected void markActionAsAuthorised(Http.Context ctx)
    {
        ctx.args.put(ACTION_AUTHORISED,
                     true);
    }

    /**
     * Marks the current action as unauthorised.  This allows method-level annotations to override controller-level annotations.
     *
     * @param ctx the request context
     */
    protected void markActionAsUnauthorised(Http.Context ctx)
    {
        ctx.args.put(ACTION_UNAUTHORISED,
                     true);
    }

    /**
     * Checks if an action is authorised.  This allows controller-level annotations to cede control to method-level annotations.
     *
     * @param ctx the request context
     * @return true if a more-specific annotation has authorised access, otherwise false
     */
    protected boolean isActionAuthorised(Http.Context ctx)
    {
        Object o = ctx.args.get(ACTION_AUTHORISED);
        return o != null && (Boolean)o;
    }

    /**
     * Checks if an action is unauthorised.  This allows controller-level annotations to cede control to method-level annotations.
     *
     * @param ctx the request context
     * @return true if a more-specific annotation has blocked access, otherwise false
     */
    protected boolean isActionUnauthorised(Http.Context ctx)
    {
        Object o = ctx.args.get(ACTION_UNAUTHORISED);
        return o != null && (Boolean)o;
    }
}
