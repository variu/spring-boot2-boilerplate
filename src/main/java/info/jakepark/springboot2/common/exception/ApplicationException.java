package info.jakepark.springboot2.common.exception;

import info.jakepark.springboot2.common.response.ResponseType;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ApplicationException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 3713800883410031528L;

    private ResponseType applicationType;
    private Object[] args;
    private Object result;

    public ApplicationException(ResponseType applicationType) {
        super(applicationType.getMessage());
        this.applicationType = applicationType;
    }

//    public ApplicationException(ApplicationType applicationType, String message) {
//        super(applicationType.name() + ":" + message);
//        this.applicationType = applicationType;
//    }
//
//    public ApplicationException(ApplicationType applicationType, Throwable cause) {
//        super(applicationType.name(), cause);
//        this.applicationType = applicationType;
//    }
//
//    public ApplicationException(ApplicationType applicationType, Object result) {
//        super(applicationType.name());
//        this.applicationType = applicationType;
//        this.result = result;
//    }
//
//    public ApplicationException(ApplicationType applicationType, Object... args) {
//        super(applicationType.name());
//        this.applicationType = applicationType;
//        this.args = args;
//    }
}
