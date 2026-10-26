import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FeedbackServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<h2>Thank You for Your Feedback!</h2>");
        out.println("Name: " + req.getParameter("name") + "<br>");
        out.println("Feedback: " + req.getParameter("message") + "<br>");
        out.println("Rating: " + req.getParameter("rating"));
    }
}