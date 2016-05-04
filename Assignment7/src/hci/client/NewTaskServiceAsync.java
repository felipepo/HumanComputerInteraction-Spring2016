package hci.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

public interface NewTaskServiceAsync {
	public void addTask(java.util.Date date, String task, int id, AsyncCallback<ArrayList<String>> callback);
	public void completeTask(int index, AsyncCallback<ArrayList<String>> callback);
	public void undoTask(int index, AsyncCallback<ArrayList<String>> callback);
	public void clearCompleted(AsyncCallback<String> callback);
	public void updateTasks(AsyncCallback<ArrayList<String>> callback);
	public void getDoneButtons(AsyncCallback<ArrayList<Button>> callback);
}
