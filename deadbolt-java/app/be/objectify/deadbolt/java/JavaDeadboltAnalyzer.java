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
package be.objectify.deadbolt.java;

import be.objectify.deadbolt.core.DeadboltAnalyzer;
import be.objectify.deadbolt.core.models.RoleHolder;
import play.mvc.Http;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class JavaDeadboltAnalyzer extends DeadboltAnalyzer
{
    /**
     *
     * @param roleHolder
     * @param handler
     * @param context
     * @param value
     * @return
     */
    public static boolean checkCustomPattern(RoleHolder roleHolder,
                                             DeadboltHandler handler,
                                             Http.Context context,
                                             String value)
    {
        boolean patternOk = false;

        DynamicResourceHandler dynamicResourceHandler = handler.getDynamicResourceHandler(context);
        if (dynamicResourceHandler == null)
        {
            throw new RuntimeException("A custom permission type is specified but no dynamic resource handler is provided");
        }
        else
        {
            patternOk = dynamicResourceHandler.checkPermission(value,
                                                               handler,
                                                               context);
        }

        return patternOk;
    }
}
