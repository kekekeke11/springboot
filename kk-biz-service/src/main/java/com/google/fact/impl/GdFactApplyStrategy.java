package com.google.fact.impl;

import com.google.fact.FactApplyStrategy;
import org.springframework.stereotype.Component;

/**
 * @author wk
 * @Description:
 * @date 2019/12/24 13:59
 **/
@Component
public class GdFactApplyStrategy implements FactApplyStrategy {

    @Override
    public String toAddFact() {
        return "跳转光大银行";
    }
}
