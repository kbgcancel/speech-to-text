package speechtotext;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;

@Controller
@SpringBootApplication
@RequestMapping(value = "/home")
public class SpeechToTextApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeechToTextApplication.class, args);
	}

	/**
	 * Displays home page
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String home() {

		return ("index");
	}

	/**
	 * 
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String transcribeAudio() {

		System.out.println("METHOD_NAME: transcribeAudio");

		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("bcd31972-7881-47c7-804f-923f2732bf62", "ZaYzCUNxqIqG");

		File audio = new File("src/test/resources/TestDialogue.wav");

		RecognizeOptions options = new RecognizeOptions.Builder()
				.contentType(HttpMediaType.AUDIO_WAV)
				.model("en-US_BroadbandModel")
				.inactivityTimeout(-1)
				.smartFormatting(true)
				.wordConfidence(false)				
				.speakerLabels(true) //TODO: to be used for speaker recognition
				.build();
 
		 SpeechResults results = service.recognize(audio, options).execute();
		  System.out.println(results);

		return "index";
	}
}
