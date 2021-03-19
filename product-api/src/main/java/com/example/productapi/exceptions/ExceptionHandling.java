package com.example.productapi.exceptions;

import java.util.Objects;

import java.io.IOException;

import com.auth0.jwt.exceptions.TokenExpiredException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.productapi.constants.ProductsConstants;
import com.example.productapi.models.HttpResponse;
import com.example.productapi.models.Monitoring;
import com.example.productapi.services.MonitoringService;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    /* 
        Class which is used for handling exceptions and creating HTTP response if excpetion
        is thrown
    */

    private final String ERROR_PATH = "/api/product/error";

    private final MonitoringService monitoringService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ExceptionHandling(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
    
    @RequestMapping(value = ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(HttpStatus.NOT_FOUND, ProductsConstants.NO_MAPPING_FOR_URL);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<HttpResponse> categoryNotFoundException(CategoryNotFoundException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.NOT_FOUND, ProductsConstants.CATEGORY_NOT_FOUND);
    }

    @ExceptionHandler(NotValidProductIdException.class)
    public ResponseEntity<HttpResponse> notValidProductIdException(NotValidProductIdException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, ProductsConstants.NOT_VALID_PRODUCT_ID);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<HttpResponse> productNotValidException(ProductNotFoundException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.NOT_FOUND, ProductsConstants.PRODUCT_NOT_EXIST);
    }

    @ExceptionHandler(NotValidProductQuantity.class)
    public ResponseEntity<HttpResponse> notValidProductQuantity(NotValidProductQuantity e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, ProductsConstants.NOT_VALID_PRODUCT_QUANTITY);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<HttpResponse> orderNotFoundException(OrderNotFoundException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));
        
        return createHttpResponse(HttpStatus.NOT_FOUND, ProductsConstants.ORDER_NOT_FOUND);
    }

    @ExceptionHandler(NotValidOrderedProductList.class)
    public ResponseEntity<HttpResponse> outOfStockException(NotValidOrderedProductList e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, ProductsConstants.ORDERED_PRODUCTS_ARE_NOT_VALID);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(AccessDeniedException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));
        
        return createHttpResponse(HttpStatus.FORBIDDEN, ProductsConstants.NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ProductsConstants.INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> ioException(IOException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ProductsConstants.ERROR_PROCESSING_FILE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        HttpMethod supportedMethod = Objects.requireNonNull(e.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(ProductsConstants.METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    private void sendMessageToMonitoringApi(Exception e) {
        String stackTrace = ExceptionUtils.getStackTrace(e);
        Monitoring monitoring = new Monitoring(stackTrace, ProductsConstants.PRODUCT_API);

        monitoringService.sendMessageToMonitoringApi(monitoring);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message);
        
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

}
