# CalculatorApp

Performs arithmetic operations.

Compile and package from this application's folder in PowerShell:
```powershell
javac --release 8 -cp "$env:CATALINA_HOME\lib\servlet-api.jar" -d "webapp\WEB-INF\classes" "src\CalculatorServlet.java"
jar -cvf CalculatorApp.war -C webapp .
Copy-Item "CalculatorApp.war" "$env:CATALINA_HOME\webapps\" -Force
```
URL: `http://localhost:8080/CalculatorApp/`
