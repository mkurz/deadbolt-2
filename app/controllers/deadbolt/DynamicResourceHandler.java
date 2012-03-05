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

/**
 * @author Steve Chaloner
 */
public interface DynamicResourceHandler
{
    /**
     * Check the access of the named resource.
     *
     *
     * @param name the resource name
     * @param meta additional information on the resource
     * @param deadboltHandler the current {@link DeadboltHandler}
     * @return true if access to the resource is allowed, otherwise false
     */
    boolean isAllowed(String name,
                      String meta,
                      DeadboltHandler deadboltHandler);
}