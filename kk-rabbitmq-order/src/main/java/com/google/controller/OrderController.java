package com.google.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.base.BaseApiService;
import com.google.base.ResponseBase;
import com.google.service.OrderService;

@RestController
public class OrderController extends BaseApiService {
	@Autowired
	private OrderService orderService;

	@RequestMapping("/addOrder")
	public ResponseBase addOrder() {
		return orderService.addOrderAndDispatch();
	}

}
