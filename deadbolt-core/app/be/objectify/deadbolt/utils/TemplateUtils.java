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

import be.objectify.deadbolt.models.RoleHolder;
import play.mvc.Http;

import java.util.Arrays;
import java.util.List;

/**
 * Convenience methods for templates.
 *
 * @author Steve Chaloner
 */
public class TemplateUtils
{
    /**
     * Converts the arguments into a String array.  Convenience method for templates.
     *
     * @param args the arguments
     * @return the arguments as an array
     */
    public static String[] as(String... args)
    {
        return args;
    }

    /**
     * Converts the argument array into a List of String arrays.  Convenience method for templates.
     *
     * @param args the arguments
     * @return
     */
    public static List<String[]> la(String[]... args)
    {
        return Arrays.asList(args);
    }

    /**
     * Used for roleHolder tags in the template.
     *
     * @return the current role holder
     */
    public static RoleHolder roleHolder() throws Throwable
    {
        return RequestUtils.getRoleHolder(PluginUtils.getDeadboltHandler(), Http.Context.current());
    }
}
