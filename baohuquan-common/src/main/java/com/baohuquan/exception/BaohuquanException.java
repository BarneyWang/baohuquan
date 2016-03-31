package com.baohuquan.exception;

import com.baohuquan.utils.ResponseCode;

/**
 * Created by wangdi5 on 2016/3/20.
 */
public class BaohuquanException extends RuntimeException {

    private ResponseCode responseCode;

    public BaohuquanException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
