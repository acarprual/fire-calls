package me.lprada.fire_calls.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebConfig {
	@RequestMapping(value = {"/{path:^(?!api$)(?!static$)[^\\.]*}/**"})
	public String redirect() {
		return "forward:/index.html";
	}

}
