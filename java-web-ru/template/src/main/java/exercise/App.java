package exercise;

import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.List;
import io.javalin.http.NotFoundResponse;
import exercise.model.User;
import exercise.dto.users.UserPage;
import exercise.dto.users.UsersPage;
import java.util.Collections;

public final class App {

    // Каждый пользователь представлен объектом класса User
    private static final List<User> USERS = Data.getUsers();

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });

        // BEGIN
        app.get("/users", ctx -> {
            UsersPage usersPage = new UsersPage(USERS);

            ctx.render("users/index.jte", Collections.singletonMap("usersPage", usersPage));
        });
        // END

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        app.get("/users/{id}", ctx -> {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            User user = USERS.stream()
                    .filter(user1 -> id.equals(user1.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("User with id " + id + " was not found!"));

            UserPage userPage = new UserPage(user);

            ctx.render("users/show.jte", Collections.singletonMap("userPage", userPage));
        });

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
