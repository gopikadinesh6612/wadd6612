# GradeApp

Calculates a grade for one mark.

Compile and package from this application's folder in PowerShell:
```powershell
javac --release 8 -cp "$env:CATALINA_HOME\lib\servlet-api.jar" -d "webapp\WEB-INF\classes" "src\MarksServlet.java"
jar -cvf GradeApp.war -C webapp .
Copy-Item "GradeApp.war" "$env:CATALINA_HOME\webapps\" -Force
```
URL: `http://localhost:8080/GradeApp/`
