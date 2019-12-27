package com.google.fact.impl;

import com.google.fact.FactApplyStrategy;
import org.springframework.stereotype.Component;

/**
 * @author wk
 * @Description:
 * @date 2019/12/26 14:51
 **/
@Component
public class JTFactApplyStrategy implements FactApplyStrategy {

    @Override
    public String toAddFact() {
        return "跳转交通银行";
    }
}
