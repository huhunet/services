package com.miracle.iam.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    public final static int FORBIDDEN  = 420;
    public final static int UNAUTHORIZED  = 401;
    public final static int SUCCESS  = 200;

    private int errorCode;
    private String errorInfo;
}
