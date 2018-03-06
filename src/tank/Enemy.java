package tank;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Timer;

import bullet.Bullet;
import bullet.EnemyBulletFactory;

public class Enemy extends Tank{
	Vector<Bullet> enemybs = new Vector<Bullet>();
	Timer fireTimer = null, directTimer = null;	// 创建两个计时器（发射子弹、改变方向）
	Timer moveTimer = null;		// 控制移动的计时器
	boolean isMoving = true;

	public Enemy(int x, int y) {
		super(x+1, y);		// +1是为了让第一个坦克动起来
		setDirect(1);
		
		ActionListener fire = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isMoving) {
					fire();
				}
			}
		};
		fireTimer = new Timer(2000, fire);				// 2秒钟发射一次子弹
		fireTimer.start();
		
		ActionListener changeDirect = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isMoving) {
					setDirect((int)(Math.random()*4));
				}
			}
		};
		directTimer = new Timer(5000, changeDirect);	// 5秒钟改变一次方向
		directTimer.start();
		
		ActionListener move = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isMoving) {
					move();
				}
			}
		};
		moveTimer = new Timer(50, move);				// 50毫秒移动一次
		moveTimer.start();
	}

	public Vector<Bullet> getenemybs() {
		return enemybs;
	}

	public void setenemybs(Vector<Bullet> enemybs) {
		this.enemybs = enemybs;
	}
	
	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public void fire() {
		int x = this.getX() + (this.getWidth()/2);
		int y = this.getY() + (this.getHeight()/2);
		EnemyBulletFactory enemyBulletFactory = new EnemyBulletFactory();
		Bullet enemyBullet = enemyBulletFactory.add(x, y, this.getDirect());
		if (enemybs.size() < 1){	// 弹夹容量
			enemybs.add(enemyBullet);
		}
	}
	
	public void move() {
		switch (getDirect()){
		case 0: 
			moveUp(); 
			if (isColliding(getDirect())){
				moveDown();
			}
			break;
		case 1: 
			moveDown();
			if (isColliding(getDirect())){
				moveUp();
			}
			break;
		case 2: 
			moveLeft(); 
			if (isColliding(getDirect())){
				moveRight();
			}
			break;
		case 3: 
			moveRight();
			if (isColliding(getDirect())){
				moveLeft();
			}
			break;
		}
	}
}
