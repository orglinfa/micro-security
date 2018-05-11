package org.linfa.micro.common.exception.auth;

import org.linfa.micro.common.exception.BaseException;
import org.linfa.micro.common.constat.CommonConstants;

public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, CommonConstants.EX_CLIENT_FORBIDDEN_CODE);
    }
}
