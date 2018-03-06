package blast;

public class MapItemBlast extends Blast{

	public MapItemBlast(int x, int y) {
		super(x-5, y-5);
		setWidth(30);
		setHeight(30);
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		int life = getLife();
		while (true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			life -= 2;
			setLife(life);
			if (life <= 0){		// 如果爆炸场面的生命减到了0
				isLive = false;	// 这个爆炸场面就死了
			}
		}
	}

}
