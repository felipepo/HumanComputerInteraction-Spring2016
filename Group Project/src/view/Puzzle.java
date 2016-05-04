package view;

public class Puzzle {
	String solution = "";
	String dashes = "";
	String hint1 = "";
	String hint2 = "";
	String translation = "";
	String language = "";

	public Puzzle(){}

	public Puzzle(String s, String d, String h1,
		String h2, String t, String l){

		solution = s;
		dashes = d;
		hint1 = h1;
		hint2 = h2;
		translation = t;
		language = l;
	}

	public String getSolution(){
		return solution;
	}
	public String getDashes(){
		return dashes;
	}
	public String getHint1(){
		return hint1;
	}
	public String getHint2(){
		return hint2;
	}
	public String getTranslation(){
		return translation;
	}
	public String getLanguage(){
		return language;
	}

}
