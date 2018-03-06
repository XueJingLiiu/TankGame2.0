package bullet;

public class P2BulletFactory implements BulletFactory {

	@Override
	public Bullet add(int x, int y, int direct) {
		Bullet b = new P2Bullet(x, y);
		b.setDirect(direct);
		return b;
	}

}
