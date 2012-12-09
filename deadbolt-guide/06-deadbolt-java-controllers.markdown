# D2 Java Controllers #
If you like annotations in Java code, you're in for a treat.  If you don't, this may be a good time to consider the Scala version.

As with previous chapter, here is a a breakdown of all the Java annotation-driven interceptors available in D2 Java, with parameters, usages and tips and tricks.

## Static constraints ##
Static constraints, such as Restrict, are implemented entirely within D2 because it can finds all the information needed to determine authorisation automatically.  For example, if a constraint requires two roles, "foo" and "bar" to be present, the logic behind the Restrict constraint knows it just needs to check the roles of the current roles holder.

### RoleHolderPresent ###
todo

### RoleHolderNotPresent ###
todo

### Restrict ###
todo

### Restrictions ###
todo

### Unrestricted ###
todo

## Dynamic constraints ##
Dynamic constraints are, as far as D2 is concerned, completely arbitrary.  The logic behind the constraint comes entirely from a third party.

### Dynamic ###
todo

### Pattern ###
todo

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

