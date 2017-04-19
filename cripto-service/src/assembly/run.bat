set JAVA_HOME="C:\Program Files\Java\jdk1.7.0_79"
REM <HOST> <PUERTO> TEXT|FILE <cadena|ruta archivo>
%JAVA_HOME%\bin\java -cp .;.\libs\* com.novatronic.cripto.service.Server
pause
