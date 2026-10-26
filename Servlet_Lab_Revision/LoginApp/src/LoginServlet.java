import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        if ("admin".equals(user) && "1234".equals(pass))
            out.println("<h2>Login Successful</h2>");
        else
            out.println("<h2>Invalid Username or Password</h2>");
    }
}