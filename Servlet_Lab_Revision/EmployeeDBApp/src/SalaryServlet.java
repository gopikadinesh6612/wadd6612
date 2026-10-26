import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SalaryServlet extends HttpServlet {
    Connection con;

    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_db", "root", "YOUR_MYSQL_PASSWORD");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<html><body><h2>Employee Salary Calculator</h2>");
        out.println("<form method='post' action='salary'>");
        out.println("Employee ID: <input name='id' required><br><br>");
        out.println("Employee Name: <input name='name' required><br><br>");
        out.println("Basic Salary: <input type='number' name='basic' min='0' step='any' required><br><br>");
        out.println("Allowance: <input type='number' name='allowance' min='0' step='any' required><br><br>");
        out.println("Deduction: <input type='number' name='deduction' min='0' step='any' required><br><br>");
        out.println("<input type='submit' value='Calculate and Save'></form>");
        out.println("<h3>Saved Employee Records</h3><table border='1' cellpadding='8'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Basic</th><th>Allowance</th><th>Deduction</th><th>Gross</th><th>Net</th></tr>");
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM employees")) {
            while (rs.next()) {
                out.println("<tr><td>" + rs.getString("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getDouble("basic_salary") + "</td>");
                out.println("<td>" + rs.getDouble("allowance") + "</td>");
                out.println("<td>" + rs.getDouble("deduction") + "</td>");
                out.println("<td>" + rs.getDouble("gross_salary") + "</td>");
                out.println("<td>" + rs.getDouble("net_salary") + "</td></tr>");
            }
        } catch (SQLException e) {
            log("Unable to display employee records", e);
            out.println("<tr><td colspan='7'>Unable to load records.</td></tr>");
        }
        out.println("</table></body></html>");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        try {
            double basic = Double.parseDouble(req.getParameter("basic"));
            double allowance = Double.parseDouble(req.getParameter("allowance"));
            double deduction = Double.parseDouble(req.getParameter("deduction"));
            if (basic < 0 || allowance < 0 || deduction < 0) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("<h3>Salary values cannot be negative.</h3>");
                return;
            }
            double gross = basic + allowance;
            double net = gross - deduction;
            String sql = "INSERT INTO employees (id, name, basic_salary, allowance, deduction, gross_salary, net_salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, id); ps.setString(2, name);
                ps.setDouble(3, basic); ps.setDouble(4, allowance);
                ps.setDouble(5, deduction); ps.setDouble(6, gross);
                ps.setDouble(7, net); ps.executeUpdate();
            }
            out.println("<html><body><h2>Employee Salary Details</h2>");
            out.println("Record saved successfully!<br>");
            out.println("Employee ID: " + id + "<br>Employee Name: " + name + "<br>");
            out.println("Basic Salary: " + basic + "<br>Allowance: " + allowance + "<br>");
            out.println("Deduction: " + deduction + "<br>Gross Salary: " + gross + "<br>");
            out.println("<h3>Net Salary: " + net + "</h3><a href='salary'>View Employees</a></body></html>");
        } catch (NumberFormatException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("<h3>Please enter valid salary numbers.</h3>");
        } catch (SQLException e) {
            log("Database operation failed", e);
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("<h3>Could not save employee details. Check server logs and employee ID.</h3>");
        }
    }

    public void destroy() {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            log("Unable to close database connection", e);
        }
    }
}