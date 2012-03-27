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
package be.objectify.deadbolt;

import be.objectify.deadbolt.models.RoleHolder;
import be.objectify.deadbolt.utils.RequestUtils;
import play.Configuration;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.mvc.Http;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

/**
 * Provides the entry point for view-level annotations.  Also loads and stores the global {@link DeadboltHandler} given
 * in application.conf.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public class Deadbolt
{
    public static final String DEADBOLT_HANDLER_KEY = "deadbolt.handler";

    public static final String CACHE_USER = "deadbolt.cache-user";

    public static boolean CACHE_USER_PER_REQUEST = false;

    public static final DeadboltHandler DEADBOLT_HANDLER;

    static
    {
        Configuration configuration = Play.application().configuration();

        if (!configuration.keys().contains(DEADBOLT_HANDLER_KEY))
        {
            throw configuration.reportError(DEADBOLT_HANDLER_KEY,
                                            "A Deadbolt handler must be defined",
                                            null);
        }

        String deadboltHandlerName = null;
        try
        {

            deadboltHandlerName = configuration.getString(DEADBOLT_HANDLER_KEY);
            DEADBOLT_HANDLER = (DeadboltHandler)Class.forName(deadboltHandlerName).newInstance();
        }
        catch (Exception e)
        {
            throw configuration.reportError(DEADBOLT_HANDLER_KEY,
                                            "Error creating Deadbolt handler: " + deadboltHandlerName,
                                            e);
        }

        if (configuration.keys().contains(CACHE_USER))
        {
            CACHE_USER_PER_REQUEST = configuration.getBoolean(CACHE_USER);
        }
    }

    /**
     * Used for restrict tags in the template.
     *
     * @param roles a list of String arrays.  Within an array, the roles are ANDed.  The arrays in the list are OR'd.
     * @return true if the view can be accessed, otherwise false
     */
    public static boolean viewRestrict(List<String[]> roles)
    {
        boolean roleOk = false;
        RoleHolder roleHolder = RequestUtils.getRoleHolder(DEADBOLT_HANDLER,
                                                           Http.Context.current());
        for (int i = 0; !roleOk && i < roles.size(); i++)
        {
            roleOk = DeadboltAnalyzer.checkRole(roleHolder,
                                                roles.get(i));
        }

        return roleOk;
    }

    /**
     * Used for dynamic tags in the template.
     *
     * @param name the name of the resource
     * @param meta meta information on the resource
     * @return true if the view can be accessed, otherwise false
     */
    public static boolean viewDynamic(String name,
                                      String meta)
    {
        DynamicResourceHandler resourceHandler = DEADBOLT_HANDLER.getDynamicResourceHandler(Http.Context.current());
        boolean allowed = false;
        if (resourceHandler == null)
        {
            throw new RuntimeException("A dynamic resource is specified but no dynamic resource handler is provided");
        }
        else
        {
            if (resourceHandler.isAllowed(name,
                                          meta,
                                          DEADBOLT_HANDLER,
                                          Http.Context.current()))
            {
                allowed = true;
            }
        }

        return allowed;
    }

    /**
     * Used for roleHolderPresent tags in the template.
     *
     * @return true if the view can be accessed, otherwise false
     */
    public static boolean viewRoleHolderPresent()
    {
        RoleHolder roleHolder = DEADBOLT_HANDLER.getRoleHolder(Http.Context.current());
        boolean allowed = false;

        if (roleHolder != null)
        {
            allowed = true;
        }

        return allowed;
    }
    
    public static boolean viewPattern(String value,
                                      PatternType patternType) throws Exception
    {
        boolean allowed = false;
        
        switch (patternType)
        {
            case REGEX:
                allowed = DeadboltAnalyzer.checkRegexPattern(DEADBOLT_HANDLER.getRoleHolder(Http.Context.current()),
                                                             getPattern(value));
                break;
            case TREE:
                Logger.error("Tree patterns are not yet supported");
                break;
            default:
                Logger.error("Unknown pattern type: " + patternType);
        }
        
        return allowed;
    }

    public static Pattern getPattern(final String patternValue) throws Exception
    {
        return Cache.getOrElse("Deadbolt." + patternValue,
                               new Callable<Pattern>()
                               {
                                   public Pattern call() throws Exception
                                   {
                                       return Pattern.compile(patternValue);
                                   }
                               },
                               0);
    }
}