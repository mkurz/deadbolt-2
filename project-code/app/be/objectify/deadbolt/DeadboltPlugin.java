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
    private boolean cacheUserPerRequestEnabled = false;
    private DeadboltHandler deadboltHandler;

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

        if (configurationKeys.contains(PluginConfigKeys.DEADBOLT_JAVA_HANDLER_KEY))
        {
            String deadboltHandlerName = null;
            try
            {
                deadboltHandlerName = configuration.getString(PluginConfigKeys.DEADBOLT_JAVA_HANDLER_KEY);
                deadboltHandler = (DeadboltHandler) Class.forName(deadboltHandlerName,
                        true,
                        application.classloader()).newInstance();
            }
            catch (Exception e)
            {
                throw configuration.reportError(PluginConfigKeys.DEADBOLT_JAVA_HANDLER_KEY,
                        "Error creating Deadbolt handler: " + deadboltHandlerName,
                        e);
            }
        }
        else
        {
            Logger.warn("No Java handler declared for Deadbolt");
        }


        cacheUserPerRequestEnabled = configuration.getBoolean(PluginConfigKeys.CACHE_DEADBOLT_USER, false);
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
}
