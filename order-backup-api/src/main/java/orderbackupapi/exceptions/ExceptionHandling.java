package orderbackupapi.exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;

import java.util.Objects;

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

import orderbackupapi.constants.BackupConstants;
import orderbackupapi.models.HttpResponse;
import orderbackupapi.models.Monitoring;
import orderbackupapi.services.MonitoringService;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    /*
        Class which is used for handling exceptions and creating HTTP response if excpetion
        is thrown
    */

    private final String ERROR_PATH = "/api/order/error";
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandling.class);

    private final MonitoringService monitoringService;

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
        return createHttpResponse(HttpStatus.NOT_FOUND, BackupConstants.NO_MAPPING_FOR_URL);
    }

    @ExceptionHandler(NotCompletedOrderObject.class)
    public ResponseEntity<HttpResponse> notCompleteOrderObject(NotCompletedOrderObject e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, BackupConstants.INCOMPLETE_ORDER_OBJECT_EXCEPTION);
    }

    @ExceptionHandler(NotCompleteOrderedProductObject.class)
    public ResponseEntity<HttpResponse> notCompleteOrderedProductObject(NotCompleteOrderedProductObject e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, BackupConstants.INCOMPLETE_ORDERED_PRODUCT_OBJECT_EXCEPTION);
    }

    @ExceptionHandler(ObjectIdNotValidException.class)
    public ResponseEntity<HttpResponse> objectIdNOtValidException(ObjectIdNotValidException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, BackupConstants.NOT_VALID_ID);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(AccessDeniedException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.FORBIDDEN, BackupConstants.NOT_ENOUGH_PERMISSION);
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

        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, BackupConstants.INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        sendMessageToMonitoringApi(e);
        logger.warn(ExceptionUtils.getStackTrace(e));
        
        HttpMethod supportedMethod = Objects.requireNonNull(e.getSupportedHttpMethods()).iterator().next();

        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(BackupConstants.METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    private void sendMessageToMonitoringApi(Exception e) {
        String stackTrace = ExceptionUtils.getStackTrace(e);
        Monitoring monitoring = new Monitoring(stackTrace, BackupConstants.ORDER_BACKUP_API);
        
        monitoringService.sendMessageToMonitoringApi(monitoring);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String msg) {
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), msg);

        return new ResponseEntity<>(httpResponse, httpStatus);
    }
}
