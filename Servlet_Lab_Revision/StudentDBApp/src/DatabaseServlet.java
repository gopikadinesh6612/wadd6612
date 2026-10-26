import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DatabaseServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String action = req.getParameter("action");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student", "root", "YOUR_MYSQL_PASSWORD")) {
                if ("add".equals(action)) {
                    String name = req.getParameter("name");
                    try (PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO student (id, name) VALUES (?, ?)")) {
                        ps.setInt(1, id);
                        ps.setString(2, name);
                        ps.executeUpdate();
                        out.println("<h2>Student Added Successfully!</h2>");
                    }
                } else if ("search".equals(action)) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "SELECT * FROM student WHERE id = ?")) {
                        ps.setInt(1, id);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                out.println("<h2>Student Found</h2>");
                                out.println("ID: " + rs.getInt("id") + "<br>");
                                out.println("Name: " + rs.getString("name"));
                            } else out.println("<h2>Student Not Found</h2>");
                        }
                    }
                }
            }
            out.println("<br><a href='index.html'>Go Back</a>");
        } catch (Exception e) {
            log("Student database operation failed", e);
            out.println("<h2>Database Error</h2><p>Check database setup, password, and logs.</p>");
            out.println("<a href='index.html'>Go Back</a>");
        }
    }
}