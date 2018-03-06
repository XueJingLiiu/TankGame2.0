package bullet;

public class EnemyBulletFactory implements BulletFactory {

	@Override
	public Bullet add(int x, int y, int direct) {
		Bullet b = new EnemyBullet(x, y);
		b.setDirect(direct);
		return b;
	}

}
