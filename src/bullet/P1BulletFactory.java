package bullet;

public class P1BulletFactory implements BulletFactory{

	@Override
	public Bullet add(int x, int y, int direct) {
		Bullet b = new P1Bullet(x, y);
		b.setDirect(direct);
		return b;
	}

}
