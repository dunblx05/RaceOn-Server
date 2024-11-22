package com.parting.dippin.core.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BaseResponse<T> {

    T data;

    public static <T> BaseResponse<T> success(T data) {

        return new BaseResponse<T>().setData(data);
    }

    public static BaseResponse success() {
        return new BaseResponse();
    }
}
