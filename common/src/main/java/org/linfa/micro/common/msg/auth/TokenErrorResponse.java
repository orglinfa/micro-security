package org.linfa.micro.common.msg.auth;

import org.linfa.micro.common.constat.RestCodeConstants;
import org.linfa.micro.common.msg.BaseResponse;

public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
