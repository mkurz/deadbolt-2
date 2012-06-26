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
            SecurityRole viewer = new SecurityRole();
            viewer.roleName = "viewer";
            viewer.save();

            SecurityRole editor = new SecurityRole();
            editor.roleName = "editor";
            editor.inheritedRoles = new ArrayList<SecurityRole>(Arrays.asList(viewer));
            editor.save();
            editor.saveManyToManyAssociations("inheritedRoles");

            SecurityRole admin = new SecurityRole();
            admin.roleName = "admin";
            admin.inheritedRoles = new ArrayList<SecurityRole>(Arrays.asList(editor));
            admin.save();
            admin.saveManyToManyAssociations("inheritedRoles");
        }

        if (User.find.findRowCount() == 0)
        {
            User user = new User();
            user.userName = "foo";
            user.roles = new ArrayList<SecurityRole>(Arrays.asList(SecurityRole.findByRoleName("admin")));
            user.save();
            user.saveManyToManyAssociations("roles");
        }
    }
}
