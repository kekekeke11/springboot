package com.google.fact.context;

import com.google.fact.FactApplyStrategy;
import com.google.fact.factory.FactApplyFactory;
import org.springframework.stereotype.Service;

/**
 * @author wk
 * @Description:获取具体策略实现
 * @date 2019/12/24 14:03
 **/
@Service
public class FactContextStrategy {

    /**
     * 获取具体的策略实现
     *
     * @param code
     * @return
     */
    public String toAddFact(Integer code) {
        //使用策略工厂获取具体的策略实现
        FactApplyStrategy factApplyStrategy = FactApplyFactory.getFactApplyStrategy(code);
        if (factApplyStrategy == null) {
            throw new RuntimeException("没有找到对应的策略实现");
        }
        return factApplyStrategy.toAddFact();
    }

    public static void main(String[] args) {
        System.out.println(new FactContextStrategy().toAddFact(2));
        System.out.println(new FactContextStrategy().toAddFact(3));
        System.out.println(new FactContextStrategy().toAddFact(1));
    }

}
