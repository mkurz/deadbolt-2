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

import be.objectify.deadbolt.core.DeadboltAnalyzer;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.RoleHolder;
import models.User;
import play.Logger;
import play.mvc.Http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class MyDynamicResourceHandler implements DynamicResourceHandler
{
    private static final Map<String, DynamicResourceHandler> HANDLERS = new HashMap<String, DynamicResourceHandler>();

    static
    {
        HANDLERS.put("pureLuck",
                     new AbstractDynamicResourceHandler()
                     {
                         public boolean isAllowed(String name, 
                                                  String meta, 
                                                  DeadboltHandler deadboltHandler,
                                                  Http.Context context)
                         {
                             return System.currentTimeMillis() % 2 == 0;
                         }
                     });
        HANDLERS.put("viewProfile",
                     new AbstractDynamicResourceHandler()
                     {
                         public boolean isAllowed(String name,
                                                  String meta,
                                                  DeadboltHandler deadboltHandler,
                                                  Http.Context context)
                         {
                             RoleHolder roleHolder = deadboltHandler.getRoleHolder(context);
                             boolean allowed;
                             if (DeadboltAnalyzer.hasRole(roleHolder, "admin"))
                             {
                                 allowed = true;
                             }
                             else
                             {
                                 // a call to view profile is probably a get request, so
                                 // the query string is used to provide info
                                 Map<String, String[]> queryStrings = context.request().queryString();
                                 String[] requestedNames = queryStrings.get("userName");
                                 allowed = requestedNames != null
                                           && requestedNames.length == 1
                                           && ((User)roleHolder).userName.equals(requestedNames[0]);
                             }

                             return allowed;
                         }
                     });
    }
    
    public boolean isAllowed(String name,
                             String meta,
                             DeadboltHandler deadboltHandler,
                             Http.Context context)
    {
        DynamicResourceHandler handler = HANDLERS.get(name);
        boolean result = false;
        if (handler == null)
        {
            Logger.error("No handler available for " + name);
        }
        else
        {
            result = handler.isAllowed(name,
                                       meta,
                                       deadboltHandler,
                                       context);
        }
        return result;
    }

    public boolean checkPermission(String permissionValue,
                                   DeadboltHandler deadboltHandler,
                                   Http.Context ctx)
    {
        boolean permissionOk = false;
        RoleHolder roleHolder = deadboltHandler.getRoleHolder(ctx);
        
        if (roleHolder != null)
        {
            List<? extends Permission> permissions = roleHolder.getPermissions();
            for (Iterator<? extends Permission> iterator = permissions.iterator(); !permissionOk && iterator.hasNext(); )
            {
                Permission permission = iterator.next();
                permissionOk = permission.getValue().contains(permissionValue);
            }
        }
        
        return permissionOk;
    }
}
