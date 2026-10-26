import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<h2>Registration Successful</h2>");
        out.println("Roll Number: " + req.getParameter("roll") + "<br>");
        out.println("Name: " + req.getParameter("name") + "<br>");
        out.println("Department: " + req.getParameter("dept") + "<br>");
        out.println("Email: " + req.getParameter("email"));
    }
}