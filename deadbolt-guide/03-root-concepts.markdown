# Root concepts #

D2 is centered around a single idea - restricting access to a resource to a specific group of users.  I've had several e-mails from developers who have thought that Deadbolt had a "restrict from" approach, and therefore could not understand why the authorisation was failing so spectacularly; to forestall future questions about this, I want to make it completely clear - D2 uses "restrict to".  For example, a controller action annotated with `@Restrict("foo")` would only allow users with the "foo" role to access the method.

Two mechanisms are provided to declare these restrictions - one at the template level and another at the controller level.  In each case, there are differences between how these are applied in Java and Scala applications, so specific details will be provided in later chapters.

## Template-level restrictions ##
For a Play 2 application that uses server-side rendering, D2 provides several template tags that will conceal or reveal DOM elements based on your specifications.

A couple of basic use cases are
* Only displayed a "Log in" link if there is no user present
* Even if a user is logged in, only display an "Administration" link if the user has administrative privileges

However, it is **extremely** important to note that using these tags will only give you a cleaner UI, one that is better tailored to the user's privileges.  It will **not** secure your server-side code in any way except - possibly - obscurity.  Server-side routes can be invoked from outside of templates by changing the URL in the browser's address bar, using command-line tools such as cURL and many other ways.

If you have seen the original Dawn Of The Dead (Romero, 1978), you may remember the protagonists concealing the entrance to their living quarters using a panel of painted hardboard.  There are no additional defensive layers behind this concealment.  When a zombified protagonist breaks through the hardboard, knowing there is something he wants on the other side, all security is gone.  Minutes later, there's blood everywhere and the survivors have to flee.

Template security is like painted hardboard - the features it offers are certainly nice to have, but a further level of defensive depth is required.  For this, you need controller action security - otherwise, the zombies will get you.

## Controller-level restrictions ##
The controller layer is most vunerable part of your application to external attack, because that is the part that's visible to whichever networks it is on.  Attack in this sense may be a concious attack on your system, or inadvertant damage caused by unauthorised users who are otherwise authenticated in your system.  D2 can't help with the first type, but it's ideal for the second.

Controller authorisation blocks or allows access to a method.  Whereas template restrictions are essentially a boolean evaluation - "if user satisfies these conditions, then...", controller authorisation is quite a bit more powerful.  Specifically, while an authorised result is generated from your application code, unauthorised results can be customised as required; you can return any status code you like along with any content you like.  If you're feeling particularly nasty, why not send a 302 redirect to a not-suitable-for-work website?  If you want to, the option is there.

## Actors ##
D2 has three interfaces which can be used to hook authorisation into your application - `RoleHolder`, `Role` and `Permission`.

### RoleHolder ###
The core actor of D2 is the `be.objectify.deadbolt.core.models.RoleHolder` interface.  This should be implemented by whatever your application considers to be an authorisable entity - in other words, a User.  Its sole purpose is to give access to the rights and permissions held by the user.

    public interface RoleHolder
    {
    	List<? extends Role> getRoles();

        List<? extends Permission> getPermissions();
    }

While it is possible to not implement the `Role` and `Permission` interfaces - for example, if you only use dynamic security - it is highly recommended that you always implement `RoleHolder`.

The name `RoleHolder` is somewhat of an anacronysm, held over from the early days of Deadbolt when it only had two options - restrict, and restrictions.  Both of these used (and still use) the roles of a user to determine access.

### Role ###
A `Role` is essentially a wrapper around a string.  It is the primary actor for the `Restrict` and `Restrictions` constraints.  Role A is equal to Role B, even if they are different objects, if they have exactly the same name.  Role names should be case-aware, so "Admin" is not the same as "admin".

As `Role` is an interface, it can be implemented as a class or an enum.

If you do not require roles in your application, you do not need to implement this interface - just return an empty list from `RoleHolder#getRoles`.

### Permission ###
A `Permission`, just like a `Role`, is essentially a wrapper around a string.  It is the primary actor for `Pattern` constraints, and has different interpretations depending on the type of pattern constraint that is being applied to it.  For example, a PatternType.EQUALITY test would perform a case-sensitive comparison between a user's permissions and the test value.  A PatternType.REGEX would assess a user's permissions in the context of regular expressions, and so on.

As `Permission` is an interface, it can be implemented as a class or an enum.

If you do not require permissions in your application, you do not need to implement this interface - just return an empty list from `RoleHolder#getPermissions`.
