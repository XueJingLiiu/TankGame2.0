package map_item;

public class BombFactory implements MapItemFactory {

	@Override
	public MapItem add(int x, int y) {
		return new Bomb(x, y);
	}

}
