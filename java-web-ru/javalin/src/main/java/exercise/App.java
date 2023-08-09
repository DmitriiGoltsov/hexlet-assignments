package exercise;

// Импортируем зависимости, необходимые для работы приложения
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

import org.thymeleaf.TemplateEngine;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import exercise.controllers.RootController;
import exercise.controllers.ArticleController;

public final class App {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "8000");
        return Integer.valueOf(port);
    }

    // Javalin поддерживает работу с шаблонизатором thymeleaf
    // Настраиваем шаблонизатор
    private static TemplateEngine getTemplateEngine() {
        // Создаём инстанс движка шаблонизатора
        TemplateEngine templateEngine = new TemplateEngine();
        // Добавляем к нему диалекты
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new Java8TimeDialect());
        // Настраиваем преобразователь шаблонов, так, чтобы обрабатывались
        // шаблоны в директории /templates/
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        // Добавляем преобразователь шаблонов к движку шаблонизатора
        templateEngine.addTemplateResolver(templateResolver);

        return templateEngine;
    }

    // Метод добавляет маршруты в переданное приложение
    private static void addRoutes(Javalin app) {
        // Для GET-запроса на маршрут / будет выполняться
        // обработчик welcome в контроллере RootController
        app.get("/", RootController.welcome);

        // При помощи методов routes() и path() маршруты можно группировать

        // BEGIN
        app.routes(() -> {
            path("articles", () -> {
                // GET /articles — список всех статей
                get(ArticleController.listArticles);
                // POST /articles — создание новой статьи
                post(ArticleController.createArticle);
                path("new", () -> {
                    // GET /articles/new — вывод формы создания новой статьи
                    get(ArticleController.newArticle);
                });
                path("{id}", () -> {
                    // GET /articles/{id} — просмотр конкретной статьи
                    get(ArticleController.showArticle);
                });
                path("{id}/edit", () -> {
                    // GET /articles/{id}/edit — вывод формы редактирования статьи
                    get(ArticleController.editArticle);
                    // POST /articles/{id}/edit — обновление данных статьи
                    post(ArticleController.updateArticle);
                });
                path("{id}/delete", () -> {
                    // GET /articles/{id}/delete — вывод страницы с подтверждением удаления статьи
                    get(ArticleController.deleteArticle);
                    // POST /articles/{id}/delete — удаление статьи
                    post(ArticleController.destroyArticle);
                });
            });
        });
        // END
    }

    public static Javalin getApp() {

        // Создаём приложение
        Javalin app = Javalin.create(config -> {
            // Включаем логгирование
            config.plugins.enableDevLogging();
            // Подключаем настроенный шаблонизатор к фреймворку
            JavalinThymeleaf.init(getTemplateEngine());
        });

        // Добавляем маршруты в приложение
        addRoutes(app);

        // Обработчик before запускается перед каждым запросом
        // Устанавливаем атрибут ctx для запросов
        app.before(ctx -> {
            ctx.attribute("ctx", ctx);
        });

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(getPort());
    }
}