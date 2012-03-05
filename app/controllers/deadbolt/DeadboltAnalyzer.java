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

import models.deadbolt.Role;
import models.deadbolt.RoleHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steve Chaloner
 */
public class DeadboltAnalyzer
{
    public static boolean checkRole(RoleHolder roleHolder,
                                    String[] roleNames)
    {
        boolean roleOk = true;
        if (!hasAllRoles(roleHolder,
                         roleNames))
        {
            roleOk = false;
        }
        return roleOk;
    }

    public static boolean hasAllRoles(RoleHolder roleHolder,
                                      String[] roleNames)
    {
        boolean hasRole = false;
        if (roleHolder != null)
        {
            List<? extends Role> roles = roleHolder.getRoles();

            if (roles != null)
            {
                List<String> heldRoles = new ArrayList<String>();
                for (Role role : roles)
                {
                    if (role != null)
                    {
                        heldRoles.add(role.getRoleName());
                    }
                }

                boolean roleCheckResult = true;
                for (int i = 0; roleCheckResult && i < roleNames.length; i++)
                {
                    boolean invert = false;
                    String roleName = roleNames[i];
                    if (roleName.startsWith("!"))
                    {
                        invert = true;
                        roleName = roleName.substring(1);
                    }
                    roleCheckResult = heldRoles.contains(roleName);

                    if (invert)
                    {
                        roleCheckResult = !roleCheckResult;
                    }
                }
                hasRole = roleCheckResult;
            }
        }
        return hasRole;
    }


}
