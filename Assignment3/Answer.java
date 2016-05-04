
public class Answer {
	private String answer;
	private Boolean value;
	
	public Answer(String input) {
		this.answer = input;
		this.value = false;
		if( input.startsWith("C ") ) {
			this.answer = input.substring( 2, input.length() );
			this.value = true;
		}
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public Boolean getValue() {
		return value;
	}
	
	public void setValue(Boolean value) {
		this.value = value;
	}
	
	public String toString() {
		return this.answer;
	}

	
}
