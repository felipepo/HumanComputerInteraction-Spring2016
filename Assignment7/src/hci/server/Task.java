package hci.server;

import java.util.Calendar;

public class Task {
	private String task;
	private Boolean completed;
	private Calendar dueDate;
	private Calendar creationDate;
	private static int numTasks = 0;
	private int id;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public static int getNumTasks(){
		return numTasks;
	}
	
	/**
	 * @return the task
	 */
	public String getTask() {
		return task;
	}
	/**
	 * @param task the task to set
	 */
	public void setTask(String task) {
		this.task = task;
	}
	/**
	 * @return the completed
	 */
	public Boolean isCompleted() {
		return completed;
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	/**
	 * @return the dueDate
	 */
	public Calendar getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * @return the creationDate
	 */
	public Calendar getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public boolean equals(Object otherTask){
		boolean result = true;
		if(this == null || otherTask == null) {
			result = false;
		}
		if(result){
			result = this.getClass().equals(otherTask.getClass());
		}
		if(result){
			result = this.getTask().equals(((Task)otherTask).getTask());
		}
		if(result){
			result = this.getDueDate().equals(((Task)otherTask).getDueDate());
		}
		return result;
	}
}
