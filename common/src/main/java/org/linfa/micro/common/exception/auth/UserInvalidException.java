package org.linfa.micro.common.exception.auth;

import org.linfa.micro.common.constat.CommonConstants;
import org.linfa.micro.common.exception.BaseException;

public class UserInvalidException extends BaseException {
    public UserInvalidException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
