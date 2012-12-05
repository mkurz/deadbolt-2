# Using Deadbolt 2 with Play 2 Java projects #

Deadbolt 2 for Java provides an idiomatic API for dealing with Java controllers and templates rendered from Java controllers in Play 2 applications.  It takes advantage of the features offered by Play 2 such as access to the HTTP context - vital, of course, for a framework that says it embraces HTTP - to give access to the current request and session, and the annotation-driven interceptor support.

## The Deadbolt Handler ##
For any module - or framework - to be useable, it must provide a mechanism by which it can be hooked into your application.  For D2, the central hook is the `be.objectify.deadbolt.java.DeadboltHandler` interface.  The four methods defined by this interface are crucial to Deadbolt - for example, `DeadboltHandler#getRoleHolder` gets the current user (or subject, to use the correct security terminology), whereas `DeadboltHandler#onAccessFailure` is used to generate a response when authorisation fails.

Before we take a look at these all-important methods, let's have a quick look at a day in the life of a HTTP request.

A HTTP request has a life cycle.  At a high level, it is

* Sent
* Received
* Processed
* Answered

The _processed_ point is where our web applications live.  In a sense, the high level life cycle is repeated again here, as the request is sent from the container into the application, received by the app, processed and answered.  We're now in navel-gazing territory, so let's cut to the chase and say that interception occurs _between_ the received and processed phases _within_ the high-level processed phase.

### Result beforeRoleCheck(Http.Context context) ###
D2 tries reasonably hard to be unobtrusive.  This is quite a lofty goal for a piece of code whose sole purpose is deny access to your application.  To achieve this goal, D2 invokes the beforeRoleCheck method of your DeadboltHandler

### RoleHolder getRoleHolder(Http.Context context) ###

### Result onAccessFailure(Http.Context context, String content) ###

### DynamicResourceHandler getDynamicResourceHandler(Http.Context context) ###

## Deferring method-level annotation-driven actions ##
Play 2 executes method-level annotations before controller-level annotations. This can cause issues when, for example, you want a particular action to be applied for method and before the method annotations. A good example is `Security.Authenticated(Secured.class)`, which sets a user’s user name for `request().username()`. Combining this with method-level annotations that require a user would fail, because the user would not be present at the time the method interceptor is invoked.

One way around this is to apply `Security.Authenticated` on every method, which violates DRY and causes bloat.

    public class DeferredController extends Controller {

        @Security.Authenticated(Secured.class)
        @Restrict(value="admin")
        public static Result someAdminFunction() {
            return ok(accessOk.render());
        }

        @Security.Authenticated(Secured.class)
        @Restrict(value="editor")
        public static Result someEditorFunction() {
            return ok(accessOk.render());
        }
    }

A better way is to set the `deferred` parameter of the D2 annotation to `true`, and then use `@DeferredDeadbolt` at the controller level to execute the method-level annotations at controller-annotation time. Since annotations are processed in order of declaration, you can specify `@DeferredDeadbolt` after `@Security.Authenticated` and so achieve the desired effect.

    @Security.Authenticated(Secured.class)
    @DeferredDeadbolt
    public class DeferredController extends Controller {

        @Restrict(value="admin", deferred=true)
        public static Result someAdminFunction() {
            return ok(accessOk.render());
        }

        @Restrict(value="admin", deferred=true)
        public static Result someAdminFunction() {
            return ok(accessOk.render());
        }
    }

