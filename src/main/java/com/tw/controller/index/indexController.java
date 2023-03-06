package com.tw.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

	@GetMapping(path = { "/", "/welcome" })
	public String userController() {
		return "user/index.html";
	}

	@GetMapping(path = {"/admin","/admin/home"})
	public String adminController() {
		return "admin/index.html";
	}

}
