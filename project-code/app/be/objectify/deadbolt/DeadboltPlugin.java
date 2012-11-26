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

import be.objectify.deadbolt.scalabolt.ScalaboltHandler;
import be.objectify.deadbolt.scalabolt.ScalaboltHandlerAccessor;
import play.Application;
import play.Configuration;
import play.Logger;
import play.Plugin;

import java.util.Set;

/**
 * A play plugin that provides authorization mechanism for defining access rights
 * to certain controller methods or parts of a view using a simple AND/OR/NOT syntax.
 */
public class DeadboltPlugin extends Plugin
{

    public static final String DEADBOLT_HANDLER_KEY = "deadbolt.handler";
    public static final String SCALABOLT_HANDLER_KEY = "scalabolt.handler.accessor";

    public static final String CACHE_USER = "deadbolt.cache-user";

    private boolean cacheUserPerRequestEnabled = false;
    private DeadboltHandler deadboltHandler;
    private ScalaboltHandlerAccessor scalaboltHandlerAccessor;

    private final Application application;

    public DeadboltPlugin(Application application)
    {
        this.application = application;
    }

    /**
     * Reads the configuration file and initialize the {@link DeadboltHandler}
     */
    @Override
    public void onStart()
    {
        Configuration configuration = application.configuration();
        Set<String> configurationKeys = configuration.keys();

        if (!configurationKeys.contains(DEADBOLT_HANDLER_KEY)
            && !configurationKeys.contains(SCALABOLT_HANDLER_KEY))
        {
            Logger.warn("No handlers declared for Deadbolt or Scalabolt.  This can cause problems when using view templates.");
        }

        if (configurationKeys.contains(DEADBOLT_HANDLER_KEY))
        {
            String deadboltHandlerName = null;
            try
            {
                deadboltHandlerName = configuration.getString(DEADBOLT_HANDLER_KEY);
                deadboltHandler = (DeadboltHandler) Class.forName(deadboltHandlerName,
                        true,
                        application.classloader()).newInstance();
            }
            catch (Exception e)
            {
                throw configuration.reportError(DEADBOLT_HANDLER_KEY,
                        "Error creating Deadbolt handler: " + deadboltHandlerName,
                        e);
            }
        }

        if (configurationKeys.contains(SCALABOLT_HANDLER_KEY))
        {
            String scalaboltHandlerName = null;
            try
            {
                scalaboltHandlerName = configuration.getString(SCALABOLT_HANDLER_KEY);
                scalaboltHandlerAccessor = (ScalaboltHandlerAccessor) Class.forName(scalaboltHandlerName,
                        true,
                        application.classloader()).newInstance();
            }
            catch (Exception e)
            {
                throw configuration.reportError(SCALABOLT_HANDLER_KEY,
                        "Error creating Scalabolt handler accessor: " + scalaboltHandlerName,
                        e);
            }
        }

        if (configurationKeys.contains(CACHE_USER))
        {
            cacheUserPerRequestEnabled = configuration.getBoolean(CACHE_USER);
        }
    }

    /**
     * Getter for the cache-user configuration option
     *
     * @return boolean cache-user value
     */
    public boolean isCacheUserPerRequestEnabled()
    {
        return cacheUserPerRequestEnabled;
    }

    /**
     * Getter for the registered Deadbolt Handler
     *
     * @return the registered Deadbolt handler, or null if it's not defined
     */
    public DeadboltHandler getDeadboltHandler()
    {
        return deadboltHandler;
    }

    /**
     * Getter for the registered Scalabolt Handler
     *
     * @return the registered Scalabolt handler, or null if it's not defined
     */
    public ScalaboltHandler getScalaboltHandler()
    {
        return scalaboltHandlerAccessor == null ? null : scalaboltHandlerAccessor.getInstance();
    }
}
