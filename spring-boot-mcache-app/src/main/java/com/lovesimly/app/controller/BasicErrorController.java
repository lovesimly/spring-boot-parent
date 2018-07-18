package com.lovesimly.app.controller;

import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public  class BasicErrorController  extends AbstractErrorController {

	public BasicErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
		// TODO Auto-generated constructor stub
	}

	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}


}
