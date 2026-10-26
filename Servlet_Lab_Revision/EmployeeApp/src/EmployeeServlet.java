import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<h2>Employee Details</h2>");
        out.println("<form method='post' action='employee'>");
        out.println("ID: <input name='id' required><br><br>");
        out.println("Name: <input name='name' required><br><br>");
        out.println("Salary: <input type='number' name='salary' required><br><br>");
        out.println("<input type='submit' value='Submit'></form>");
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<h2>Employee Details</h2>");
        out.println("ID: " + req.getParameter("id") + "<br>");
        out.println("Name: " + req.getParameter("name") + "<br>");
        out.println("Salary: " + req.getParameter("salary"));
    }
}