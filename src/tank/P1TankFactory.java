package tank;

/*
 * 具体工厂类
 */
public class P1TankFactory implements TankFactory {

	@Override
	public Tank add(int x, int y) {
		return new P1Tank(x, y);
	}
}
