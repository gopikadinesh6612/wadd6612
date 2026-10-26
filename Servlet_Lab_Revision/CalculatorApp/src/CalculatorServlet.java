import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CalculatorServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        double a = Double.parseDouble(req.getParameter("a"));
        double b = Double.parseDouble(req.getParameter("b"));
        String op = req.getParameter("op");
        switch (op) {
            case "+": out.println("Result: " + (a + b)); break;
            case "-": out.println("Result: " + (a - b)); break;
            case "*": out.println("Result: " + (a * b)); break;
            case "/":
                if (b == 0) out.println("Cannot divide by zero");
                else out.println("Result: " + (a / b));
                break;
        }
    }
}