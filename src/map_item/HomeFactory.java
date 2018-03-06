package map_item;

public class HomeFactory implements MapItemFactory {

	@Override
	public MapItem add(int x, int y) {
		return new Home(x, y);
	}

}
