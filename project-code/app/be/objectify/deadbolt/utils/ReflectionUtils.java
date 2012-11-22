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

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class ReflectionUtils
{
    private ReflectionUtils()
    {
        // no-op
    }

    public static boolean hasMethod(Class clazz,
                                    String name,
                                    Class<?>... parameterTypes)
    {
        boolean found = false;

        try
        {
            clazz.getMethod(name,
                            parameterTypes);
            found = true;
        }
        catch (NoSuchMethodException e)
        {
            // no logging needed, since a) this is possible, and b) the use of reflection should never be admitted to
        }

        return found;
    }
}
