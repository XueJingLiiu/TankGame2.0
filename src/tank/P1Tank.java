package tank;

import bullet.Bullet;
import bullet.P1BulletFactory;

/*
 * 具体产品类
 */
public class P1Tank extends Tank{
	
	public P1Tank(int x, int y) {
		super(x, y);
		setDirect(0);
	}

	public Bullet fire() {
		P1BulletFactory p1BulletFactory = new P1BulletFactory();
		int x = this.getX() + (this.getWidth()/2);
		int y = this.getY() + (this.getHeight()/2);
		Bullet p1Bullet = p1BulletFactory.add(x, y, this.getDirect());
		return p1Bullet;
	}
}
