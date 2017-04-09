package com.epam.result.client.rest;

import com.epam.result.client.exception.ServerDataAccessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * @author  Bogdan Viktor
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();
    private static final Logger LOGGER = LogManager.getLogger();


    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return errorHandler.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        LOGGER.debug("CustomResponseErrorHandler - handleError()");
        try{
            errorHandler.handleError(response);
        }catch(HttpStatusCodeException e){
            throw new ServerDataAccessException(e.getResponseBodyAsString());
        }
    }
}
