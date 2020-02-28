package com.google.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author wk
 * @Description:
 * @date 2019/11/26 16:50
 **/
@RestController
public class RegionController {

    /**
     * restTemplate底层是httpClient
     */

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/getRegionMessage")
    public String getRegionMessage() {
        return restTemplate.getForObject("http://kk-story-server/getMessage", String.class);
    }
}
