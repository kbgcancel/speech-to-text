/**
 * 
 */
package speechtotext.model;

/**
 * @author kristine.cancel
 *
 */
public class Dialog {

	private int speaker;
	private String phrase;
	
	public int getSpeaker() {
		return speaker;
	}
	
	public void setSpeaker(int speaker) {
		this.speaker = speaker;
	
	}
	
	public String getPhrase() {
		return phrase;
	}
	
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

}