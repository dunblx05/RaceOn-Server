package com.parting.dippin.core.exception;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.BAD_REQUEST;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.COMMON_UN_RESOLVED_EXCEPTION;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.DATA_ACCESS_EXCEPTION;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.INTERNAL_SERVER_ERROR;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_URL;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_USER_ID;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_USER_TOKEN;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.REQUIRED_LOGIN;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.UN_AUTHORIZED;

import java.util.HashMap;
import java.util.Map;

public sealed class CommonException extends RuntimeException {

    private static final Map<CodeAndMessage, CommonException> factory = new HashMap<>();
    private final CodeAndMessage codeAndMessage;

    private CommonException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage.message());
        this.codeAndMessage = codeAndMessage;
    }

    static {
        initializeFactory();
    }

    private static void initializeFactory() {
        factory.put(BAD_REQUEST, new InvalidParameterException(BAD_REQUEST));
        factory.put(REQUIRED_LOGIN, new UnAuthenticatedException(REQUIRED_LOGIN));
        factory.put(INVALID_USER_ID, new UnAuthorizedException(INVALID_USER_ID));
        factory.put(INVALID_USER_TOKEN, new UnAuthorizedException(INVALID_USER_TOKEN));
        factory.put(UN_AUTHORIZED, new UnAuthorizedException(UN_AUTHORIZED));
        factory.put(INVALID_URL, new InvalidParameterException(INVALID_URL));
        factory.put(INTERNAL_SERVER_ERROR, new InternalServerException(INTERNAL_SERVER_ERROR));
        factory.put(DATA_ACCESS_EXCEPTION, new DataAccessException(DATA_ACCESS_EXCEPTION));
        factory.put(COMMON_UN_RESOLVED_EXCEPTION, new CommonUnResolvedException(COMMON_UN_RESOLVED_EXCEPTION));
    }

    public static CommonException from(final CommonCodeAndMessage codeAndMessage) {
        return getException(codeAndMessage);
    }

    public CodeAndMessage getCodeAndMessage() {
        return codeAndMessage;
    }

    public int getHttpStatusCode() {
        return codeAndMessage.httpStatusCode();
    }

    private static CommonException getException(final CodeAndMessage codeAndMessage) {
        final CommonException commonException = factory.get(codeAndMessage);
        if (commonException != null) {
            return commonException;
        }

        return factory.get(COMMON_UN_RESOLVED_EXCEPTION);
    }

    private static final class InvalidParameterException extends CommonException {
        private InvalidParameterException(final CodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class UnAuthenticatedException extends CommonException {
        private UnAuthenticatedException(final CodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class UnAuthorizedException extends CommonException {
        private UnAuthorizedException(final CodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class DataAccessException extends CommonException {
        private DataAccessException(final CodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class InternalServerException extends CommonException {
        private InternalServerException(final CodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class CommonUnResolvedException extends CommonException {
        public CommonUnResolvedException(final CodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }
}