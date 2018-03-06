package map_item;
/*
 * 抽象产品类
 */
public abstract class MapItem {
	int x, y;    		
	int width, height;
	int[] position;  
	boolean isLive;
	int[] rect = null;
	public int[] getRect() {
		return rect;
	}
	public void setRect() {
		int[] rect = new int[4];
		rect[0] = x;
		rect[1] = y;
		rect[2] = x + width;
		rect[3] = y + height;
		this.rect = rect;
	}
	public MapItem(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		width = 60;
		height = 60;
		isLive = true;
		setPosition();
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int[] getPosition() {
		return position;
	}
	public void setPosition() {
		int[] position = new int[2];
		position[0] = y / 60;
		position[1] = x / 60;
		this.position = position;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
}
