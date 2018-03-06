package tank;

import bullet.Bullet;
import bullet.P2BulletFactory;

public class P2Tank extends Tank{

	public P2Tank(int x, int y) {
		super(x, y);
		setDirect(0);
	}
	public Bullet fire() {
		P2BulletFactory P2BulletFactory = new P2BulletFactory();
		int x = this.getX() + (this.getWidth()/2);
		int y = this.getY() + (this.getHeight()/2);
		Bullet p2Bullet = P2BulletFactory.add(x, y, this.getDirect());
		return p2Bullet;
	}

}
