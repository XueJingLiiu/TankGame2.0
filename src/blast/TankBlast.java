package blast;

public class TankBlast extends Blast{

	public TankBlast(int x, int y) {
		super(x-20, y-20);
		setWidth(60);
		setHeight(60);
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
			life--;
			setLife(life);
			if (life <= 0){		// 如果爆炸场面的生命减到了0
				isLive = false;	// 这个爆炸场面就死了
			}
		}
	}

}
