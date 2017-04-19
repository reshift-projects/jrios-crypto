set JAVA_HOME="C:\Program Files\Java\jdk1.7.0_79"
REM <HOST> <PUERTO> TEXT|FILE <cadena|ruta archivo>
%JAVA_HOME%\bin\java -cp .;commons-io-2.0.1.jar;socket-client-0.0.1-SNAPSHOT.jar com.novatronic.mock.Client localhost 8888 text SE003AES009DATOS1234
pause
