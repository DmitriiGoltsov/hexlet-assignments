package exercise.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.ArrayUtils;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        String pathAsString = "src/main/resources/users.json";
        String content = getFileContent(pathAsString);

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> users = mapper.readValue(content, List.class);
        Comparator<Map<String, Object>> comparator =
                Comparator.comparingInt(x -> Integer.parseInt(String.valueOf(x.get("id"))));
        return users.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        // END
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException {

        // BEGIN
        PrintWriter pw = response.getWriter();

        List<Map<String, Object>> users = getUsers();
        StringBuilder result = new StringBuilder();

        result.append("<table>").append("<tr>");

        for (var user : users) {

            String userId = String.valueOf(user.get("id"));
            String userName = String.valueOf(user.get("firstName"));
            String lastName = String.valueOf(user.get("lastName"));
            String fullName = String.join(" ", userName, lastName);
            result.append("<td>").append(userId).append("</td>");
            result.append("<td>");
            result.append("<a href=\"/users/").append(userId).append("\">")
                    .append(fullName).append("</a>");
            result.append("</td>");

        }

        result.append("/tr").append("/table");

        pw.write(result.toString());
        pw.close();
        // END
    }

    private void showUser(HttpServletRequest request,
                         HttpServletResponse response,
                         String id)
                 throws IOException {

        // BEGIN
        PrintWriter pw = response.getWriter();

        List<Map<String, Object>> users = this.getUsers();
        Optional<Map<String, Object>> usersOptional = users.stream()
                .filter(user -> user.get("id").equals(id))
                .findAny();

        if (usersOptional.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
        } else {
            StringBuilder result = new StringBuilder();
            Map<String, Object> foundUser = usersOptional.get();

            result.append("User: \n");
            result.append("     ID: ").append(foundUser.get("id")).append("\n");
            result.append("     Name: ").append(foundUser.get("firstName")).append("\n");
            result.append("     Lastname: ").append(foundUser.get("lastName")).append("\n");
            result.append("     Email: ").append(foundUser.get("email")).append("\n");

            pw.write(result.toString());
        }

        pw.close();
        // END
    }

    private String getFileContent(String path) {
        Path fullPath = Paths.get(path).toAbsolutePath().normalize();
        try {
            return Files.readString(fullPath);
        } catch (IOException | OutOfMemoryError | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
