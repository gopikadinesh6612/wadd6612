# FeedbackApp

Displays feedback; does not store it.

Compile and package from this application's folder in PowerShell:
```powershell
javac --release 8 -cp "$env:CATALINA_HOME\lib\servlet-api.jar" -d "webapp\WEB-INF\classes" "src\FeedbackServlet.java"
jar -cvf FeedbackApp.war -C webapp .
Copy-Item "FeedbackApp.war" "$env:CATALINA_HOME\webapps\" -Force
```
URL: `http://localhost:8080/FeedbackApp/`
