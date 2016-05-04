package application;

public class Target {
	private double x;
	private double y;
	private double width;
	private boolean success;
	private double ID;
	private long MT;
	
	Target(double appWidth, double appHeight) {
		success = false;
		x = Math.random() * appWidth;
		y = Math.random() * appHeight;
		width = Math.random() * (0.2 * appWidth);
		if (width < 0.3 * 0.2 * appWidth) {
			width = 0.3 * 0.2 * appWidth;
		}
		if (width > 0.6 * 0.2 * appWidth) {
			width = 0.6 * 0.2 * appWidth;
		}
		if(x < width) {
			x = width;
		}
		if(x+width > appWidth) {
			x = appWidth - width;
		}
		if(y < width) {
			y = width;
		}
		if(y+width > appHeight) {
			y = appHeight - width;
		}
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public double getID() {
		return ID;
	}

	public void setID(double distance) {
		ID = Math.log10(distance/width + 1);
	}

	public long getMT() {
		return MT;
	}

	public void setMT(long mT) {
		MT = mT;
	}
}
