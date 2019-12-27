package com.google.fact.impl;

import com.google.fact.FactApplyStrategy;
import org.springframework.stereotype.Component;

/**
 * @author wk
 * @Description:
 * @date 2019/12/26 17:10
 **/
@Component
public class ZGFactApplyStrategy implements FactApplyStrategy {

    @Override
    public String toAddFact() {
        return "跳转中国银行";
    }
}
