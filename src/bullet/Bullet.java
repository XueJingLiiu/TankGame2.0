package bullet;

import blast.Blast;
import blast.MapItemBlastFactory;
import blast.TankBlastFactory;

public abstract class Bullet implements Runnable{
	private int x, y;
	private int width, height;
	private int speed;
	private int direct;
	private boolean isLive;
	private boolean isTank = false;					// 判断子弹撞击的是否为坦克
	int[] rect = null;
	int[] position;     							// 子弹在地图上的坐标
	public int[] getPosition() {
		return position;
	}
	public void setPosition() {
		int[] position = new int[2];
		position[0] = y / 60;
		position[1] = x / 60;
		this.position = position;
	}
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
	public Bullet(int x, int y) {
		super();
		speed = 20;
		width = 10;
		height = 10;
		isLive = true;
		this.x = x - (width/2);
		this.y = y - (height/2);
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
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	public boolean isTank() {
		return isTank;
	}
	public void setTank(boolean isTank) {
		this.isTank = isTank;
	}
	public Blast blast(){
		Blast blast = null;
		if (!isTank){
			MapItemBlastFactory m_factory = new MapItemBlastFactory();
			Blast mapItemBlast = m_factory.add(getX(), getY());
			blast = mapItemBlast;
		} else {
			TankBlastFactory t_factory = new TankBlastFactory();
			Blast tankBlast = t_factory.add(getX(), getY());
			blast = tankBlast;
		}
		return blast;
	}
}
