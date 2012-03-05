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
package controllers.deadbolt;

import models.deadbolt.RoleHolder;
import play.Configuration;
import play.Play;

import java.util.List;

/**
 * @author Steve Chaloner
 */
public class Deadbolt
{
    public static final String DEADBOLT_HANDLER_KEY = "deadbolt.handler";

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
            throw new RuntimeException("Error creating Deadbolt handler: " + deadboltHandlerName);
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
        for (int i = 0; !roleOk && i < roles.size(); i++)
        {
            roleOk = DeadboltAnalyzer.checkRole(DEADBOLT_HANDLER.getRoleHolder(),
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
        DynamicResourceHandler resourceHandler = DEADBOLT_HANDLER.getDynamicResourceHandler();
        boolean allowed = false;
        if (resourceHandler == null)
        {
            throw new RuntimeException("A dynamic resource is specified but no dynamic resource handler is provided");
        }
        else
        {
            if (resourceHandler.isAllowed(name,
                                          meta,
                                          DEADBOLT_HANDLER))
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
        RoleHolder roleHolder = DEADBOLT_HANDLER.getRoleHolder();
        boolean allowed = false;

        if (roleHolder != null)
        {
            allowed = true;
        }

        return allowed;
    }
}