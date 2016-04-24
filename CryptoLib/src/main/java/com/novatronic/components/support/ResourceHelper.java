/*
* Elaborado por: ofernandez@novatronic.com
* 19/Feb/2013
* Novatronic S.A.C. Todos los derechos reservados
*/
package com.novatronic.components.support;

import java.io.File;
import java.net.URL;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* Esta clase prove&eacute; un metodo para realizar la busqueda de recursos a nivel del
* contenedor.<br/><br/>
* Es una pr&aacute;ctica usual colocar los archivos de
* configuracin y cargados en el classpath, pero los distintos entornos de
* ejecuci&oacute;n no tienen una forma homog&eacute;nea de obtener dicho recurso. Para
* esto es importante realizar la ubicacin de estos por etapas o ubicaciones posibles.
* Esta clase provee dicho mecanismo.
*
* @author ofernandez
* @since 1.0
* @version 1.0
*/
public class ResourceHelper {

    public static final Logger log = LoggerFactory.getLogger(ResourceHelper.class);
    private static final String MSG_CONTEXT_CL = "1. Buscando recurso a traves de el Context Classloader";
    private static final String MSG_CLASS_CL = "2. Buscando recurso a traves del Classloader de Clases";
    private static final String MSG_SYSTEM_CL = "3. Buscando recurso a traves del System Classloader";
    private static final String MSG_SYSTEM_PATH = "4. Buscando el recurso en el Directorio de Ejecucion o en la ruta especificada";

    /**
     * Ubica y obtiene un recurso en el orden siguiente:
     * <ol>
     * <li>Ubic&aacute;ndolo a trav&eacute;s del Context ClassLoader</li>
     * <li>Ubic&aacute;ndolo a trav&eacute;s del ClassLoader de Clases</li>
     * <li>Ubic&aacute;ndolo a trav&eacute;s del System ClassLoader</li>
     * <li>Ubic&aacute;ndolo en el directorio de ejecuci&oacute;n</li>
     * </ol>
     * El m&eacute;todo 4 permite obtener un archivo en una ubicaci&oacute;n
     * distinta al classpath.<br/><br/>
     * Seg&uacute;n lo anterior, las siguientes llamadas son correctas:
     * <ol>
     * <li>{@code URL resource = ResourceHelper.find("config.properties")}<br/>Ubica el archivo
     * en el classpath asumiendo que no esta ubicado dentro de ning&uacute;n paquete o
     * directorio. En caso no encuentrarlo en los 3 primeros pasos lo buscar&aacute; en el
     * directorio de ejecuci&oacute;n directamente</li>
     * <li>{@code URL resource = ResourceHelper.find("config/config.properties")}<br/>De
     * manera similar que el ejemplo anterior, realiza la b&uacute;squeda mediante los 3 
     * primeros pasos, pero en el 4to paso se buscar&aacute; a partir del directorio de
     * ejecuci&oacute;n dentro de la carpeta "config"</li>
     * <li>{@code URL resource = ResourceHelper.find("/home/config/config.xml")}<br/>De
     * manera similar que el primer ejemplo, realiza la b&uacute;squeda mediante los 3 
     * primeros pasos, pero en el 4to paso se buscar&aacute; a partir de la ruta pasada
     * como par&aacute;metro</li>
     * </ol>
     *
     * @param path La ruta a buscar a partir d elos pasos mencionados
     * @return Un objeto {@code URL} que representa el objeto buscado o {@code NULL} en
     * caso no encontrarlo
     */
    public static URL find(String path) {
        URL url;
        File file;

        log.info("Recurso por buscar:{}", path);
        url = findResource(MSG_CONTEXT_CL,Thread.currentThread().getContextClassLoader(), path);
        if (url != null) {
            return url;
        }
        
        url = findResource(MSG_CLASS_CL,ResourceHelper.class.getClassLoader(), path);
        if (url != null) {
            return url;
        }
        
        url = findResource(MSG_SYSTEM_CL,ClassLoader.getSystemClassLoader(), path);
        if (url != null) {
            return url;
        }
        
        log.debug(MSG_SYSTEM_PATH);
        file = new File(path);
        if (file.exists()) {
            try {
                url = file.toURI().toURL();
                return url;
            } catch (Exception ex) {
                log.warn("No fue posible obtener el recurso a partir de:" + path, ex);
                return null;
            }
        } else {
            return null;
        }
    }
    
    private static URL findResource(String message, ClassLoader classLoader, String path){
        URL url = null;
        
        log.debug(message);
        if (classLoader != null) {
            url = classLoader.getResource(path);
        }
        
        return url;
    }
    
    /**
     * Obtiene un archivo properties del classpath. Para realizar esto, hace uso del
     * metodo {@link #find(java.lang.String)  }
     * 
     *
     * @param path
     * @return
     */
    public static Properties findAsProperties(String path) {
        Properties props;
        URL url;

        props = null;

        try {
            url = find(path);
            props = new Properties();
            props.load(url.openStream());

        } catch (Exception ex) {
            log.error("No se pudo cargar el properties:" + path, ex);
        }

        return props;
    }
}
