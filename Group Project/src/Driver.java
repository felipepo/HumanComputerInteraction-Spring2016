
import controller.Controller;
import model.Database;
import view.InfoView;
import view.ThumbnailsView;

public class Driver {
	
	
	
	public static void main(String[] args) {
		Database model = new Database();
		ThumbnailsView thumbnailsView = new ThumbnailsView();
		InfoView infoView = new InfoView(0);
		thumbnailsView.setModel(model);
		infoView.setModel(model);
		Controller controller = new Controller(thumbnailsView, infoView, model);
		
	}
}
