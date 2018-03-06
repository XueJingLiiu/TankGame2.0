package map_item;

public class GameOverFactory implements MapItemFactory {

	@Override
	public MapItem add(int x, int y) {
		return new GameOver(x, y);
	}

}
