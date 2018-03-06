package blast;

public class MapItemBlastFactory implements BlastFactory {

	@Override
	public Blast add(int x, int y) {
		return new MapItemBlast(x, y);
	}

}
