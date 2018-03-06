package blast;

public class Born extends Blast{

	public Born(int x, int y) {
		super(x, y);
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
				Thread.sleep(125);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			life--;
			setLife(life);
			if (life <= 0){		// 如果出生场面的生命减到了0
				isLive = false;	// 这个出生场面就死了
			}
		}
	}
}
