import java.awt.*;
import javax.swing.*;

public class ImageClass extends JLabel {
	private Image img;
	private ImageIcon icon;
	
	public void setIcon(Icon icon) {
		super.setIcon(icon);
		this.icon = (ImageIcon)super.getIcon();
		if (icon instanceof ImageIcon){
            img = ((ImageIcon) icon).getImage();
        }
	}
	
	public void paint(Graphics g){
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
    }
	
	
}
