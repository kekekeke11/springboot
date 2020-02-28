package com.google.api.controller.service;

        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

/**
 * @author wk
 * @Description:
 * @date 2019/11/26 13:55
 **/
@RestController
public class MessageController {

    @RequestMapping(value = "/getMessage")
    public String getMessage() {
        return "盖伦来自德玛西亚";
    }
}
