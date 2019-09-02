package info.jakepark.springboot2.common.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CustomResponse {
    String code;
    String message;
    Object result;
    List<CustomError> erros;
    String debugMessage;
}