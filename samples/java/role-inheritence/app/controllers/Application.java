package controllers;

import be.objectify.deadbolt.actions.Dynamic;
import com.avaje.ebean.Ebean;
import models.SecurityRole;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Application extends Controller
{
    public static Result index()
    {
        return ok(index.render(User.findByUserName("foo"),
                               SecurityRole.find.all()));
    }

    @Dynamic(value = "minimumLevelRequired", meta="admin")
    public static Result adminFunction()
    {
        return ok("Access ok");
    }

    @Dynamic(value = "minimumLevelRequired", meta="editor")
    public static Result editorFunction()
    {
        return ok("Access ok");
    }

    @Dynamic(value = "minimumLevelRequired", meta="viewer")
    public static Result viewerFunction()
    {
        return ok("Access ok");
    }

    public static Result changeRole()
    {
        User user = User.findByUserName("foo");
        Map<String, String[]> parameters = request().body().asFormUrlEncoded();
        String roleName = parameters.get("roleName")[0];
        SecurityRole role = SecurityRole.findByRoleName(roleName);

        user.roles.clear();
        Ebean.deleteManyToManyAssociations(user,
                                           "roles");
        user.save();

        user.update();
        user.roles = new ArrayList<SecurityRole>(Arrays.asList(role));
        user.save();
        user.saveManyToManyAssociations("roles");

        return ok(role.getRoleName());
    }
}