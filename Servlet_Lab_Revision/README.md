# Servlet Lab Revision Pack

Contains separate folders for 10 applications. Each application has its own `src` Java file, `webapp` folder, and `webapp/WEB-INF/web.xml`. Database applications also have separate `database/setup.sql` scripts.

## Setup
- Intended for Apache Tomcat 9 and Java.
- Set `$env:CATALINA_HOME` to your Tomcat 9 installation folder.
- MySQL Connector/J must be available to Tomcat (e.g. in Tomcat's `lib` folder).
- Replace `YOUR_MYSQL_PASSWORD` in database servlet code with your local MySQL password.
- Run the SQL files in MySQL Workbench, not in PowerShell.
- These examples use the default Java package and `web.xml` mappings (no `@WebServlet` annotations).

Applications:
1. HelloApp
2. LoginApp (hardcoded credentials)
3. StudentRegistrationApp
4. CalculatorApp
5. EmployeeApp (`doGet` + `doPost`)
6. FeedbackApp
7. GradeApp (one mark)
8. StudentDBApp (add/search students)
9. LoginDBApp (database login)
10. EmployeeDBApp (salary calculator + database)

Database setup order:
- `StudentDBApp/database/setup.sql`
- `LoginDBApp/database/setup.sql`
- `EmployeeDBApp/database/setup.sql`

The hardcoded/plain-text login examples are for lab practice only, not production use.
