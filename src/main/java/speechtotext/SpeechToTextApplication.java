package speechtotext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
import speechttext.util.FileUtil;

@Controller
@SpringBootApplication
public class SpeechToTextApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeechToTextApplication.class, args);
	}

	/**
	 * Displays home page
	 * 
	 * @return
	 */
	@GetMapping("/home")
	public String home() {

		return "index";
	}

	/**
	 * @throws IOException 
	 * 
	 */
	@PostMapping("/transcribe")
	public String transcribeAudio(@RequestParam("file") MultipartFile file, ModelMap model) throws IOException {

		System.out.println("METHOD_NAME: transcribeAudio");

		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("bcd31972-7881-47c7-804f-923f2732bf62", "ZaYzCUNxqIqG");

		if (file.isEmpty()) {
			// ERROR message
			model.addAttribute("error", "* Please select a file to upload.");
			return "index";
		}  else {
						
			// convert multipart file to file
			FileUtil fileUtil = new FileUtil();
			File audioFile = fileUtil.multipartFileToFileConversion(file);
	
			RecognizeOptions options = new RecognizeOptions.Builder().contentType(HttpMediaType.AUDIO_WAV)
					.model("en-US_BroadbandModel").inactivityTimeout(-1).smartFormatting(true).wordConfidence(false)
					.speakerLabels(true).build();
	
			SpeechResults speechresult = service.recognize(audioFile, options).execute();
			System.out.println(speechresult.toString());
	
			String word;
			StringBuffer phrases = new StringBuffer();
			int speaker = 0;
			int prevSpeaker = 0;
			int speakerLabelIndx = 0;
			// Create List of DialogExchange(Format: <speaker>:<phrase>)
			List<Dialog> dialogs = new ArrayList<Dialog>();
			Dialog dialog = new Dialog();
	
				if (speechresult.getResults() != null && !speechresult.getResults().isEmpty()) {
					List<Transcript> results = speechresult.getResults();
	
					// SR: SpeechResult Object
					for (int SRrsltSz = 0; SRrsltSz < results.size(); SRrsltSz++) {
						for (int SRAltrntvSz = 0; SRAltrntvSz < results.get(SRrsltSz).getAlternatives().size(); SRAltrntvSz++) {
	
							SpeechAlternative alternative = results.get(SRrsltSz).getAlternatives().get(SRAltrntvSz);
							// Get timestamps
							List<SpeechTimestamp> timestamps = alternative.getTimestamps();
							// Get speakerLabels
							List<SpeakerLabel> speakerlabels = speechresult.getSpeakerLabels();
	
							// compare speakerlabels for each timestamp then create appropriate dialog
							for (int i = 0; i < timestamps.size(); i++) {
								word = new String(timestamps.get(i).getWord());
								speaker = speakerlabels.get(speakerLabelIndx).getSpeaker();
	
								if (prevSpeaker == speaker) {
									phrases.append(word + " ");
								}  else {
									dialog.setPhrase(phrases.toString());
									dialog.setSpeaker(prevSpeaker);
	
									dialogs.add(dialog);
	
									// create new instance of dialog and phrase
									dialog = new Dialog();
									phrases.setLength(0);
	
									prevSpeaker = speaker;
								}
	
								speakerLabelIndx++;
							
								// Add the last created Dialog to dialogList
								if (i == timestamps.size() - 1) {
									dialog.setPhrase(phrases.toString());
									dialog.setSpeaker(speaker);
	
									dialogs.add(dialog);
								}
							}
						}
					}
	
					// Display Info Message
					model.addAttribute("message", "Successfully Converted Audio to Text.");
	
					for (Dialog dialog1 : dialogs) {
						System.out.println("Speaker " + dialog1.getSpeaker() + ": " + dialog1.getPhrase() + ".\n");
						model.addAttribute("results", dialogs);
					}
				}
	
				return "index";
			}
		
		}
	
}