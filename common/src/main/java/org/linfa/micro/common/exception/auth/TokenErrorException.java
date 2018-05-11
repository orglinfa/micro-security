package org.linfa.micro.common.exception.auth;
import org.linfa.micro.common.exception.BaseException;
import org.linfa.micro.common.constat.CommonConstants;

/**
 *
 */
public class TokenErrorException extends BaseException {
    public TokenErrorException(String message, int status) {
        super(message, CommonConstants.EX_TOKEN_ERROR_CODE);
    }
}
