package com.parting.dippin.domain.friend.exception;

import static com.parting.dippin.domain.friend.exception.FriendCodeAndMessage.ALREADY_FRIEND_EXCEPTION;
import static com.parting.dippin.domain.friend.exception.FriendCodeAndMessage.NOT_FRIENDS_EXCEPTION;

import com.parting.dippin.core.exception.BusinessException;
import com.parting.dippin.core.exception.CodeAndMessage;
import java.util.HashMap;
import java.util.Map;

public class FriendTypeException extends BusinessException {

    private static final Map<FriendCodeAndMessage, FriendTypeException> exceptionMap = new HashMap<>();

    private FriendTypeException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage);
    }

    static {
        exceptionMap.put(ALREADY_FRIEND_EXCEPTION, new FriendTypeException.AlreadyFriendException(ALREADY_FRIEND_EXCEPTION));
        exceptionMap.put(NOT_FRIENDS_EXCEPTION, new FriendTypeException.DoesNotFriendException(NOT_FRIENDS_EXCEPTION));
    }

    public static FriendTypeException from(final FriendCodeAndMessage codeAndMessage) {
        final FriendTypeException noticeTypeException = exceptionMap.get(codeAndMessage);
        if (noticeTypeException == null) {
            return new FriendTypeException.FriendUnresolvedException(codeAndMessage);
        }

        return noticeTypeException;
    }

    private static final class AlreadyFriendException extends FriendTypeException {

        public AlreadyFriendException(final FriendCodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class DoesNotFriendException extends FriendTypeException {

        public DoesNotFriendException(final FriendCodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class FriendUnresolvedException extends FriendTypeException {

        public FriendUnresolvedException(final FriendCodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }
}
