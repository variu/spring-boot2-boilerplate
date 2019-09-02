package info.jakepark.springboot2.common.exception;

import info.jakepark.springboot2.common.handler.CustomMessageHandler;
import info.jakepark.springboot2.common.response.CustomResponse;
import info.jakepark.springboot2.common.response.ResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class CustomExceptionHandler {

    private final CustomMessageHandler customMessageHandler;

    private ResponseEntity<CustomResponse> failResponse(ResponseType responseType, Object result, Exception e) {
        return ResponseEntity.status(responseType.getHttpStatus())
                .body(new CustomResponse()
                        .setCode(responseType.getCode())
                        .setResult(result)
                        .setMessage(customMessageHandler.getMessage(responseType))
                        .setDebugMessage(e.getMessage()))
                ;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleInternalServerError(Exception e) {
        log.error(e.getMessage());
        return failResponse(ResponseType.INTERNAL_SERVER_ERROR, null, e);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return failResponse(ResponseType.BAD_REQUEST_METHOD, null, e);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity handleApplicationException(ApplicationException e) {
        log.debug(e.getMessage());
        return failResponse(e.getApplicationType(), e.getResult(), e);
    }

}
