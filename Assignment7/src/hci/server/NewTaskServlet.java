package hci.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ibm.icu.util.Calendar;

import hci.client.NewTaskService;

public class NewTaskServlet extends RemoteServiceServlet implements NewTaskService {

	private Database database;
	
	public NewTaskServlet() {
		super();
		database = new Database();
	}
	@Override
	public ArrayList<String> addTask(Date date, String task, int id) {
		//HttpServletRequest request = this.getThreadLocalRequest();
	   // HttpSession session = request.getSession();
	   // session.setAttribute("done", doneButtons);
	   // session.setAttribute("undo", undoButtons);
		
		ArrayList<String> results = new ArrayList<String>();
		Task _task = new Task();
		java.util.Calendar today = java.util.Calendar.getInstance();
		_task.setCreationDate(today);
		_task.setTask(task);
		if(date == null) {
			date = today.getTime();
		}
		java.util.Calendar dueDate = java.util.Calendar.getInstance();
		dueDate.setTime(date);
		_task.setDueDate(dueDate);
		_task.setCompleted(false);
		_task.setId(id);
		String result = "";
		database.addTask(_task);
		String stringDate = "";
		for(int i = database.getTaskList().size() - 1; i >=0; i--){
			if(!database.getTaskList().get(i).isCompleted()){
				if(!database.getTaskList().get(i).getDueDate().getTime().equals(database.getTaskList().get(i).getCreationDate().getTime())) {
					stringDate = database.getTaskList().get(i).getDueDate().getTime().toString();
				}
				result += "<tr><td>" + database.getTaskList().get(i).getTask() + "</td>"
						+ "<td>" + database.getTaskList().get(i).getCreationDate().getTime().toString() + "</td>"
						+ "<td>" + stringDate + "</td>"
								+ "<td><div id=\""+database.getTaskList().get(i).getId()+"\"></div></td></tr>";
			}
		}
		String stringID = new Integer(id).toString();
		results.add(stringID);
		results.add(result);
		return results;
	}
	
	public ArrayList<String> completeTask(int id){
		ArrayList<String> result = new ArrayList<String>();
		database.completeTask(id);
		String completedResult = "";
		String TODOResult = "";
		String stringDate = "";
		for(int i = database.getTaskList().size() - 1; i >=0; i--){
			if(database.getTaskList().get(i).isCompleted()){
				if(!database.getTaskList().get(i).getDueDate().getTime().equals(database.getTaskList().get(i).getCreationDate().getTime())) {
					stringDate = database.getTaskList().get(i).getDueDate().getTime().toString();
				}
				completedResult += "<tr><td>" + database.getTaskList().get(i).getTask() + "</td>"
						+ "<td>" + database.getTaskList().get(i).getCreationDate().getTime().toString() + "</td>"
						+ "<td>" + stringDate + "</td>"
						+"<td><div id=\"u"+database.getTaskList().get(i).getId()+"\"></div></td></tr>";
			}
			else {
				if(!database.getTaskList().get(i).getDueDate().getTime().equals(new Date(0))) {
					stringDate = database.getTaskList().get(i).getDueDate().getTime().toString();
				}
				TODOResult += "<tr><td>" + database.getTaskList().get(i).getTask() + "</td>"
						+ "<td>" + database.getTaskList().get(i).getCreationDate().getTime().toString() + "</td>"
						+ "<td>" + stringDate + "</td>"
						+ "<td><div id=\""+database.getTaskList().get(i).getId()+"\"></div></td></tr>";
			}
		}
		result.add(completedResult);
		result.add(TODOResult);
		String stringID = new Integer(id).toString();
		result.add(stringID);
		return result;
	}
	
