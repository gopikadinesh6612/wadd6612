# LoginDBApp

Verifies login credentials against MySQL. Lab-only plaintext password example.

Compile and package from this application's folder in PowerShell:
```powershell
javac --release 8 -cp "$env:CATALINA_HOME\lib\servlet-api.jar" -d "webapp\WEB-INF\classes" "src\LoginServlet.java"
jar -cvf LoginDBApp.war -C webapp .
Copy-Item "LoginDBApp.war" "$env:CATALINA_HOME\webapps\" -Force
```
URL: `http://localhost:8080/LoginDBApp/`
