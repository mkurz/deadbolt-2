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
package actions.deadbolt;

import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.DeadboltAnalyzer;
import controllers.deadbolt.DeadboltHandler;
import models.deadbolt.RoleHolder;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * @author Steve Chaloner
 */
public abstract class AbstractDeadboltAction<T> extends Action<T> 
{
    private static final String ACTION_AUTHORISED = "deadbolt.action-authorised";

    private static final String ACTION_UNAUTHORISED = "deadbolt.action-unauthorised";

    protected <C extends DeadboltHandler> DeadboltHandler getDeadboltHandler(Class<C> deadboltHandlerClass)
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
            deadboltHandler = Deadbolt.DEADBOLT_HANDLER;
        }
        return deadboltHandler;
    }

    protected boolean checkRole(RoleHolder roleHolder,
                                String[] roleNames)
    {
        return DeadboltAnalyzer.checkRole(roleHolder,
                                          roleNames);
    }

    protected boolean hasAllRoles(RoleHolder roleHolder,
                                  String[] roleNames)
    {
        return DeadboltAnalyzer.hasAllRoles(roleHolder,
                                            roleNames);
    }
    
    protected Result onAccessFailure(DeadboltHandler deadboltHandler,
                                     String content,
                                     Http.Context ctx)
    {
        Logger.warn(String.format("Deadbolt: Access failure on [%s]",
                                  ctx.request().uri()));
        return deadboltHandler.onAccessFailure(content);
    }

    protected RoleHolder getRoleHolder(Http.Context ctx,
                                       DeadboltHandler deadboltHandler)
    {
        RoleHolder roleHolder = deadboltHandler.getRoleHolder();
        if (roleHolder == null)
        {
            Logger.error(String.format("Access to [%s] requires a RoleHolder, but no RoleHolder is present.  Denying access",
                                       ctx.request().uri()));
        }

        return roleHolder;
    }

    protected void markActionAsAuthorised(Http.Context ctx)
    {
        ctx.request().args.put(ACTION_AUTHORISED,
                               true);
    }

    protected void markActionAsUnauthorised(Http.Context ctx)
    {
        ctx.request().args.put(ACTION_UNAUTHORISED,
                               true);
    }

    protected boolean isActionAuthorised(Http.Context ctx)
    {
        Object o = ctx.request().args.get(ACTION_AUTHORISED);
        return o != null && (Boolean)o;
    }

    protected boolean isActionUnauthorised(Http.Context ctx)
    {
        Object o = ctx.request().args.get(ACTION_UNAUTHORISED);
        return o != null && (Boolean)o;
    }
}
