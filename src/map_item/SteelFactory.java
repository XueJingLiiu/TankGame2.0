package map_item;

public class SteelFactory implements MapItemFactory {

	@Override
	public MapItem add(int x, int y) {
		return new Steel(x, y);
	}

}
