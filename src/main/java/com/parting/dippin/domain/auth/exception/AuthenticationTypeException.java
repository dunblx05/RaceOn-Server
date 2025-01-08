package com.parting.dippin.domain.auth.exception;

import static com.parting.dippin.domain.auth.exception.AuthenticationCodeAndMessage.INVALID_TOKEN_OWNER;
import static com.parting.dippin.domain.auth.exception.AuthenticationCodeAndMessage.INVALID_TOKEN_TYPE;
import static com.parting.dippin.domain.auth.exception.AuthenticationCodeAndMessage.USER_NOT_REGISTERED;

import com.parting.dippin.core.exception.BusinessException;
import com.parting.dippin.core.exception.CodeAndMessage;
import java.util.HashMap;
import java.util.Map;

public sealed class AuthenticationTypeException extends BusinessException {

    private static final Map<AuthenticationCodeAndMessage, AuthenticationTypeException> exceptionMap = new HashMap<>();

    private AuthenticationTypeException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage);
    }

    static {
        exceptionMap.put(USER_NOT_REGISTERED, new UserNotRegisteredException(USER_NOT_REGISTERED));
        exceptionMap.put(INVALID_TOKEN_TYPE, new InvalidTokenTypeException(INVALID_TOKEN_TYPE));
        exceptionMap.put(INVALID_TOKEN_OWNER, new InvalidTokenOwnerException(INVALID_TOKEN_OWNER));
    }

    public static AuthenticationTypeException from(final AuthenticationCodeAndMessage codeAndMessage) {
        return getException(codeAndMessage);
    }

    private static AuthenticationTypeException getException(final CodeAndMessage codeAndMessage) {
        final AuthenticationTypeException authenticationTypeException = exceptionMap.get(codeAndMessage);
        if (authenticationTypeException == null) {
            return new AuthenticationUnResolvedException(codeAndMessage);
        }

        return authenticationTypeException;
    }

    private static final class InvalidTokenOwnerException extends AuthenticationTypeException {

        private InvalidTokenOwnerException(final CodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class InvalidTokenTypeException extends AuthenticationTypeException {

        private InvalidTokenTypeException(final CodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class UserNotRegisteredException extends AuthenticationTypeException {

        private UserNotRegisteredException(final CodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class AuthenticationUnResolvedException extends AuthenticationTypeException {

        public AuthenticationUnResolvedException(final CodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }
}