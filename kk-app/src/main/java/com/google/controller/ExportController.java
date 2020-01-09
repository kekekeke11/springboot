package com.google.controller;

import com.google.service.TtlProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author kundy
 * @create 2019/2/13 9:06 PM
 */

@RestController
@RequestMapping("/excelUtils")
public class ExportController {

    @Autowired
    private TtlProductInfoService productInfoService;

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        productInfoService.export(response, "商品信息");
    }

}
