package speechtotext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeakerLabel;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechTimestamp;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;

import speechtotext.model.Dialog;

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
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String transcribeAudio(ModelMap model) {

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
				.speakerLabels(true) 
				.build();
 
		 SpeechResults speechresult = service.recognize(audio, options).execute();
		 //System.out.println(speechresult.toString());

		 String word;
		 StringBuffer phrases = new StringBuffer();
		 int speaker = 0;
		 int prevSpeaker = 0;
		 
		 // Create List of DialogExchange(Format: <speaker>:<phrase>)
		 List<Dialog> dialogs = new ArrayList();
		 Dialog dialog = new Dialog();
		 
		 System.out.println(speechresult.getResults());
		 if(speechresult.getResults() != null && !speechresult.getResults().isEmpty()) {
			 // FIXME: Currently for TestDialogue.wav, not sure if same index for other audio files
			 List<Transcript> results = speechresult.getResults();
			 SpeechAlternative alternative = results.get(0).getAlternatives().get(0);
			 
			 // Get timestamps
			 List<SpeechTimestamp> timestamps = alternative.getTimestamps();
			 // Get speakerLabels
			 List<SpeakerLabel> speakerlabels = speechresult.getSpeakerLabels();
			 
			 // ASSUMING same yung index ng timestamps and speakerlabels.
			 // compare speakerlabels for each timestamp then create appropriate dialog
			 for(int i = 0; i < timestamps.size(); i++) {			 
				 word = new String(timestamps.get(i).getWord());
				 speaker = speakerlabels.get(i).getSpeaker();
				 
				 if (prevSpeaker == speaker) {
					 phrases.append(word + " ");
				 } else {
					 dialog.setPhrase(phrases.toString().substring(0, phrases.length()-1));
					 dialog.setSpeaker(prevSpeaker);
					 
					 // Add to Dialogs 
					 dialogs.add(dialog);
					 
					 // create new instance of dialog and phrase
					 dialog = new Dialog();
					 phrases.setLength(0);
					 
					 prevSpeaker = speaker;
				 }
				 
				 if (i == timestamps.size()-1) {
					 dialog.setPhrase(phrases.toString());
					 dialog.setSpeaker(speaker);
					 
					 // Add to Dialogs 
					 dialogs.add(dialog);
				 }
			 }
			 
			 
			model.addAttribute("message", "Successfully Converted Audio to Text.");
			// Check if conversation is correct
			for (Dialog dialog1 : dialogs) {
				System.out.println("Speaker " + dialog1.getSpeaker() + ": " + dialog1.getPhrase().substring(0, dialog1.getPhrase().length()-1) + ".\n");
				model.addAttribute("results", dialogs);
				}
		 }
		 
		 return "index";
	}
	
}