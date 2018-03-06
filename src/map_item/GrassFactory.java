package map_item;

public class GrassFactory implements MapItemFactory {

	@Override
	public MapItem add(int x, int y) {
		return new Grass(x, y);
	}

}
