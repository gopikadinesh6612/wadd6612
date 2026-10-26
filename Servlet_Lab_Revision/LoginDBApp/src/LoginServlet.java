import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/login_db", "root", "YOUR_MYSQL_PASSWORD");
                 PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?")) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) out.println("<h2>Login Successful!</h2>");
                    else out.println("<h2>Invalid Username or Password</h2>");
                }
            }
            out.println("<br><a href='index.html'>Go Back</a>");
        } catch (Exception e) {
            log("Login database operation failed", e);
            out.println("<h2>Database Error</h2><p>Check database setup, password, and logs.</p>");
        }
    }
}