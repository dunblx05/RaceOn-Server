package com.parting.dippin.domain.member.exception;

import static com.parting.dippin.domain.member.exception.MemberCodeAndMessage.FAILED_UPLOAD_IMAGE;
import static com.parting.dippin.domain.member.exception.MemberCodeAndMessage.INVALID_MEMBER_CODE;

import com.parting.dippin.core.exception.BusinessException;
import com.parting.dippin.core.exception.CodeAndMessage;
import java.util.HashMap;
import java.util.Map;

public sealed class MemberTypeException extends BusinessException {

    private static final Map<MemberCodeAndMessage, MemberTypeException> exceptionMap = new HashMap<>();

    private MemberTypeException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage);
    }

    static {
        exceptionMap.put(FAILED_UPLOAD_IMAGE, new FailedUploadImageException(FAILED_UPLOAD_IMAGE));
        exceptionMap.put(INVALID_MEMBER_CODE, new InvalidMemberCodeException(INVALID_MEMBER_CODE));
    }

    public static MemberTypeException from(final MemberCodeAndMessage codeAndMessage) {
        final MemberTypeException noticeTypeException = exceptionMap.get(codeAndMessage);
        if (noticeTypeException == null) {
            return new MemberUnResolvedException(codeAndMessage);
        }

        return noticeTypeException;
    }

    private static final class FailedUploadImageException extends MemberTypeException {

        public FailedUploadImageException(final MemberCodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class InvalidMemberCodeException extends MemberTypeException {

        public InvalidMemberCodeException(final MemberCodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class MemberUnResolvedException extends MemberTypeException {

        public MemberUnResolvedException(final MemberCodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }
}
