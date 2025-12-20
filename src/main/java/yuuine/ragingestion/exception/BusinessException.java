package yuuine.ragingestion.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        this(String.valueOf(errorCode.getCode()), errorCode.getMessage());
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        this(String.valueOf(errorCode.getCode()), errorCode.getMessage(), cause);
    }
}
