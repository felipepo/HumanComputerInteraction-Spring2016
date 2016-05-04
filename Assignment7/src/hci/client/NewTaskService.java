package hci.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.Button;
@RemoteServiceRelativePath("tasks")
public interface NewTaskService extends RemoteService{
	public ArrayList<String> addTask(java.util.Date date, String task, int id);
	public ArrayList<String> completeTask(int index);
	public ArrayList<String> undoTask(int index);
	public String clearCompleted();
	public ArrayList<String> updateTasks();
	public ArrayList<Button> getDoneButtons();
}
