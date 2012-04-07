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
package controllers;

import be.objectify.deadbolt.PatternType;
import be.objectify.deadbolt.actions.DeadboltPattern;
import be.objectify.deadbolt.actions.Dynamic;
import be.objectify.deadbolt.actions.RoleHolderPresent;
import play.mvc.Controller;
import play.mvc.Result;
import security.MyAlternativeDeadboltHandler;
import views.html.accessOk;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@DeadboltPattern(value = ".*", patternType = PatternType.REGEX)
public class RegexPatternController extends Controller
{
    public static Result index()
    {
        return ok(accessOk.render());
    }

    @DeadboltPattern(value = "(.)*\\.edit", patternType = PatternType.REGEX)
    public static Result editPrinter()
    {
        return ok(accessOk.render());
    }

    @DeadboltPattern(value = "(.)*\\.detonate", patternType = PatternType.REGEX)
    public static Result detonatePrinter()
    {
        return ok(accessOk.render());
    }
}
