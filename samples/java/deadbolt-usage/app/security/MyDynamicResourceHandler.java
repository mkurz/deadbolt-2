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
package security;

import be.objectify.deadbolt.DeadboltHandler;
import be.objectify.deadbolt.DynamicResourceHandler;
import play.Logger;
import play.mvc.Http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class MyDynamicResourceHandler implements DynamicResourceHandler
{
    private static final Map<String, DynamicResourceHandler> HANDLERS = new HashMap<String, DynamicResourceHandler>();
    
    static
    {
        HANDLERS.put("pureLuck",
                     new DynamicResourceHandler()
                     {
                         public boolean isAllowed(String name, 
                                                  String meta, 
                                                  DeadboltHandler deadboltHandler, 
                                                  Http.Context context)
                         {
                             return System.currentTimeMillis() % 2 == 0;
                         }
                     });
    }
    
    public boolean isAllowed(String name,
                             String meta,
                             DeadboltHandler deadboltHandler,
                             Http.Context context)
    {
        DynamicResourceHandler handler = HANDLERS.get(name);
        boolean result = false;
        if (handler == null)
        {
            Logger.error("No handler available for " + name);
        }
        else
        {
            result = handler.isAllowed(name,
                                       meta,
                                       deadboltHandler,
                                       context);
        }
        return result;
    }
}
