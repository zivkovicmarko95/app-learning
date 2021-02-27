package userapi.exceptions;

import java.io.IOException;
import java.util.Objects;

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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import userapi.constants.MessagesConstants;
import userapi.models.HttpResponse;
import userapi.models.Monitoring;
import userapi.services.MonitoringService;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    /*
        This class is used for handling exceptions and creating HTTP response to the user
    */

    private final String ERROR_PATH = "/api/user/error";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MonitoringService monitoringService;

    @Autowired
    public ExceptionHandling(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisableException(DisabledException e) {
        sendMessageToMonitoringApi(e);
        logger.error(MessagesConstants.ACCOUNT_DISABLED);
        return createHttpResponse(HttpStatus.BAD_REQUEST, MessagesConstants.ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException(BadCredentialsException e) {
        sendMessageToMonitoringApi(e);
        logger.error(MessagesConstants.INCORRECT_CREDENTIALS);
        return createHttpResponse(HttpStatus.BAD_REQUEST, MessagesConstants.INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(AccessDeniedException e) {
        sendMessageToMonitoringApi(e);
        logger.error(MessagesConstants.NOT_ENOUGH_PERMISSION);
        return createHttpResponse(HttpStatus.FORBIDDEN, MessagesConstants.NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException(LockedException e) {
        sendMessageToMonitoringApi(e);
        logger.error(MessagesConstants.ACCOUNT_LOCKED);
        return createHttpResponse(HttpStatus.UNAUTHORIZED, MessagesConstants.ACCOUNT_LOCKED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException e) {
        sendMessageToMonitoringApi(e);
        logger.error(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistException e) {
        sendMessageToMonitoringApi(e);
        logger.error(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<HttpResponse> usernameExistException(UsernameExistException e) {
        sendMessageToMonitoringApi(e);
        logger.error(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException e) {
        sendMessageToMonitoringApi(e);
        logger.error(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException e) {
        sendMessageToMonitoringApi(e);
        logger.error(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NotValidIdException.class)
    public ResponseEntity<HttpResponse> notValidIdException(NotValidIdException e) {
        sendMessageToMonitoringApi(e);
        logger.error(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.BAD_REQUEST, MessagesConstants.NOT_VALID_ID);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception e) {
        sendMessageToMonitoringApi(e);
        logger.error(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, MessagesConstants.INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> ioException(IOException e) {
        sendMessageToMonitoringApi(e);
        logger.error(ExceptionUtils.getStackTrace(e));

        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, MessagesConstants.ERROR_PROCESSING_FILE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        sendMessageToMonitoringApi(e);
        logger.error(ExceptionUtils.getStackTrace(e));

        HttpMethod supportedMethod = Objects.requireNonNull(e.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(MessagesConstants.METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @RequestMapping(value = ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(HttpStatus.NOT_FOUND, MessagesConstants.NO_MAPPING_FOR_URL);
    }

    private void sendMessageToMonitoringApi(Exception e) {
        String stackTrace = ExceptionUtils.getStackTrace(e);
        Monitoring monitoring = new Monitoring(stackTrace, MessagesConstants.USER_API);
        
        monitoringService.sendMessageToMonitoringApi(monitoring);
    }

	private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message);
        
        return new ResponseEntity<>(httpResponse, httpStatus);
	}

}
