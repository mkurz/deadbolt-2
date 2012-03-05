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
package actions.deadbolt;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An AND-ed group of names which form an individual part of Deadbolt {@link Restrictions}.  This exists for two reasons
 * - it allows a 2-dimensional array as an annotation parameter
 * - re-using the {@link Restrict} annotation to parameterize isn't possible, because Play will apply
 * {@link RestrictAction}s to the individual parameters
 *
 * @author Steve Chaloner
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface And
{
    /**
     * The role names of roles with access to the target.
     *
     * @return the role names
     */
    String[] value();
}