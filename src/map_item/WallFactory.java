package map_item;
/*
 * 具体工厂类
 */
public class WallFactory implements MapItemFactory {

	@Override
	public MapItem add(int x, int y) {
		return new Wall(x, y);
	}

}
