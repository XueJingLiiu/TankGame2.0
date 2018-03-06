package blast;

public class BornFactory implements BlastFactory {

	@Override
	public Blast add(int x, int y) {
		return new Born(x, y);
	}

}
