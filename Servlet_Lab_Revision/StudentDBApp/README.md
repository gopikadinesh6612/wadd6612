# StudentDBApp

Adds and searches students in MySQL. Run database/setup.sql first.

Compile and package from this application's folder in PowerShell:
```powershell
javac --release 8 -cp "$env:CATALINA_HOME\lib\servlet-api.jar" -d "webapp\WEB-INF\classes" "src\DatabaseServlet.java"
jar -cvf StudentDBApp.war -C webapp .
Copy-Item "StudentDBApp.war" "$env:CATALINA_HOME\webapps\" -Force
```
URL: `http://localhost:8080/StudentDBApp/`
