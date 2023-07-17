package exercise.controllers;

import io.javalin.http.Handler;
import java.util.List;
import java.util.Map;
import io.javalin.validation.Validator;
import io.javalin.validation.ValidationError;
import io.javalin.validation.JavalinValidation;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.lang3.StringUtils;
import io.javalin.http.Context;

import exercise.domain.User;
import exercise.domain.query.QUser;

public final class UserController {

    private static void removeFlashMessage(Context ctx) {
        ctx.sessionAttribute("flash", null);
    }

    public static Handler listUsers = ctx -> {

        List<User> users = new QUser()
            .orderBy()
                .id.asc()
            .findList();

        ctx.attribute("users", users);
        ctx.render("users/index.html");
        removeFlashMessage(ctx);
    };

    public static Handler showUser = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        User user = new QUser()
            .id.equalTo(id)
            .findOne();

        ctx.attribute("user", user);
        ctx.render("users/show.html");
    };

    public static Handler newUser = ctx -> {

        ctx.attribute("errors", Map.of());
        ctx.attribute("user", Map.of());
        ctx.render("users/new.html");
    };

    public static Handler createUser = ctx -> {
        // BEGIN
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        Validator<String> nameValidator = ctx.formParamAsClass("firstName", String.class)
                .check(item -> !item.isEmpty(), "Name should not be empty!");

        Validator<String> surnameValidator = ctx.formParamAsClass("lastName", String.class)
                .check(item -> !item.isEmpty(), "Surname should not be empty!");

        Validator<String> emailValidator = ctx.formParamAsClass("email", String.class)
                .check(item -> EmailValidator.getInstance().isValid(email),
                        "Email should comply with email pattern");

        Validator<String> passwordValidator = ctx.formParamAsClass("password", String.class)
                .check(item -> item.length() >= 4 && StringUtils.isNumeric(password),
                        "Your password has to contain 4 or more symbols which have to be numeric!");

        Map<String, List<ValidationError<? extends Object>>> errors = JavalinValidation.collectErrors(
                nameValidator,
                surnameValidator,
                emailValidator,
                passwordValidator
                );

        if (!errors.isEmpty()) {
            ctx.status(422);
            ctx.attribute("errors", errors);

            User invalidUser = new User(firstName, lastName, email, password);
            ctx.attribute("user", invalidUser);

            ctx.render("users/new.html");
            return;
        }

        User newUser = new User(firstName, lastName, email, password);
        newUser.save();

        ctx.attribute("flash", "A new user has been created successfully!");
        ctx.redirect("/users");
        // END
    };
}
