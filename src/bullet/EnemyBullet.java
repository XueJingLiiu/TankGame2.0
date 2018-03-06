package bullet;

public class EnemyBullet extends Bullet{
	
	public EnemyBullet(int x, int y) {
		super(x, y);
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
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
