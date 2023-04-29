package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        String parameter = request.getParameter("search");
        List<String> companies = getCompanies();
        PrintWriter writer = response.getWriter();
        StringBuilder result = new StringBuilder();

        if (parameter == null || parameter.isEmpty()) {
            for (String company : companies) {
                result.append(company).append("\n");
            }
        } else {
            List<String> filteredCompanies = companies.stream()
                    .filter(company -> company.contains(parameter))
                    .toList();

            if (filteredCompanies.isEmpty()) {
                writer.write("Companies not found");
            } else {
                for (String company : filteredCompanies) {
                    result.append(company).append("\n");
                }
            }
        }

        writer.write(result.toString());
        writer.close();
        // END
    }
}
