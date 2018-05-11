package org.linfa.micro.common.exception.auth;

import org.linfa.micro.common.exception.BaseException;
import org.linfa.micro.common.constat.CommonConstants;

/**
 *
 */
public class ClientInvalidException extends BaseException {
    public ClientInvalidException(String message) {
        super(message, CommonConstants.EX_CLIENT_INVALID_CODE);
    }
}
