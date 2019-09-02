package info.jakepark.springboot2.common.exception;

import info.jakepark.springboot2.common.handler.CustomMessageHandler;
import info.jakepark.springboot2.common.response.CustomResponse;
import info.jakepark.springboot2.common.response.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@ApiIgnore
@RestController
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {

    private static final String PATH = "error";

    private final ErrorAttributes errorAttributes;
    private final CustomMessageHandler customMessageHandler;

    @Override
    public String getErrorPath() {
        return PATH;
    }

    /**
     * 404 에러 처리(404 외에는 GlobalConrollerException 으로 이동)
     *
     * @param request
     * @return
     */
    @RequestMapping(PATH)
    public ResponseEntity<CustomResponse> error(WebRequest request) {
        Map<String, Object> errors = this.getErrorAttributes(request);
        String path = (String) errors.get("path");
        String error = (String) errors.get("error");
        String message = (String) errors.get("messsage");
        int status = (int) errors.get("status");
        ResponseType applicationType = ResponseType.NOT_FOUND;
        return ResponseEntity.status(HttpStatus.valueOf(status))
                .body(new CustomResponse()
                        .setCode(applicationType.getCode())
                        .setMessage(customMessageHandler.getMessage(applicationType))
                        .setResult(path)
                        .setDebugMessage(message)
                );
    }

    private Map<String, Object> getErrorAttributes(WebRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.putAll(this.errorAttributes.getErrorAttributes(request, true));
        return map;
    }
}
