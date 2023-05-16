package exercise.controllers;

import io.javalin.core.validation.BodyValidator;
import io.javalin.core.validation.ValidationError;
import io.javalin.http.Context;
import io.javalin.apibuilder.CrudHandler;
import io.ebean.DB;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import exercise.domain.User;
import exercise.domain.query.QUser;
import org.apache.commons.validator.routines.EmailValidator;

public class UserController implements CrudHandler {

    public void getAll(Context ctx) {
        // BEGIN
        List<User> users = new QUser()
                .orderBy()
                .id.asc()
                .findList();

        String json = DB.json().toJson(users);
        ctx.attribute("users", users);
        ctx.json(json);
        // END
    };

    public void getOne(Context ctx, String id) {

        // BEGIN
        User user = new QUser()
                .id.equalTo(id)
                .findOne();

        String json = DB.json().toJson(user);
        ctx.attribute("user", user);
        ctx.json(json);
        // END
    };

    public void create(Context ctx) {

        // BEGIN
        String body = ctx.body();
        User userToCreate = DB.json().toBean(User.class, body);

        BodyValidator<User> userBodyValidator =  ctx.bodyValidator(User.class);

        userBodyValidator
                .check(user -> !user.getFirstName().isEmpty(), "New user should have a name!")
                .check(user -> !user.getLastName().isEmpty(), "New user should have a surname!")
                .check(user -> EmailValidator.getInstance().isValid(user.getEmail()), "Email you tried to use is not valid!")
                .check(user -> {
                    Predicate<String> predicate = x -> x.contains("*") && x.length() > 4;
                    return predicate.test(user.getEmail());
                }, "Password has to contain at least five symbols including at least one \"*\"!")
                .get();

        Map<String, List<ValidationError<User>>> errors = userBodyValidator.errors();

        user.save();
        // END
    };

    public void update(Context ctx, String id) {
        // BEGIN
        String body = ctx.body();
        User user = DB.json().toBean(User.class, body);

        user.setId(id);
        user.update();
        // END
    };

    public void delete(Context ctx, String id) {
        // BEGIN
        User user = new QUser()
                .id.equalTo(id)
                .findOne();

        user.delete();
        // END
    };
}
