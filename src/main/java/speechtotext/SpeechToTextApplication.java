package speechtotext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class SpeechToTextApplication {

	@RequestMapping("/home")
	//@ResponseBody
	String home() {
		//return "Welcome to my First Spring Boot App!";
		return("index");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpeechToTextApplication.class, args);
	}
}
