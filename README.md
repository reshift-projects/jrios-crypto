"# jrios-crypto" 

"Habilitar la configuracion Seguridad JDK"

1. Descargar las siguientes librerias:    
    Java 8
    http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
    Java 7
    http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html

2. Reemplazar loca_policy.jar y US_export_policy.jar en la ruta de seguridad:

    %JAVA_HOME\jre\lib\security


"Servidor Cifrado"
1. Configuracion del Servidor Cifrado
> broker.properties (Leer el contenido, la seccion de cifrado para depositar los archivo necesarios)
2. Ejecutar el run.bat de la carpeta cripto-service-install-1.0-SNAPSHOT
> run.bat
3. La aplicacion se encuentra disponible para atender


"Cliente Socket"
1. Verificar la trama, ver el arhivo run.bat de la capeta socket-client
> socket-client\run.bat
2. La estructura de parametros se define en:

... com.novatronic.mock.Client <HOST> <PUERTO> text <cadena>

Donde

HOST: IP del servidor de cifrado

PUERTO: Puerto del servidor

text: especifica los datos en cadena para el cifrado del mismo

cadena: SE003AES009DATOS1234

	S: Tipo (S:Sincrono, A:Asicrono)
	
	003: lontitud de cabecera de [algoritmo]
	
	AES: Algoritmo
	
	009: lontitud de cabecera de [datos]
	
	DATOS1234: datos
	
