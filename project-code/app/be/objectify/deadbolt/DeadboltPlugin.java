package be.objectify.deadbolt;

import play.Application;
import play.Configuration;
import play.Plugin;

/**
 * A play plugin that provides authorization mechanism for defining access rights
 * to certain controller methods or parts of a view using a simple AND/OR/NOT syntax.
 *
 */
public class DeadboltPlugin extends Plugin {
	
    public static final String DEADBOLT_HANDLER_KEY = "deadbolt.handler";

    public static final String CACHE_USER = "deadbolt.cache-user";

    private boolean cacheUserPerRequestEnabled = false;
    private DeadboltHandler deadboltHandler;

	private final Application application;
	
	public DeadboltPlugin(Application application) {
		this.application = application;
	}

	/**
	 * Reads the configuration file and initialize the {@link DeadboltHandler}
	 */
	@Override
	public void onStart() {
		Configuration configuration = application.configuration();
		if (!configuration.keys().contains(DEADBOLT_HANDLER_KEY))
        {
            throw configuration.reportError(DEADBOLT_HANDLER_KEY,
                                            "A Deadbolt handler must be defined",
                                            null);
        }

        String deadboltHandlerName = null;
        try
        {
            deadboltHandlerName = configuration.getString(DEADBOLT_HANDLER_KEY);
            deadboltHandler = (DeadboltHandler) Class.forName(deadboltHandlerName, true, application.classloader()).newInstance();
        }
        catch (Exception e)
        {
            throw configuration.reportError(DEADBOLT_HANDLER_KEY,
                                            "Error creating Deadbolt handler: " + deadboltHandlerName,
                                            e);
        }

        if (configuration.keys().contains(CACHE_USER))
        {
            cacheUserPerRequestEnabled = configuration.getBoolean(CACHE_USER);
        }
	}

	/**
	 * Getter for the cache-user configuration option
	 * @return boolean cache-user value
	 */
	public boolean isCacheUserPerRequestEnabled() {
		return cacheUserPerRequestEnabled;
	}

	/**
	 * Getter for the registered Deadbolt Handler
	 * @return DeadboltHandler registered Deadbolt handler
	 */
	public DeadboltHandler getDeadboltHandler() {
		return deadboltHandler;
	}

}
