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
import org.apache.commons.lang3.StringUtils;
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
                .id.equalTo(Long.parseLong(id))
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
                .check(it -> it.getFirstName().length() > 0, "First name can not be empty")
                .check(it -> it.getLastName().length() > 0, "Last name can not be empty")
                .check(it -> EmailValidator.getInstance().isValid(it.getEmail()), "Should be valid email")
                .check(it -> StringUtils.isNumeric(it.getPassword()), "Password must contains only digits")
                .check(it -> it.getPassword().length() >= 4, "Password must contain at least 4 characters")
                .get();

        userToCreate.save();
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
                .id.equalTo(Long.parseLong(id))
                .findOne();

        user.delete();
        // END
    };
}
