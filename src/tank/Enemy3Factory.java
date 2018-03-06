package tank;

public class Enemy3Factory implements EnemyFactory {

	@Override
	public Tank add(int x, int y) {
		return new Enemy3(x, y);
	}

}
