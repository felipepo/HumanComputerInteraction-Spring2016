import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class QuestionnaireShell{

	public Shell shell;
	final Menu menubar;
	final Menu menu;
	final Button[] options;
	Button submitButton;
	ArrayList<Question> questions;
	int mode;
	String givenAnswer;
	static int numberOfCorrectAnswers;
	private final static int NUMBER_ANSWERS_PER_QUESTION = 4;
	String filename;
	final Label label;
	
	
	public QuestionnaireShell() {
		shell = new Shell();
		shell.setText("SWT Questionnaire by Felipe Podolan Oliveira");
		shell.setSize(500,500);
		shell.setLayout(new FormLayout());
		
		menubar = new Menu(shell, SWT.BAR);
		menu = new Menu(shell, SWT.DROP_DOWN);
		
		options = new Button[NUMBER_ANSWERS_PER_QUESTION];
		submitButton = new Button( shell , SWT.PUSH );
		submitButton.setVisible(false);
		
		questions = new ArrayList<Question>();
		
		mode = 0;
		
		givenAnswer = null;
		numberOfCorrectAnswers = 0;
		
		for(int i = 0; i < NUMBER_ANSWERS_PER_QUESTION; i++) {
			options[i] = new Button(shell, SWT.RADIO);
			options[i].setVisible(false);
			final int buttonIndex = i;
			options[i].addSelectionListener( new SelectionAdapter() {
				final int innerButtonIndex = buttonIndex;
				@Override
				public void widgetSelected(SelectionEvent e) {
					givenAnswer = options[innerButtonIndex].getText();
				}
			});
		}
		
		label = new Label(shell, SWT.BORDER);
		
	}
	
	public void display() {
		shell.open();
		while( !shell.isDisposed() ) {
			if( !shell.getDisplay().readAndDispatch() ) {
				shell.getDisplay().sleep();
			}
		}
		shell.dispose();
	}
	
	public void setQuestions(ArrayList<Question> questions){
		this.questions = questions;
	}
	
	public void addMenu() {
		final MenuItem cascade = new MenuItem(menubar, SWT.CASCADE);
		cascade.setText("Options");
	    cascade.setMenu(menu);
		final MenuItem mode = new MenuItem(menu, SWT.PUSH);
		mode.setText("Change mode");
		mode.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeMode();
			}
		} );
		shell.setMenuBar(menubar);
	}
	
	public void addQuestion(final int index) {
		label.setText(questions.get(index).getQuestion());
		label.setBounds(shell.getClientArea());
		FormData labelData = new FormData();
		labelData.top = new FormAttachment(10,0);
		labelData.left = new FormAttachment(5,0);
		label.setLayoutData(labelData);
		
		for(int i = 0; i < NUMBER_ANSWERS_PER_QUESTION; i++) {
			String answer = questions.get(index).getAnswers().get(i).getAnswer();
			options[i].setText(answer);
			options[i].setVisible(true);
		}
		
		for (int i = 0; i < NUMBER_ANSWERS_PER_QUESTION; i++){
			FormData formData = new FormData();
			formData.top = new FormAttachment(label, 5+i*25);
			formData.left = new FormAttachment(7,0);
			options[i].setLayoutData(formData);
		}
		
		submitButton.dispose();
		submitButton = new Button( shell , SWT.PUSH );
		submitButton.setText( "Submit " );
		submitButton.setVisible(true);
		FormData submitData = new FormData();
		submitData.top = new FormAttachment(options[options.length-1],5);
		submitData.right = new FormAttachment(95,0);
		submitButton.setLayoutData(submitData);
		submitButton.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				submitQuestion(index);
			}
		} );
		addOpenButton();
		shell.layout(true);
	}
	
	public void submitQuestion(int index) {
		for(Button button: options) {
			button.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		}
		
		if(givenAnswer == null) {
			return;
		}
		switch(mode) {
		case 0:
			incrementCorrectAnswers(index);
			givenAnswer = null;
			if(index+1 < questions.size()){
				for(Button button: options) {
					button.setSelection(false);
				}
				addQuestion(index+1);
			}
			else {
				endQuestionnaire();
			}
			break;
		case 1:
			showCorrectOption(index);
		}	
	}
	
	public void incrementCorrectAnswers(int index) {
		for(Answer answer: questions.get(index).getAnswers()) {
			if ( answer.getAnswer().equals(givenAnswer)) {
				if(answer.getValue()) {
					numberOfCorrectAnswers ++;
					break;
				}
			}
		}
	}
	
	public void endQuestionnaire() {
		for(Control kid: shell.getChildren()) {
			kid.dispose();
		}
		
		final Label label = new Label(shell, SWT.BORDER);
		double score = (double)numberOfCorrectAnswers/questions.size() * 100;
		label.setText("Questionnaire Finished!\nYour score is: " + score + "%");
		label.setBounds(shell.getClientArea());
		FormData labelData = new FormData();
		labelData.top = new FormAttachment(10,0);
		labelData.left = new FormAttachment(5,0);
		label.setLayoutData(labelData);
	}
	
	public void changeMode() {
		switch (mode) {
			case 0:
				mode = 1;
				break;
			case 1:
				mode = 0;
				break;
		}
	}
	
	public void showCorrectOption(int index) {
		
		for(Answer answer: questions.get(index).getAnswers()) {
			if(answer.getValue()) {
				for(Button button: options) {
					if (button.getText().equals(answer.getAnswer())){
						button.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
					}
				}
			}
			else {
				if (answer.getAnswer() == givenAnswer) {
					for(Button button: options) {
						if(button.getText().equals(givenAnswer)) {
							button.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
						}
					}
				}
			}
		}
		shell.redraw();
	}
	
	public void addOpenButton(){
		final Button submitButton = new Button( shell , SWT.PUSH );
		submitButton.setText( "Open" );
		FormData submitData = new FormData();
		if(options[0] != null) {
			submitData.top = new FormAttachment(options[options.length-1],5);
		}
		else {
			submitData.bottom = new FormAttachment(95,0);
		}
		submitData.left = new FormAttachment(5,0);
		submitButton.setLayoutData(submitData);
		submitButton.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				  fd.setText("Open");
				  String[] filterExt = { "*.txt"};
				  fd.setFilterExtensions(filterExt);
				  filename = fd.open();
				  readFile();
				  addQuestion(0);
				  shell.redraw();
				
			}
		});
	}
	
    public void readFile() {
    	
    	FileReader fr = null;
    	try {
    		fr = new FileReader(filename);
    	} catch (FileNotFoundException e) {
    		System.out.println("-----------------File not found!-----------------");
    		e.printStackTrace();
    	}
    	BufferedReader br = new BufferedReader(fr);
    	

    	String line;
    	try {
    		while ( (line = br.readLine()) != null) {
    			Question question = new Question(line);
    			for(int i = 0; i < NUMBER_ANSWERS_PER_QUESTION; i++) {
    				line = br.readLine();
    				Answer answer = new Answer(line);
    				question.addAnswer(answer);
    			}
    			br.readLine();
    			questions.add(question);
    		}
    	} catch (IOException e1) {
    		System.out.println("-----------------Could not read line!-----------------");
    		e1.printStackTrace();
    	}
    	
    	try {
    		br.close();
    	} catch (IOException e) {
    		System.out.println("-----------------Could not close file-----------------");
    		e.printStackTrace();
    	}
    	
    	/*for(Question question: questions) {
    		System.out.println(question);
    	}*/
    }
	
}
