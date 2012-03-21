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

import com.avaje.ebean.Ebean;
import models.SecurityRole;
import models.User;
import play.Application;
import play.GlobalSettings;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class Global extends GlobalSettings
{
    @Override
    public void onStart(Application application)
    {
        if (SecurityRole.find.findRowCount() == 0)
        {
            for (String roleName : Arrays.asList("foo", "bar", "hurdy", "gurdy"))
            {
                SecurityRole role = new SecurityRole();
                role.roleName = roleName;
                role.save();
            }
        }

        if (User.find.findRowCount() == 0)
        {
            User user = new User();
            user.userName = "steve";
            user.roles = new ArrayList<SecurityRole>();
            user.roles.add(SecurityRole.findByRoleName("foo"));
            user.roles.add(SecurityRole.findByRoleName("bar"));

            user.save();
            Ebean.saveManyToManyAssociations(user,
                                             "roles");
        }
    }
}
