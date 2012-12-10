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
package actions;

import be.objectify.deadbolt.java.actions.RestrictionsAction;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import security.MyRoles;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class CustomRestrictionsAction extends Action<CustomRestrictions>
{
    @Override
    public Result call(Http.Context context) throws Throwable
    {
        final List<String[]> roleGroups = new ArrayList<String[]>();
        for (RoleGroup roleGroup : configuration.value())
        {
            MyRoles[] value = roleGroup.value();
            String[] group = new String[value.length];
            for (int i = 0; i < value.length; i++)
            {
                group[i] = value[i].getRoleName();
            }
            roleGroups.add(group);
        }

        RestrictionsAction restrictionsAction = new RestrictionsAction()
        {
            @Override
            public List<String[]> getRoleGroups()
            {
                return roleGroups;
            }
        };
        restrictionsAction.configuration = configuration.config();
        restrictionsAction.delegate = this.delegate;
        return restrictionsAction.call(context);
    }
}
