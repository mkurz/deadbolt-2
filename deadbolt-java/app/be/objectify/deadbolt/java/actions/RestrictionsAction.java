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
package be.objectify.deadbolt.java.actions;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.core.models.RoleHolder;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the {@link Restrictions} functionality, i.e. within an {@link And} roles are ANDed, and between
 * {@link And} the role groups are ORed.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public class RestrictionsAction extends AbstractRestrictiveAction<Restrictions>
{
    public RestrictionsAction()
    {
        // no-op
    }

    public RestrictionsAction(Restrictions configuration,
                              Action<?> delegate)
    {
        this.configuration = configuration;
        this.delegate = delegate;
    }

    @Override
    public Result applyRestriction(Http.Context ctx,
                                   DeadboltHandler deadboltHandler) throws Throwable
    {
        Result result;

        if (isAllowed(ctx,
                      deadboltHandler))
        {
            markActionAsAuthorised(ctx);
            result = delegate.call(ctx);
        }
        else
        {
            markActionAsUnauthorised(ctx);
            result = onAccessFailure(deadboltHandler,
                                     configuration.content(),
                                     ctx);
        }

        return result;
    }


    private boolean isAllowed(Http.Context ctx,
                              DeadboltHandler deadboltHandler)
    {
        RoleHolder roleHolder = getRoleHolder(ctx,
                                              deadboltHandler);

        boolean roleOk = false;
        if (roleHolder != null)
        {
            List<String[]> roleGroups = getRoleGroups();

            for (int i = 0; !roleOk && i < roleGroups.size(); i++)
            {
                roleOk = checkRole(roleHolder,
                                   roleGroups.get(i));
            }
        }

        return roleOk;
    }

    public List<String[]> getRoleGroups()
    {
        List<String[]> roleGroups = new ArrayList<String[]>();
        for (And group : configuration.value())
        {
            roleGroups.add(group.value());
        }
        return roleGroups;
    }

    @Override
    public Class<? extends DeadboltHandler> getDeadboltHandlerClass()
    {
        return configuration.handler();
    }
}
