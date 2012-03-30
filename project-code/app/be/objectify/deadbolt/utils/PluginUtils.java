package be.objectify.deadbolt.utils;

import static play.api.Play.unsafeApplication;
import static play.libs.Scala.orNull;
import play.Logger;
import be.objectify.deadbolt.DeadboltHandler;
import be.objectify.deadbolt.DeadboltPlugin;

public class PluginUtils
{
	public static DeadboltPlugin getPlugin() throws Exception
	{
		DeadboltPlugin plugin = orNull(unsafeApplication().plugin(DeadboltPlugin.class));
		if (plugin == null) {
			throw new Exception("Deadbolt plugin was not registered or disabled (check you conf/play.plugins file)");
		}
		return plugin;
	}
	
    public static boolean isUserCacheEnabled() throws Exception
    {
    	DeadboltPlugin p = getPlugin();
        return (p != null ? p.isCacheUserPerRequestEnabled() : false);
    }
    
    public static DeadboltHandler getHandler() throws Exception {
    	DeadboltPlugin p = getPlugin();
        return (p != null ? p.getDeadboltHandler() : null);
    }
}
