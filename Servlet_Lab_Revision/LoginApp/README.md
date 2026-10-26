# LoginApp

Lab-only hardcoded login: admin / 1234.

Compile and package from this application's folder in PowerShell:
```powershell
javac --release 8 -cp "$env:CATALINA_HOME\lib\servlet-api.jar" -d "webapp\WEB-INF\classes" "src\LoginServlet.java"
jar -cvf LoginApp.war -C webapp .
Copy-Item "LoginApp.war" "$env:CATALINA_HOME\webapps\" -Force
```
URL: `http://localhost:8080/LoginApp/`
