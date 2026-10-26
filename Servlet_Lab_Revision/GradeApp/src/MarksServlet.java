import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MarksServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String name = req.getParameter("name");
        int mark = Integer.parseInt(req.getParameter("mark"));
        if (mark < 0 || mark > 100) {
            out.println("Mark must be between 0 and 100.");
            return;
        }
        String grade;
        if (mark >= 90) grade = "A";
        else if (mark >= 75) grade = "B";
        else if (mark >= 50) grade = "C";
        else grade = "Fail";
        out.println("<h2>Student Result</h2>");
        out.println("Name: " + name + "<br>Mark: " + mark + "<br>Grade: " + grade);
    }
}