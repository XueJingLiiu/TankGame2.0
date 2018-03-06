package tank;

public class P2TankFactory implements TankFactory {

	@Override
	public Tank add(int x, int y) {
		return new P2Tank(x, y);
	}

}
