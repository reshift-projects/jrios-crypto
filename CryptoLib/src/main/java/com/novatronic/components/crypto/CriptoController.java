/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.crypto;

import com.novatronic.components.ImplFile.ByteCripto;
import com.novatronic.components.crypto.message.Request;
import com.novatronic.components.crypto.message.Response;
import com.novatronic.components.crypto.operation.OperationExecutor;
import com.novatronic.components.exceptions.CryptoException;
import com.novatronic.components.support.ResourceHelper;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Ricardo
 */
public class CriptoController {

    private static final Logger LOGGER = Logger.getLogger(CriptoController.class);
    private static final String CONFIG = "CipherOptions_Bytes.properties";
    private final Properties configuration;

    public CriptoController() {
        configuration = ResourceHelper.findAsProperties(CONFIG);
    }

    public CriptoController(Properties configuration) {
        this.configuration = configuration;
    }

    public Response process(Request request) {
        Crypto crypto;
        Response response;
        Properties config;

        try {

            request.validar();

            config = getConfiguration(request);

            crypto = CryptoFactory.getInstance(config);

            response = OperationExecutor.execute(crypto, request);

            LOGGER.info("Respuesta obtenida [" + response.getCodigoRespuesta() + "]");

        } catch (CryptoException e) {
            response = new Response();
            response.setCodigoRespuesta(e.getCodigo());
            response.setDescripcionRespuesta(e.getMessage());
            LOGGER.error("Respuesta con error identificado [" + response.getCodigoRespuesta() + "=" + response.getDescripcionRespuesta() + "]", e);
        } catch (Exception e) {
            response = new Response();
            response.setCodigoRespuesta(CryptoException.GENERAL);
            response.setDescripcionRespuesta("Error en el sistema");
            LOGGER.error("Respuesta con error [" + response.getCodigoRespuesta() + "=" + response.getDescripcionRespuesta() + "]", e);
        }

        return response;
    }

    private Properties getConfiguration(Request request) {
        Properties config = new Properties();
        config.putAll(configuration);

        switch (request.getType()) {
            case ASYMETRIC:
                config.put("encSignAlg", request.getAlgoritmo());
                config.put("verSignAlg", request.getAlgoritmo());
                break;
            case SYMETRIC:
                config.put("encAlg", request.getAlgoritmo());
                config.put("decAlg", request.getAlgoritmo());
                break;
        }

        return config;
    }

}
