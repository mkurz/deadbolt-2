# A deeper look at dynamic rules #

The `@Dynamic` annotation and `@dynamic` template tag allow you to specify arbitrary rules to control access.  For example:

* Simple (contrived) examples
    * Allow access if today is Tuesday
    * Allow access if a user has a date of birth defined

* Some more complex examples
    * Allow access if the user is a beta tester AND the controller/action/template area is available for beta testing
    * Allow a maximum of 300 requests per hour per API key

* Blacklisting
    * Deny access only if user registered within the last 30 days

## Using sessions and requests in your rules ##

In order to make these decisions, information is helpful.  You may need access to the session, or the current request.  Both are available from the `Http.Context` object passed into your `DynamicResourceHandler` (DRH) implementations.

### Sessions ###
In Play 2, sessions implement the `java.util.Map` interface and so data can get accessed in the usual way, i.e. session.get(key).

    public class MyDynamicResourceHandler implements DynamicResourceHandler
    {
        public boolean isAllowed(String name,
                                 String meta,
                                 DeadboltHandler deadboltHandler,
                                 Http.Context ctx) {
		    Http.Session session = ctx.session();
		    String foo = session.get("bar");
	    }
								 

        public boolean checkPermission(String permissionValue,
                                       DeadboltHandler deadboltHandler,
                                       Http.Context ctx) {
            Http.Session session = ctx.session();
		    String foo = session.get("bar");
		    ...
        }
    }
	
You can also store write data into the session - just don't forget that sessions are stored as client-side cookies!  Any and all confidential information should be kept out of sessions.

### Request query parameters ###
Using the `Http.Request#queryString()` method, you can get a map of all query parameters for the current request.  For example, for the URL

    http://localhost:9000/users?userName=foo

a call to `queryString()` would result in a map containing the key `userName` mapped to the value `foo`.

    public class MyDynamicResourceHandler implements DynamicResourceHandler
    {
        public boolean isAllowed(String name,
                                 String meta,
                                 DeadboltHandler deadboltHandler,
                                 Http.Context ctx) {
		    Http.Request request = ctx.request();
		    Map<String, String> queryStrings = request.queryString();
			String userId = queryStrings.get("userName");
			...
	    }
								 

        public boolean checkPermission(String permissionValue,
                                       DeadboltHandler deadboltHandler,
                                       Http.Context ctx) {
            Http.Request request = ctx.request();
		    Map<String, String> queryStrings = request.queryString();
			String userId = queryStrings.get("userName");
			...
        }
    }

A major problem here is that your DRH needs knowledge of the methods - or, more precisely, the parameters of the method - to which it is applied.  This can be alleviated somewhat through the use of convention, or by passing metadata into the DRH.  Both will be covered later in this chapter.
	
### Request bodies ###
When using a HTTP POST or PUT operation, the request body will typically contain information.  Bodies can be accessed in a variety of formats, including custom formats.

    public class MyDynamicResourceHandler implements DynamicResourceHandler
    {
        public boolean isAllowed(String name,
                                 String meta,
                                 DeadboltHandler deadboltHandler,
                                 Http.Context ctx) {
		    Http.Request request = ctx.request();
			Http.RequestBody body = request.body();
		    JsonNode json = body.asJson();
			...
	    }
								 

        public boolean checkPermission(String permissionValue,
                                       DeadboltHandler deadboltHandler,
                                       Http.Context ctx) {
            Http.Request request = ctx.request();
			Http.RequestBody body = request.body();
		    MyCustomType o = body.as(MyCustomType.class);
			...
        }
    }

Once you have obtained the information from the body, you're back in the decision-making process of your rule.

###  Request path parameters ###

Path parameters are, unfortunately, the point at which we hit a problem.  Play encourages developers to create clean, RESTful URLs, for example

    http://localhost:9000/users/foo
	
in place of the query parameter example used earlier,

    http://localhost:9000/users?userName=foo
	
The benefits of clean URLs are many - they can be bookmarked, are easily shared and are more easily cached, to name just a few.  The one thing that path parameters are not, in Play 2, is available at runtime.  There is no way in Play 2 to get these parameters without deriving them manually by comparing the route definition with the actual URL.

It has been stated on the Play! Framework group (<https://groups.google.com/d/msg/play-framework/9qssE8s8aQA/pGXHrBf7gOYJ>) that path parameters should not be accessible by calling Request#queryStrings(), because path parameters are not query parameters.  This is correct, but doesn't really help in the real world - things will be much easier when path parameters can be accessed as easily as other request information.

## Strategies for using dynamic resource handlers ##
To the best of my knowledge, there are three ways in which to use dynamic resource handlers in Deadbolt 2:

1. Use a single `DynamicResourceHandler` that deals with all dynamic security
2. Use multiple `DynamicResourceHandlers` and use specific ones in specific places
3. Use a single `DynamicResourceHandler` that maps the name given in the annotation and creates a new, specific handler to deal with the request.

### 1. Use a single, potentially huge DRH ###
A friend of mine, sitting in a second-year university course discussion on object-oriented design, witnessed a student put up his hand and say, "I don't get it.  Why don't we just put everything in one big class?".  If you would ask a similar question, this is the approach for you.  For the rest of us, I think we can all appreciate that any DRH dealing with more than a couple of separate dynamic restrictions would get very large, very quickly.

### 2. Use multiple DRHs that are specified as needed ###
// todo

### 3. Use a single DRH façade that dispatches to other DRHs###
// todo