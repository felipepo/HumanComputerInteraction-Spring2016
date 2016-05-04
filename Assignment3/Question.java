import java.util.ArrayList;

public class Question {
	String question;
	private ArrayList<Answer> answers;

	Question(String question) {
		this.question = question;
		this.answers = new ArrayList<Answer> ();
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
	
	public String toString() {
		String result = this.question + "\n";
		for (int i = 0; i < this.answers.size(); i++) {
			result += this.answers.get(i) +"\n";
		}
		return result;
	}
	
}
