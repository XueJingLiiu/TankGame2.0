package tank;

public class Enemy2Factory implements EnemyFactory {

	@Override
	public Tank add(int x, int y) {
		return new Enemy2(x, y);
	}

}
