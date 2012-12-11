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

## Customising the inputs of annotation-driven actions ##
One of the problems with D2's annotations is they require strings to specify, for example, role names or pattern values.  It would be far safer to use enums, but this is not possible for a module - it would completely kill the generic applicability of the annotations.  If D2 shipped with an enum containing roles, how would you extend it?  You would be stuck with whatever was specified, or forced to fork the codebase and customise it.  Similarly, annotations can neither implement, extend or be extended.

To address this situation, D2 has four constraints whose inputs can be customised to some degree.  The trick lies, not with inheritence, but delegation and wrapping.  The constraints are

 * Restrict
 * Restrictions
 * Dynamic
 * Pattern

Here, I'll explain how to customise the Restrict constraint to use enums as annotation parameters, but the principle is the same for each constraint.

To start, create an enum that represents your roles.

    public enum MyRoles implements Role {
        foo,
        bar,
        hurdy;

        @Override
        public String getRoleName() {
            return name();
        }
    }

Next, create a new annotation to drive your custom version of Restrict.  Note that an array of `MyRoles` values can be placed in the annotation.  The standard `Restrict` annotation is also present to provide further configuration.  This means your customisations are minimised. 

    @With(CustomRestrictAction.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Documented
    @Inherited
    public @interface CustomRestrict {
        MyRoles[] value();

        Restrict config();
    }

The code above contains `@With(CustomRestrictAction.class)` in order to specify the action that should be triggered by the annotation.  This action can be implemented as follows. 

    public class CustomRestrictAction extends Action<CustomRestrict> {
        
        @Override
        public Result call(Http.Context context) throws Throwable {
            final List<String> roleNames = new ArrayList<String>();
            // get the string values from the enums
            for (MyRoles role : configuration.value()) {
                roleNames.add(role.getRoleName());
            }

            // Programatically construct the standard action for Restrict
            RestrictAction restrictAction = new RestrictAction() {
                @Override
                public String[] getRoleNames() {
                    // Return the roles as strings
                    return roleNames.toArray(new String[roleNames.size()]);
                }
            };
            // Get the nested Restrict annotation from CustomRestrict and set it as the configuration of the standard action
            restrictAction.configuration = configuration.config();
            // Pass the delegate to the nested action, to ensure forward calls work correctly
            restrictAction.delegate = this.delegate;
            // Invoke the action
            return restrictAction.call(context);
        }
    }

Pretty simple stuff - the code is basically converting an array of enum values to an array of strings.

To use your custom annotation, you apply it as you would any other D2 annotation.

    @CustomRestrict(value = {MyRoles.foo, MyRoles.bar}, config = @Restrict(""))
    public static Result customRestrictOne() {
        return ok(accessOk.render());
    }

Each customisable action has one or more extension points.  These are

 * RestrictAction - String[] getRoleNames()
 * RestrictionsAction - List<String[]> getRoleGroups()
 * Dynamic - String getValue(), String getMeta()
 * Pattern - String getValue()
 