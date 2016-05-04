package hci.server;

import java.util.ArrayList;
import java.util.Calendar;

public class Database {
	private ArrayList<Task> taskList;
	
	public Database(){
		taskList = new ArrayList<Task>();
	}
	
	public ArrayList<Task> getTaskList(){
		return this.taskList;
	}
	
	public void addTask(Task task){
		Calendar today = Calendar.getInstance();
		if(!task.getDueDate().before(today)) {
			//taskList.add(task);
		}
		if(task.getDueDate().compareTo(today) >= 0) {
			taskList.add(task);
		}
	}
	
	public void completeTask(int id){
		for(int i = 0; i < taskList.size(); i++){
			if (taskList.get(i).getId() == id){
				taskList.get(i).setCompleted(true);
			}
		}
	}
	
	public void undoTask(int id){
		for(int i = 0; i < taskList.size(); i++){
			if (taskList.get(i).getId() == id){
				taskList.get(i).setCompleted(false);
			}
		}
	}
	
	public void clearCompletedTasks() {
		for(int i = 0; i < taskList.size(); i++) {
			if(taskList.get(i).isCompleted()){
				taskList.remove(i);
			}
		}
	}
	
}
