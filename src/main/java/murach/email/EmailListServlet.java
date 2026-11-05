package murach.email;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import murach.business.User;
import murach.data.UserDB;

public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String url = "/index.html";

        // Get current action
        String action = request.getParameter("action");

        if (action == null) {
            action = "join";
        }

        // Perform action and set URL
        if (action.equals("join")) {

            url = "/index.html";

        } else if (action.equals("add")) {

            // Get parameters from request
            String firstName =
                    request.getParameter("firstName");

            String lastName =
                    request.getParameter("lastName");

            String email =
                    request.getParameter("email");

            // Create User object
            User user = new User(
                    firstName, lastName, email
            );

            // Save User object
            UserDB.insert(user);

            // Pass User object to JSP
            request.setAttribute("user", user);

            url = "/thanks.jsp";
        }

        // Forward to selected page
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}
