/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.crypto;

import com.novatronic.components.support.CipherType;
import com.novatronic.components.support.ResourceHelper;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Ricardo
 */
public class CryptoService {

    private static final Logger LOGGER = Logger.getLogger(CryptoService.class);
    private static final String CONFIG = "CipherOptions_Bytes.properties";

    public CryptoResponseDTO process(CryptoDTO request) {
        Crypto crypto;
        CryptoResponseDTO response;

        Properties config = ResourceHelper.findAsProperties(CONFIG);

        CipherType type = request.getType();

        if (!type.hasAnAlgoritm(request.getAlgoritmo())) {
            throw new RuntimeException("No se permite este algoritmo para [" + type + "]");
        }

        if (!type.hasAnOperation(request.getOperacion())) {
            throw new RuntimeException("No se permite este algoritmo para [" + type + "]");
        }

        response = new CryptoResponseDTO(request);
        try {

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

            crypto = CryptoFactory.getInstance(config);

            byte[] dataResponse = null;
            String codigoRespuesta = "99";

            if (request.getOperacion().equals("E")) {
                dataResponse = (byte[]) crypto.encrypt(request.getData());
                codigoRespuesta = "00";
            } else if (request.getOperacion().equals("D")) {
                dataResponse = (byte[]) crypto.decrypt(request.getData());
                codigoRespuesta = "00";
            } else if (request.getOperacion().equals("F")) {
                dataResponse = (byte[]) crypto.sign(request.getData());
                codigoRespuesta = "00";
            } else if (request.getOperacion().equals("V")) {
                codigoRespuesta = crypto.verify(request.getData(), request.getDataToVerified()) ? "00" : "99";
            }

            response.setDataRespuesta(dataResponse);
            response.setCodigoRespuesta(codigoRespuesta);
            response.setDescripcionRespuesta("-");
            LOGGER.info("Respuesta satisfactoria [" + response.getCodigoRespuesta() + "]");

        } catch (Exception e) {
            response.setCodigoRespuesta("99");
            response.setDescripcionRespuesta(e.getMessage());
            LOGGER.error("Respuesta con error [" + response.getCodigoRespuesta() + "]", e);
        }

        return response;
    }

}
