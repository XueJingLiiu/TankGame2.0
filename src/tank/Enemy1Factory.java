package tank;

public class Enemy1Factory implements EnemyFactory {

	@Override
	public Tank add(int x, int y) {
		return new Enemy1(x, y);
	}

}
