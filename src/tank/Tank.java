package tank;

import map.Map;

/*
 * 抽象产品类
 */
public abstract class Tank{
	private int x, y;    		// 坦克的横纵坐标
	private int width, height;  // 坦克的宽高
	private int speed;   		// 坦克的移动速度
	private int direct;   		// 坦克方向
	private int[] rect;			// 坦克的矩形
	private boolean isLive;     // 坦克是否存活
	private int armor; 			// 坦克的盔甲数
	private int[][] map; 		// 地图
	private int stage; 			// 关卡

	public Tank(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		speed = 5;
		width = 50;
		height = 50;
		armor = 1;
		isLive = true;
	}
	
	public void moveUp(){
		y -= speed;
	}
	
	public void moveDown(){
		y += speed;
	}
	
	public void moveLeft(){
		x -= speed;
	}
	
	public void moveRight(){
		x += speed;
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

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap() {
		int[][] map = new int[13][13];
		switch (stage) {
		case 1: map = Map.map1; break;
		case 2: map = Map.map2; break;
		case 3: map = Map.map3; break;
		case 4: map = Map.map4; break;
		case 5: map = Map.map5; break;
		case 6: map = Map.map6; break;
		case 7: map = Map.map7; break;
		}
		this.map = map;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
		setMap();
	}

	public boolean isColliding(int direct) {
		boolean b = false, b1 = false, b2 = false;  // b1、b2用于检测坦克周围的格子
		int x = this.getX(), y = this.getY(), w = this.getWidth(), h = this.getHeight();
		int x1 = x, x2 = (x+w), y1 = y, y2 = (y+h);
		if (x<=0 || y<=0 || x2>=780 || y2>=780){  	// 边界处理
			b = true;
			return b;
		}
		switch (direct){
		case 0: 		// 左上与右上
			y1 /= 60;
			x1 /= 60; 
			x2 /= 60; 
			b1 = map[y1][x1] != 0 && map[y1][x1] != 6 && map[y1][x1] != 7 && map[y1][x1] != 8 && map[y1][x1] != 4;
			b2 = map[y1][x2] != 0 && map[y1][x2] != 6 && map[y1][x2] != 7 && map[y1][x2] != 8 && map[y1][x2] != 4;
			break;
		case 1: 		// 左下与右下
			y2 /= 60;
			x1 /= 60; 
			x2 /= 60; 
			b1 = map[y2][x1] != 0 && map[y2][x1] != 6 && map[y2][x1] != 7 && map[y2][x1] != 8 && map[y2][x1] != 4;
			b2 = map[y2][x2] != 0 && map[y2][x2] != 6 && map[y2][x2] != 7 && map[y2][x2] != 8 && map[y2][x2] != 4;
			break;
		case 2: 		// 左上与左下
			x1 /= 60; 
			y1 /= 60;
			y2 /= 60;
			b1 = map[y1][x1] != 0 && map[y1][x1] != 6 && map[y1][x1] != 7 && map[y1][x1] != 8 && map[y1][x1] != 4;
			b2 = map[y2][x1] != 0 && map[y2][x1] != 6 && map[y2][x1] != 7 && map[y2][x1] != 8 && map[y2][x1] != 4;
			break;
		case 3:			// 右上与右下
			x2 /= 60;
			y1 /= 60;
			y2 /= 60; 
			b1 = map[y1][x2] != 0 && map[y1][x2] != 6 && map[y1][x2] != 7 && map[y1][x2] != 8 && map[y1][x2] != 4;
			b2 = map[y2][x2] != 0 && map[y2][x2] != 6 && map[y2][x2] != 7 && map[y2][x2] != 8 && map[y2][x2] != 4;
			break;
		}
		if (b1 || b2){
			b = true;
			return b;
		}
		return b;
	}
}
