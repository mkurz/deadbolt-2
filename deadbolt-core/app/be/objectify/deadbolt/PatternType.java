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
package be.objectify.deadbolt;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public enum PatternType
{
    /**
     * Checks the pattern against the permissions of the user.  Exact, case-sensitive matches only!
     */
    EQUALITY,

    /**
     * A standard regular expression that will be evaluated against the permissions of the RoleHolder
     */
    REGEX,

    /**
     * Perform some custom matching on the pattern.  Note this isn't implemented yet!
     */
    CUSTOM
}
