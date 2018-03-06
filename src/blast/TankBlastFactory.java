package blast;

public class TankBlastFactory implements BlastFactory {

	@Override
	public Blast add(int x, int y) {
		return new TankBlast(x, y);
	}

}
