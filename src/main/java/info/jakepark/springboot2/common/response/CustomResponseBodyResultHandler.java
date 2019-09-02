package info.jakepark.springboot2.common.response;

import info.jakepark.springboot2.common.handler.CustomMessageHandler;
import info.jakepark.springboot2.common.response.annotation.IgnoreAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice("info.jakepark.springboot2")
public class CustomResponseBodyResultHandler implements ResponseBodyAdvice<Object> {

    private final CustomMessageHandler customMessageHandler;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // Custom code(컨트롤러 레벨에서 response에 셋팅)
        // e.g) response.setHeader("X-Status-Code", ResponseType.ACCESS_DENIED.getCode());
//        String statusCode = ((ServletServerHttpResponse) response).getServletResponse().getHeader("X-Status-Code");

        if (body instanceof BeanPropertyBindingResult) {
            // controller단 validation 에러 발생 시 처리
            BeanPropertyBindingResult beanPropertyBindingResult = ((BeanPropertyBindingResult) body);

            List<CustomError> errors = new ArrayList<>();
            beanPropertyBindingResult.getAllErrors().forEach(e -> {
                if (e instanceof FieldError) {
                    errors.add(this.mappingError((FieldError) e));
                } else {
                    errors.add(this.mappingError(e));
                }
            });

            response.setStatusCode(HttpStatus.BAD_REQUEST);

            // result 값 없음
            return getCustomResponse(ResponseType.BAD_REQUEST, null, errors);
        }

        IgnoreAdvice ignoreAdvice = returnType.getMethodAnnotation(IgnoreAdvice.class);
        if (Objects.nonNull(ignoreAdvice) && ignoreAdvice.ignore()) {
            return body;
        }

        if (((ServletServerHttpResponse) response).getServletResponse().getStatus() == HttpStatus.OK.value()) {
            if (ObjectUtils.isEmpty(body)) {
                return getCustomResponse(ResponseType.OK_BUT_NO_CONTENT, body);
            }
        }

        return getCustomResponse(ResponseType.OK, body);
    }

    private CustomError mappingError(FieldError e) {
        return new CustomError()
                .setType(e.getField())
                .setData(Objects.isNull(e.getRejectedValue()) ? "" : e.getRejectedValue().toString())
                .setMessage(e.getDefaultMessage());
    }

    private CustomError mappingError(ObjectError e) {
        return new CustomError()
                .setType(e.getObjectName())
                .setData(e.getCode())
                .setMessage(e.getDefaultMessage());
    }

    private CustomResponse getCustomResponse(ResponseType responseType, Object result) {
        return new CustomResponse()
                .setCode(responseType.getCode())
                .setMessage(customMessageHandler.getMessage(responseType))
                .setResult(result);
    }

    private CustomResponse getCustomResponse(ResponseType responseType, Object result, List<CustomError> errors) {
        return new CustomResponse()
                .setCode(responseType.getCode())
                .setMessage(customMessageHandler.getMessage(responseType))
                .setResult(result)
                .setErros(errors);
    }
}
