package com.google.controller;

import com.google.fact.FactApplyStrategy;
import com.google.fact.PurposeEnum;
import com.google.util.SpringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wk
 * @Description:
 * @date 2019/12/26 17:59
 **/
@RestController
public class FactController {

    @RequestMapping(value = "/toAddFact")
    public String toAddFact(Integer code) {

        FactApplyStrategy factApplyStrategy = null;
        try {
            factApplyStrategy = (FactApplyStrategy)
                    SpringUtils.getBean(Class.forName(PurposeEnum.getEnumObjByCode(code).getClassName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return factApplyStrategy.toAddFact();
    }
}
