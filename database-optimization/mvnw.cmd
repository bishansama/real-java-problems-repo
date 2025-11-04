@ECHO OFF
SETLOCAL
SET BASE_DIR=%~dp0
SET WRAPPER_DIR=%BASE_DIR%\.mvn\wrapper
SET WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar

IF NOT EXIST "%WRAPPER_JAR%" (
  IF NOT EXIST "%WRAPPER_DIR%" (
    mkdir "%WRAPPER_DIR%"
  )
  SET WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar
  ECHO Downloading Maven Wrapper jar from %WRAPPER_URL%
  powershell -Command "Invoke-WebRequest -Uri '%WRAPPER_URL%' -OutFile '%WRAPPER_JAR%'"
)

SET JAVA_EXE=java
"%JAVA_EXE%" -Dmaven.multiModuleProjectDirectory="%BASE_DIR%" -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*