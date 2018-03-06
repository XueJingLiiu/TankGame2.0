package map_item;

public class WaterFactory implements MapItemFactory {

	@Override
	public MapItem add(int x, int y) {
		return new Water(x, y);
	}

}
