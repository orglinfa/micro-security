package org.linfa.micro.common.msg.auth;

import org.linfa.micro.common.constat.RestCodeConstants;
import org.linfa.micro.common.msg.BaseResponse;

/**
 *
 */
public class TokenForbiddenResponse  extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
