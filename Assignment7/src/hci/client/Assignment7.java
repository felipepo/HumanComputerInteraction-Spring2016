package hci.client;

import hci.shared.FieldVerifier;
import sun.util.calendar.BaseCalendar.Date;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Assignment7 implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final NewTaskServiceAsync newTaskService = GWT.create(NewTaskService.class);

	java.util.Date selectedDate;
	
	static int taskId = -1;
	static ArrayList<Button> doneButtons = new ArrayList<Button>();
	static ArrayList<Button> undoButtons = new ArrayList<Button>();
	
	public Assignment7() {
		super();
		//doneButtons = new ArrayList<Button>();
		//undoButtons = new ArrayList<Button>();
	}
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		newTaskService.updateTasks(new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<String> result) {
				
				RootPanel.get("completedContainer").getElement().setInnerHTML(result.get(0));
				RootPanel.get("tableContainer").getElement().setInnerHTML(result.get(1));
				newTaskService.getDoneButtons(new AsyncCallback<ArrayList<Button>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ArrayList<Button> result) {
						//update done buttons
						for(int i = 0; i < result.size(); i++) {
							String buttonID = result.get(i).getTitle();
							if(DOM.getElementById(buttonID) != null) {
								DOM.sinkEvents(result.get(i).getElement(), Event.ONCLICK);
								//NOT BEING ADDED
								DOM.getElementById(buttonID).appendChild(result.get(i).getElement());
							}
						}
						
					}
					
				});
			}
		});
		
		
		
		final DialogBox newTaskDialogBox = new DialogBox();
		final Button clearTasksButton = new Button("Clear Completed");
		newTaskDialogBox.setTitle("New TODO Task");
		newTaskDialogBox.setText("New TODO Task");
		newTaskDialogBox.setAnimationEnabled(true);
		final HTML serverResponseLabel = new HTML();
		VerticalPanel newTaskVPanel = new VerticalPanel();
		newTaskVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		newTaskVPanel.add(new Label("Task Description"));
		final TextArea taskField = new TextArea();
		taskField.setVisibleLines(5);
		taskField.setCharacterWidth(30);
		newTaskVPanel.add(taskField);
		newTaskVPanel.add(new Label("Due Date"));
		final DatePicker dueDateField = new DatePicker();
		dueDateField.addValueChangeHandler(new ValueChangeHandler<java.util.Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<java.util.Date> event) {
				selectedDate = event.getValue();
			}
			
		});
		final Button newTaskButton = new Button("New Task");
		// We can add style names to widgets
		newTaskButton.addStyleName("sendButton");
		newTaskVPanel.add(dueDateField);
		final Button closeButton = new Button("Close");
		closeButton.getElement().setId("closeButton");
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				newTaskDialogBox.hide();
				newTaskButton.setEnabled(true);
				newTaskButton.setFocus(true);
				clearTasksButton.setEnabled(true);
				clearTasksButton.setFocus(true);
			}
		});
		newTaskVPanel.add(closeButton);
		
		final Button enterButton = new Button("Enter");
		enterButton.getElement().setId("enterButton");
		enterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String task = taskField.getText();
				sendNewTask(task);
			}
			
			protected void sendNewTask(String task) {
				taskId++;
				newTaskService.addTask(selectedDate, task, taskId, new AsyncCallback<ArrayList<String>>() {

					@Override
					public void onFailure(Throwable caught) {
						serverResponseLabel.setHTML("ADDITION FAILED! <br>Maybe the date is prior than today");
						newTaskDialogBox.clear();
						newTaskDialogBox.add(serverResponseLabel);
						newTaskDialogBox.add(closeButton);
						newTaskDialogBox.center();
					}

					@Override
					public void onSuccess(ArrayList<String> results) {
						String result = results.get(1);
						final int ID = Integer.parseInt(results.get(0));
						//create new done button
						Button doneButton = new Button("Done");
						doneButton.setTitle(results.get(0));  //button title is the ID of the task
						DOM.sinkEvents(doneButton.getElement(), Event.ONCLICK);
						DOM.setEventListener(doneButton.getElement(), new EventListener() {

							@Override
							public void onBrowserEvent(Event event) {
								if (Event.ONCLICK == event.getTypeInt()) {
									completeTask(ID);
								}
							}
							
							private void completeTask(int id) {
								newTaskService.completeTask(id, new AsyncCallback<ArrayList<String>>(){

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void onSuccess(ArrayList<String> result) {
										RootPanel.get("completedContainer").getElement().setInnerHTML(result.get(0));
										RootPanel.get("tableContainer").getElement().setInnerHTML(result.get(1)); // cause all done buttons to be removed
										final int ID = Integer.parseInt(result.get(2));
										
										//update undo buttons
										for(int i = 0; i < undoButtons.size(); i++) {
											String buttonID = undoButtons.get(i).getTitle();
											if(DOM.getElementById("u" + buttonID) != null) {
												DOM.sinkEvents(undoButtons.get(i).getElement(), Event.ONCLICK);
												DOM.getElementById("u" +buttonID).appendChild(undoButtons.get(i).getElement());
											}
										}
										//update done buttons
										for(int i = 0; i < doneButtons.size(); i++) {
											String buttonID = doneButtons.get(i).getTitle();
											if(DOM.getElementById(buttonID) != null) {
												DOM.sinkEvents(doneButtons.get(i).getElement(), Event.ONCLICK);
												DOM.getElementById(buttonID).appendChild(doneButtons.get(i).getElement());
											}
										}
										
										Button undoButton = new Button("Undo");
										undoButton.setTitle(result.get(2)); // button title is the id of the task
										DOM.sinkEvents(undoButton.getElement(), Event.ONCLICK);
										DOM.setEventListener(undoButton.getElement(), new EventListener() {

											@Override
											public void onBrowserEvent(Event event) {
												if (Event.ONCLICK == event.getTypeInt()) {
													undoTask(ID);
												}
											}
											
											public void undoTask(int id) {
												newTaskService.undoTask(id, new AsyncCallback<ArrayList<String>>() {

													@Override
													public void onFailure(Throwable caught) {
														// TODO Auto-generated method stub
														
													}

													@Override
													public void onSuccess(ArrayList<String> result) {
														RootPanel.get("completedContainer").getElement().setInnerHTML(result.get(0));
														RootPanel.get("tableContainer").getElement().setInnerHTML(result.get(1)); // cause all done buttons to be removed
														//update undo buttons
														for(int i = 0; i < undoButtons.size(); i++) {
															String buttonID = undoButtons.get(i).getTitle();
															if(DOM.getElementById("u" + buttonID) != null) {
																DOM.sinkEvents(undoButtons.get(i).getElement(), Event.ONCLICK);
																DOM.getElementById("u" +buttonID).appendChild(undoButtons.get(i).getElement());
															}
														}
														
														//update done buttons
														for(int i = 0; i < doneButtons.size(); i++) {
															String buttonID = doneButtons.get(i).getTitle();
															if(DOM.getElementById(buttonID) != null) {
																DOM.sinkEvents(doneButtons.get(i).getElement(), Event.ONCLICK);
																DOM.getElementById(buttonID).appendChild(doneButtons.get(i).getElement());
															}
														}
														
													}
													
												});
											}
										});
										
										boolean found = false;
										for(int i = 0; i < undoButtons.size(); i++) {
											if(undoButton.getTitle().equals(undoButtons.get(i).getTitle())){
												found = true;
											}
										}
										if(!found){
											undoButtons.add(undoButton);
										}
										//update undo buttons
										for(int i = 0; i < undoButtons.size(); i++) {
											String buttonID = undoButtons.get(i).getTitle();
											if(DOM.getElementById("u" + buttonID) != null) {
												DOM.sinkEvents(undoButtons.get(i).getElement(), Event.ONCLICK);
												DOM.getElementById("u" +buttonID).appendChild(undoButtons.get(i).getElement());
											}
										}
										
										//update done buttons
										for(int i = 0; i < doneButtons.size(); i++) {
											String buttonID = doneButtons.get(i).getTitle();
											if(DOM.getElementById(buttonID) != null) {
												DOM.sinkEvents(doneButtons.get(i).getElement(), Event.ONCLICK);
												DOM.getElementById(buttonID).appendChild(doneButtons.get(i).getElement());
											}
										}
									}
									
								});	
							}
							
						});
						
						boolean found = false;
						for(int i = 0; i < doneButtons.size(); i++) {
							if(doneButton.getTitle().equals(doneButtons.get(i).getTitle())){
								found = true;
							}
						}
						if(!found){
							doneButtons.add(doneButton);
						}
						newTaskDialogBox.hide();
						newTaskButton.setEnabled(true);
						newTaskButton.setFocus(true);
						clearTasksButton.setEnabled(true);
						clearTasksButton.setFocus(true);
						RootPanel.get("tableContainer").getElement().setInnerHTML(result); // cause all done buttons to be removed
						
						//update undo buttons
						for(int i = 0; i < undoButtons.size(); i++) {
							String buttonID = undoButtons.get(i).getTitle();
							if(DOM.getElementById("u" + buttonID) != null) {
								DOM.sinkEvents(undoButtons.get(i).getElement(), Event.ONCLICK);
								DOM.getElementById("u" +buttonID).appendChild(undoButtons.get(i).getElement());
							}
						}
						//update done buttons
						for(int i = 0; i < doneButtons.size(); i++) {
							String buttonID = doneButtons.get(i).getTitle();
							if(DOM.getElementById(buttonID) != null) {
								DOM.sinkEvents(doneButtons.get(i).getElement(), Event.ONCLICK);
								DOM.getElementById(buttonID).appendChild(doneButtons.get(i).getElement());
							}
						}
					}
					
				});
				
			}
		});
		
		newTaskVPanel.add(enterButton);
		
		newTaskDialogBox.setWidget(newTaskVPanel);
		
		
		newTaskButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				newTaskButton.setEnabled(false);
				newTaskButton.setFocus(false);
				clearTasksButton.setEnabled(false);
				clearTasksButton.setFocus(false);
				newTaskDialogBox.center();
			}
			
		});
		RootPanel.get("newTaskButtonContainer").add(newTaskButton);
		
		clearTasksButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				newTaskService.clearCompleted(new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(String result) {
						RootPanel.get("completedContainer").getElement().setInnerHTML("");
						RootPanel.get("tableContainer").getElement().setInnerHTML(result);
						//update undo buttons
						for(int i = 0; i < undoButtons.size(); i++) {
							String buttonID = undoButtons.get(i).getTitle();
							System.out.println(DOM.getElementById("u" + buttonID));
							if(DOM.getElementById("u" + buttonID) != null) {
								DOM.sinkEvents(undoButtons.get(i).getElement(), Event.ONCLICK);
								DOM.getElementById("u" +buttonID).appendChild(undoButtons.get(i).getElement());
							}
						}
						
						//update done buttons
						for(int i = 0; i < doneButtons.size(); i++) {
							String buttonID = doneButtons.get(i).getTitle();
							if(DOM.getElementById(buttonID) != null) {
								DOM.sinkEvents(doneButtons.get(i).getElement(), Event.ONCLICK);
								DOM.getElementById(buttonID).appendChild(doneButtons.get(i).getElement());
							}
						}
					}
					
				});
				
			}
			
		});
		RootPanel.get("clearTasksButtonContainer").add(clearTasksButton);
	}
	
}
