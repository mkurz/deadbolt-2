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
 * Within an {@link And} roles are ANDed, and between {@link And} the role groups are ORed.  For example,
 *
 * @author Steve Chaloner (steve@objectify.be)
 * @Restrictions({@And("foo"), @And("hurdy", "gurdy)}) means the {@link be.objectify.deadbolt.core.models.RoleHolder} must have either the
 * foo role OR both the hurdy AND gurdy roles.
 * <p/>
 * Role names that start with ! are negated, so @Restrictions({@And("foo", "bar"), @And("hurdy", "!gurdy")}) requires
 * the {@link be.objectify.deadbolt.core.models.RoleHolder} to have either the foo role AND the bar roles, or the hurdy AND NOT the gurdy
 * roles.
 */
@With(RestrictionsAction.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
public @interface Restrictions
{
    /**
     * Within an {@link And}, the relation is AND.  Between {@link And}s, the relationship is OR.
     *
     * @return
     */
    And[] value();

    /**
     * Indicates the expected response type.  Useful when working with non-HTML responses.  This is free text, which you
     * can use in {@link be.objectify.deadbolt.core.DeadboltHandler#onAccessFailure} to decide on how to handle the response.
     *
     * @return a content indicator
     */
    String content() default "";

    /**
     * Use a specific {@link be.objectify.deadbolt.core.DeadboltHandler} for this restriction in place of the global one.
     *
     * @return the class of the DeadboltHandler you want to use
     */
    Class<? extends DeadboltHandler> handler() default DeadboltHandler.class;

    /**
     * If true, the annotation will only be run if there is a {@link DeferredDeadbolt} annotation at the class level.
     *
     * @return true iff the associated action should be deferred until class-level annotations are applied.
     */
    boolean deferred() default false;
}