	public ArrayList<String> undoTask(int id){
		ArrayList<String> result = new ArrayList<String>();
		database.undoTask(id);
		String completedResult = "";
		String TODOResult = "";
		String stringDate = "";
		for(int i = database.getTaskList().size() - 1; i >=0; i--){
			if(database.getTaskList().get(i).isCompleted()){
				if(!database.getTaskList().get(i).getDueDate().getTime().equals(database.getTaskList().get(i).getCreationDate().getTime())) {
					stringDate = database.getTaskList().get(i).getDueDate().getTime().toString();
				}
				completedResult += "<tr><td>" + database.getTaskList().get(i).getTask() + "</td>"
						+ "<td>" + database.getTaskList().get(i).getCreationDate().getTime().toString() + "</td>"
						+ "<td>" + stringDate + "</td>"
						+"<td><div id=\"u"+database.getTaskList().get(i).getId()+"\"></div></td></tr>";
			}
			else {
				if(!database.getTaskList().get(i).getDueDate().getTime().equals(new Date(0))) {
					stringDate = database.getTaskList().get(i).getDueDate().getTime().toString();
				}
				TODOResult += "<tr><td>" + database.getTaskList().get(i).getTask() + "</td>"
						+ "<td>" + database.getTaskList().get(i).getCreationDate().getTime().toString() + "</td>"
						+ "<td>" + stringDate + "</td>"
						+ "<td><div id=\""+database.getTaskList().get(i).getId()+"\"></div></td></tr>";
			}
		}
		result.add(completedResult);
		result.add(TODOResult);
		String stringID = new Integer(id).toString();
		result.add(stringID);
		return result;
	}
	
	public String clearCompleted() {
		String stringDate = "";
		String result = "";
		Database database2 = new Database();
		for(int i = 0; i < database.getTaskList().size(); i++) {
			if(!database.getTaskList().get(i).isCompleted()) {
				database2.addTask(database.getTaskList().get(i));
			}
		}
		//database.clearCompletedTasks();
		database = database2;
		for(int i = database.getTaskList().size() - 1; i >=0; i--){
			if(!database.getTaskList().get(i).isCompleted()){
				if(!database.getTaskList().get(i).getDueDate().getTime().equals(database.getTaskList().get(i).getCreationDate().getTime())) {
					stringDate = database.getTaskList().get(i).getDueDate().getTime().toString();
				}
				result += "<tr><td>" + database.getTaskList().get(i).getTask() + "</td>"
						+ "<td>" + database.getTaskList().get(i).getCreationDate().getTime().toString() + "</td>"
						+ "<td>" + stringDate + "</td>"
								+ "<td><div id=\""+database.getTaskList().get(i).getId()+"\"></div></td></tr>";
			}
		}
		return result;
	}
	
	public ArrayList<String> updateTasks(){
		database = new Database();
		
		ArrayList<String> result = new ArrayList<String>();
		String completedResult = "";
		String TODOResult = "";
		String stringDate = "";
		for(int i = database.getTaskList().size() - 1; i >=0; i--){
			if(database.getTaskList().get(i).isCompleted()){
				if(!database.getTaskList().get(i).getDueDate().getTime().equals(database.getTaskList().get(i).getCreationDate().getTime())) {
					stringDate = database.getTaskList().get(i).getDueDate().getTime().toString();
				}
				completedResult += "<tr><td>" + database.getTaskList().get(i).getTask() + "</td>"
						+ "<td>" + database.getTaskList().get(i).getCreationDate().getTime().toString() + "</td>"
						+ "<td>" + stringDate + "</td>"
						+"<td><div id=\"u"+database.getTaskList().get(i).getId()+"\"></div></td></tr>";
			}
			else {
				if(!database.getTaskList().get(i).getDueDate().getTime().equals(new Date(0))) {
					stringDate = database.getTaskList().get(i).getDueDate().getTime().toString();
				}
				TODOResult += "<tr><td>" + database.getTaskList().get(i).getTask() + "</td>"
						+ "<td>" + database.getTaskList().get(i).getCreationDate().getTime().toString() + "</td>"
						+ "<td>" + stringDate + "</td>"
						+ "<td><div id=\""+database.getTaskList().get(i).getId()+"\"></div></td></tr>";
			}
		}
		result.add(completedResult);
		result.add(TODOResult);
		return result;
	}
	
	public ArrayList<Button> getDoneButtons() {
		  HttpServletRequest request = this.getThreadLocalRequest();
	      HttpSession session = request.getSession();
	      return (ArrayList<Button>) session.getAttribute("done");
	   }
	

}
