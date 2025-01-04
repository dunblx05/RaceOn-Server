package com.parting.dippin.domain.report.exception;

import static com.parting.dippin.domain.report.exception.ReportCodeAndMessage.INVALID_REPORTED_MEMBER_ID;

import com.parting.dippin.core.exception.BusinessException;
import com.parting.dippin.core.exception.CodeAndMessage;
import java.util.HashMap;
import java.util.Map;

public sealed class ReportTypeException extends BusinessException {

    private static final Map<ReportCodeAndMessage, ReportTypeException> exceptionMap = new HashMap<>();

    private ReportTypeException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage);
    }

    static {
        exceptionMap.put(INVALID_REPORTED_MEMBER_ID, new InvalidReportedMemberException(INVALID_REPORTED_MEMBER_ID));
    }

    public static ReportTypeException from(final ReportCodeAndMessage codeAndMessage) {
        final ReportTypeException noticeTypeException = exceptionMap.get(codeAndMessage);
        if (noticeTypeException == null) {
            return new ReportUnResolvedException(codeAndMessage);
        }

        return noticeTypeException;
    }

    private static final class InvalidReportedMemberException extends ReportTypeException {

        public InvalidReportedMemberException(final ReportCodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }

    private static final class ReportUnResolvedException extends ReportTypeException {

        public ReportUnResolvedException(final ReportCodeAndMessage codeAndMessage) {
            super(codeAndMessage);
        }
    }
}
