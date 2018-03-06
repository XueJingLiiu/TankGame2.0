package bullet;

public class P1Bullet extends Bullet{
	
	public P1Bullet(int x, int y) {
		super(x, y);
		Thread t = new Thread(this);	// 开启子弹线程
		t.start();
	}

	@Override
	public void run() {					// 子弹移动
		while (true){
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			switch (getDirect()){
			case 0: setY(getY() - getSpeed()); break;
			case 1: setY(getY() + getSpeed()); break;
			case 2: setX(getX() - getSpeed()); break;
			case 3: setX(getX() + getSpeed()); break;
			}
		}
	}
}
