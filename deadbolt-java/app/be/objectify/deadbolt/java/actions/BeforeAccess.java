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

import be.objectify.deadbolt.core.DeadboltHandler;
import play.mvc.With;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Invokes beforeRoleCheck on the global or a specified {@link be.objectify.deadbolt.core.DeadboltHandler}.  This is an easy way to programmatically
 * apply authentication without requiring a role.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
@With(BeforeAccessAction.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
public @interface BeforeAccess
{
    /**
     * Use a specific {@link be.objectify.deadbolt.core.DeadboltHandler} for this restriction in place of the global one.
     *
     * @return the class of the DeadboltHandler you want to use
     */
    Class<? extends DeadboltHandler> value() default DeadboltHandler.class;

    /**
     * By default, if another Deadbolt action has already been executed in the same request and has allowed access,
     * beforeRoleCheck will not be executed again.  Set this to true if you want it to execute unconditionally.
     *
     * @return true if beforeRoleCheck should always be executed, otherwise false
     */
    boolean alwaysExecute() default true;

    /**
     * If true, the annotation will only be run if there is a {@link DeferredDeadbolt} annotation at the class level.
     *
     * @return true iff the associated action should be deferred until class-level annotations are applied.
     */
    boolean deferred() default false;
}