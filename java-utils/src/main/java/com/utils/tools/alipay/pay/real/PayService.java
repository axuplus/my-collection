package com.utils.tools.alipay.pay.real;

import com.utils.util.wrapper.Wrapper;

import java.util.Map;

public interface PayService {

    Wrapper aliAttestation(String mapSource);

    String aliPayReturn(Map<String, String> param);
}
