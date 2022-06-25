package com.shin.small.common.exception;

import com.shin.small.common.web.State;

/**
 * 业务异常
 */
public class SmallServiceException extends RuntimeException {
    private State state;

    public SmallServiceException(State state, String message) {
        super(message);
        if (state == null) {
            throw new IllegalArgumentException("使用StarServiceException必须指定错误时的业务状态码！");
        }
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
