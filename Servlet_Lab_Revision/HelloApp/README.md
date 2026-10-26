# HelloApp

Reads a name from a POST form and displays a greeting.

Compile and package from this application's folder in PowerShell:
```powershell
javac --release 8 -cp "$env:CATALINA_HOME\lib\servlet-api.jar" -d "webapp\WEB-INF\classes" "src\HelloServlet.java"
jar -cvf HelloApp.war -C webapp .
Copy-Item "HelloApp.war" "$env:CATALINA_HOME\webapps\" -Force
```
URL: `http://localhost:8080/HelloApp/`
