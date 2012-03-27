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
package be.objectify.deadbolt.utils;

import be.objectify.deadbolt.Deadbolt;
import be.objectify.deadbolt.DeadboltHandler;
import be.objectify.deadbolt.models.RoleHolder;
import play.mvc.Http;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class RequestUtils
{
    private RequestUtils()
    {
        // no-op
    }
    
    public static final RoleHolder getRoleHolder(DeadboltHandler deadboltHandler,
                                                 Http.Context ctx)
    {
        Object cachedUser = ctx.args.get(Deadbolt.CACHE_USER);
        RoleHolder roleHolder;
        if (Deadbolt.CACHE_USER_PER_REQUEST)
        {
            if (cachedUser != null)
            {
                roleHolder = (RoleHolder)cachedUser;
            }
            else
            {
                roleHolder = deadboltHandler.getRoleHolder(ctx);
                ctx.args.put(Deadbolt.CACHE_USER,
                             roleHolder);
            }
        }
        else
        {
            roleHolder = deadboltHandler.getRoleHolder(ctx);
        }

        return roleHolder;
    }
}
