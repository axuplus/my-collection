package com.utils.util.exception;

import com.utils.util.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BackCarException extends RuntimeException {
    private ResultCodeEnum resultCodeEnum;
}
