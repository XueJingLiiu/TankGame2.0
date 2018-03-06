package map_item;

import java.util.Timer;
import java.util.TimerTask;

public class GameOver extends MapItem{

	public GameOver(int x, int y) {
		super(x, y);
		width = 3 * 60;
		height = 2 * 60;
		isLive = false;
	}
	public void over(){
		isLive = true;
		TimerTask over = new TimerTask() {
			
			@Override
			public void run() {
				if (getY() >= 6*60) {		// 如果纵坐标大于360
					setY(getY() - 10);		// 纵坐标减少10
				}
			}
		};
		Timer timer = new Timer();
		timer.schedule(over, 0, 100); 	// 计时器：每一百毫秒减少一次纵坐标
	}
}
