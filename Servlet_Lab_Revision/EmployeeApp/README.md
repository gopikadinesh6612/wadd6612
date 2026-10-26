# EmployeeApp

doGet displays the form; doPost displays submitted employee details.

Compile and package from this application's folder in PowerShell:
```powershell
javac --release 8 -cp "$env:CATALINA_HOME\lib\servlet-api.jar" -d "webapp\WEB-INF\classes" "src\EmployeeServlet.java"
jar -cvf EmployeeApp.war -C webapp .
Copy-Item "EmployeeApp.war" "$env:CATALINA_HOME\webapps\" -Force
```
URL: `http://localhost:8080/EmployeeApp/`
