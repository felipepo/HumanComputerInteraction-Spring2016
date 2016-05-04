import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThumbnailListener implements ActionListener{
	int index;


	public ThumbnailListener(int i) {
		super();
		index = i;
	}
	
	public int getIndex() {
		return index;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
