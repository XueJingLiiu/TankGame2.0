package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import blast.Blast;
import blast.Born;
import blast.BornFactory;
import blast.MapItemBlast;
import blast.TankBlast;
import blast.TankBlastFactory;
import bullet.Bullet;
import map.Map;
import map.RectCollide;
import map_item.BombFactory;
import map_item.GameOver;
import map_item.GameOverFactory;
import map_item.GrassFactory;
import map_item.HomeFactory;
import map_item.MapItem;
import map_item.SteelFactory;
import map_item.WallFactory;
import map_item.WaterFactory;
import sound.SoundPlayer;
import tank.Enemy;
import tank.Enemy1Factory;
import tank.Enemy2Factory;
import tank.Enemy3Factory;
import tank.P1Tank;
import tank.P1TankFactory;
import tank.P2Tank;
import tank.P2TankFactory;
import tank.Tank;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	int stage = 1; 								// 战场1
	int[][] map = new int[13][13]; 				// 地图
	int screenWidth = 780, screenHeight = 780;  // 画板宽高
	int enemyNum = 9; 							// 坦克总数量
	/* P1坦克 */
	Image[] p1_images = null;  					// P1坦克图片数组
	Vector<Tank> p1s = new Vector<Tank>();  	// p1坦克向量
	Bullet p1Bullet = null;						// p1坦克子弹
	Vector<Bullet> p1bs = new Vector<Bullet>(); // p1坦克子弹向量
	Image p1_bullet_image = null;				// p1坦克子弹图片
	int p1_life = 2; 							// p1剩余生命数
	boolean p1_ismoving = false; 				// p1移动的标志
	boolean p1_over = false; 					// p1卒
	/* P2坦克 */
	Image[] p2_images = null;  					// P2坦克图片数组
	Vector<Tank> p2s = new Vector<Tank>();  	// P2坦克向量
	Bullet p2Bullet = null;						// P2坦克子弹
	Vector<Bullet> p2bs = new Vector<Bullet>(); // P2坦克子弹向量
	Image p2_bullet_image = null;				// P2坦克子弹图片
	int p2_life = 2; 							// P2剩余生命数
	boolean p2_ismoving = false; 				// p2移动的标志
	boolean p2_over = false; 					// p2卒
	/* 敌方坦克 */
	Image[] enemy1_images = null;  					// enemy1坦克图片数组
	Image[] enemy2_images = null;  					// enemy2坦克图片数组
	Image[] enemy3_images = null;  					// enemy3坦克图片数组
	Vector<Tank> enemys = new Vector<Tank>();  		// enemy坦克向量，子弹击中坦克时遍历的向量
	Vector<Tank> enemy1s = new Vector<Tank>();  	// enemy1坦克向量，绘制坦克时遍历的向量
	Vector<Tank> enemy2s = new Vector<Tank>();  	// enemy2坦克向量
	Vector<Tank> enemy3s = new Vector<Tank>();  	// enemy3坦克向量
	Bullet enemyBullet = null;						// enemy坦克子弹
	Image enemy_bullet_image = null;				// enemy坦克子弹图片
	/* 墙 */
	Image wall_image = null;						 // 墙的图片
	Vector<MapItem> walls = new Vector<MapItem>();   // 墙的向量
	/* 钢 */
	Image steel_image = null;						 // 钢的图片
	Vector<MapItem> steels = new Vector<MapItem>();  // 钢的向量
	/* 草 */
	Image grass_image = null;						 // 草的图片
	Vector<MapItem> grasses = new Vector<MapItem>(); // 草的向量
	/* 水 */
	Image water_image = null;						 // 水的图片
	Vector<MapItem> waters = new Vector<MapItem>();  // 水的向量
	/* 家 */
	Image home_image = null;						 // 家的图片
	MapItem home = null;
	/* 炸弹 */
	Image bomb_image = null;						 // 炸弹的图片
	Vector<MapItem> bombs = new Vector<MapItem>();   // 炸弹的向量
	/* 计时器 */
	Image _timer_image = null;						 // 计时器的图片
	Vector<MapItem> _timers = new Vector<MapItem>(); // 计时器的向量
	/* 爆炸场面 */
	Image[] tank_blast_images = null;				// 坦克爆炸场面图片数组
	Image[] map_item_blast_images = null;			// 地图元素爆炸场面图片数组
	Vector<Blast> blasts = new Vector<Blast>();		// 爆炸场面向量
	/* 出生场面 */
	Image[] born_images = null; 					// 出生场面数组
	Vector<Born> borns = new Vector<Born>(); 		// 出生场面向量
	/* GameOver */
	Image game_over_image = null; 					// 游戏结束图片
	MapItem gameOver = null; 						// 游戏结束变量
	
	public GamePanel() {
		setPreferredSize(new Dimension(780, 780));
		init();
		Thread t = new Thread(this);
		t.start();
		initGameOver();
		initBlast();
		initBorn();
	}

	private void init(){
		initMap();
		initP1();
		initP2();
		initEnemy1();
		initEnemy2();
		initEnemy3();
		initBomb();
		init_timer();
	}

	private void initP2() {
		p2_images = new Image[4];
		try {
			p2_images[0] = ImageIO.read(new File("images/p2tankU.gif"));
			p2_images[1] = ImageIO.read(new File("images/p2tankD.gif"));
			p2_images[2] = ImageIO.read(new File("images/p2tankL.gif"));
			p2_images[3] = ImageIO.read(new File("images/p2tankR.gif"));
			p2_bullet_image = ImageIO.read(new File("images/tankmissile.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				for (int i=0; i<map.length; i++){
					for (int j=0; j<map[i].length; j++){
						if (map[i][j] == 7){    // 遍历地图，在地图坐标为7的地方创建p2
							P2TankFactory factory = new P2TankFactory();
							Tank p2 = factory.add(j*60, i*60);
							p2.setStage(stage);
							p2s.add(p2);
						}
					}
				}
			}
		};
		timer.schedule(task, 1000);
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map[i].length; j++){
				if (map[i][j] == 7){    // 遍历地图，在地图坐标为7的地方创建p2
					Born(j*60, i*60);
				}
			}
		}
	}

	private void init_timer() {
		try {
			_timer_image = ImageIO.read(new File("images/_timer.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				int x = (int)(Math.random()*721);
				int y = (int)(Math.random()*721);
				BombFactory factory = new BombFactory();
				MapItem _timer = factory.add(x, y);
				if (_timers.size() < 1) {
					_timers.add(_timer);
				}
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					
					@Override
					public void run() {
						for (int i=0; i<_timers.size(); i++) {
							MapItem _timer = _timers.get(i);
							if (_timer.isLive()) {
								_timer.setLive(false);
								_timers.remove(_timer);
							}
						}
					}
				};
				timer.schedule(task, 8000);
			}
		};
		Timer timer1 = new Timer(); 					 // 控制定时器循环的定时器
		timer1.schedule(task, 5000);
	}

	private void initBomb() {
		try {
			bomb_image = ImageIO.read(new File("images/bomb.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				int x = (int)(Math.random()*721);
				int y = (int)(Math.random()*721);
				BombFactory factory = new BombFactory();
				MapItem bomb = factory.add(x, y);
				if (bombs.size() < 1) {
					bombs.add(bomb); 
				}
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					
					@Override
					public void run() {
						for (int i=0; i<bombs.size(); i++) {
							MapItem bomb = bombs.get(i);
							if (bomb.isLive()) {
								bomb.setLive(false);
								bombs.remove(bomb);
							}
						}
					}
				};
				timer.schedule(task, 8000);
			}
		};
		Timer timer2 = new Timer(); 					 // 控制炸弹循环的定时器
		timer2.schedule(task, 20000);
	}

	private void initBorn() {
		born_images = new Image[8];
		try {
			for (int i=1; i<=8; i++){
				born_images[i-1] = ImageIO.read(new File("images/born" + i + ".gif"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void Born(int x, int y) {
		BornFactory factory = new BornFactory(); 	// 出生场面工厂
		Born born = (Born) factory.add(x, y); 		// 创建爆炸场面
		borns.add(born); 							// 加入到出生向量中
	}

	private void initEnemy3() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				for (int i=0; i<map.length; i++){
					for (int j=0; j<map[i].length; j++){
						if (map[i][j] == 8){    // 遍历地图，在地图坐标为8的地方创建enemy2
							Enemy3Factory factory = new Enemy3Factory();
							Tank enemy3 = factory.add(j*60, i*60);
							enemy3.setStage(stage);
							enemys.add(enemy3);
							enemy3s.add(enemy3);
						}
					}
				}
			}
		};
		timer.schedule(task, 21000);
		enemy3_images = new Image[16];
		try {
			enemy3_images[0] = ImageIO.read(new File("images/enemy3U1.gif"));
			enemy3_images[1] = ImageIO.read(new File("images/enemy3D1.gif"));
			enemy3_images[2] = ImageIO.read(new File("images/enemy3L1.gif"));
			enemy3_images[3] = ImageIO.read(new File("images/enemy3R1.gif"));
			
			enemy3_images[4] = ImageIO.read(new File("images/enemy3U2.gif"));
			enemy3_images[5] = ImageIO.read(new File("images/enemy3D2.gif"));
			enemy3_images[6] = ImageIO.read(new File("images/enemy3L2.gif"));
			enemy3_images[7] = ImageIO.read(new File("images/enemy3R2.gif"));
			
			enemy3_images[8] = ImageIO.read(new File("images/enemy3U3.gif"));
			enemy3_images[9] = ImageIO.read(new File("images/enemy3D3.gif"));
			enemy3_images[10] = ImageIO.read(new File("images/enemy3L3.gif"));
			enemy3_images[11] = ImageIO.read(new File("images/enemy3R3.gif"));
			
			enemy3_images[12] = ImageIO.read(new File("images/enemy3U4.gif"));
			enemy3_images[13] = ImageIO.read(new File("images/enemy3D4.gif"));
			enemy3_images[14] = ImageIO.read(new File("images/enemy3L4.gif"));
			enemy3_images[15] = ImageIO.read(new File("images/enemy3R4.gif"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		TimerTask task2 = new TimerTask() {
			
			@Override
			public void run() {
				for (int i=0; i<map.length; i++){
					for (int j=0; j<map[i].length; j++){
						if (map[i][j] == 8){    // 遍历地图，在地图坐标为6的地方创建p1
							Born(j*60, i*60);
						}
					}
				}
			}
		};
		timer.schedule(task2, 20000);
	}

	private void initGameOver() {
		GameOverFactory factory = new GameOverFactory();
		gameOver = factory.add(5*60, 11*60);
		
		try {
			game_over_image = ImageIO.read(new File("images/over.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initEnemy2() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				for (int i=0; i<map.length; i++){
					for (int j=0; j<map[i].length; j++){
						if (map[i][j] == 8){    // 遍历地图，在地图坐标为8的地方创建enemy2
							Enemy2Factory factory = new Enemy2Factory();
							Tank enemy2 = factory.add(j*60, i*60);
							enemy2.setStage(stage);
							enemys.add(enemy2);
							enemy2s.add(enemy2);
						}
					}
				}
			}
		};
		timer.schedule(task, 11000);
		enemy2_images = new Image[4];
		try {
			enemy2_images[0] = ImageIO.read(new File("images/enemy2U.gif"));
			enemy2_images[1] = ImageIO.read(new File("images/enemy2D.gif"));
			enemy2_images[2] = ImageIO.read(new File("images/enemy2L.gif"));
			enemy2_images[3] = ImageIO.read(new File("images/enemy2R.gif"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		TimerTask task2 = new TimerTask() {
			
			@Override
			public void run() {
				for (int i=0; i<map.length; i++){
					for (int j=0; j<map[i].length; j++){
						if (map[i][j] == 8){    // 遍历地图，在地图坐标为6的地方创建p1
							Born(j*60, i*60);
						}
					}
				}
			}
		};
		timer.schedule(task2, 10000);
	}

	private void initEnemy1() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				for (int i=0; i<map.length; i++){
					for (int j=0; j<map[i].length; j++){
						if (map[i][j] == 8){    // 遍历地图，在地图坐标为8的地方创建enemy1
							Enemy1Factory factory = new Enemy1Factory();
							Tank enemy1 = factory.add(j*60, i*60);
							enemy1.setStage(stage);
							enemys.add(enemy1);
							enemy1s.add(enemy1);
						}
					}
				}
			}
		};
		enemy1_images = new Image[4];
		try {
			enemy1_images[0] = ImageIO.read(new File("images/enemy1U.gif"));
			enemy1_images[1] = ImageIO.read(new File("images/enemy1D.gif"));
			enemy1_images[2] = ImageIO.read(new File("images/enemy1L.gif"));
			enemy1_images[3] = ImageIO.read(new File("images/enemy1R.gif"));
			enemy_bullet_image = ImageIO.read(new File("images/enemymissile.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		timer.schedule(task, 1000);
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map[i].length; j++){
				if (map[i][j] == 8){    // 遍历地图，在地图坐标为6的地方创建p1
					Born(j*60, i*60);
				}
			}
		}
	}

	private void initBlast() {
		tank_blast_images = new Image[8];
		map_item_blast_images = new Image[4];
		try {
			for (int i=1; i<=4; i++){
				map_item_blast_images[i-1] = ImageIO.read(new File("images/blast" + i + ".gif"));
			}
			for (int i=1; i<=8; i++){
				tank_blast_images[i-1] = ImageIO.read(new File("images/blast" + i + ".gif"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initMap() {
		switch (stage) {
		case 1: map = Map.map1; break;
		case 2: map = Map.map2; break;
		case 3: map = Map.map3; break;
		case 4: map = Map.map4; break;
		case 5: map = Map.map5; break;
		case 6: map = Map.map6; break;
		case 7: map = Map.map7; break;
		}
		try {
			wall_image = ImageIO.read(new File("images/walls.gif"));
			steel_image = ImageIO.read(new File("images/steels.gif"));
			home_image = ImageIO.read(new File("images/home.jpg"));
			grass_image = ImageIO.read(new File("images/grass.png"));
			water_image = ImageIO.read(new File("images/water.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map[i].length; j++){
				if (map[i][j] == 1){
					WallFactory wallFactory = new WallFactory();
					MapItem wall = wallFactory.add(j*60, i*60);
					walls.add(wall);
				}
				if (map[i][j] == 2){
					SteelFactory steelFactory = new SteelFactory();
					MapItem steel = steelFactory.add(j*60, i*60);
					steels.add(steel);
				}
				if (map[i][j] == 4){
					GrassFactory grassFactory = new GrassFactory();
					MapItem grass = grassFactory.add(j*60, i*60);
					grasses.add(grass);
				}
				if (map[i][j] == 3){
					WaterFactory waterFactory = new WaterFactory();
					MapItem water = waterFactory.add(j*60, i*60);
					waters.add(water);
				}
				if (map[i][j] == 5){
					HomeFactory homeFactory = new HomeFactory();
					home = homeFactory.add(j*60, i*60);
				}
			}
		}
		SoundPlayer play = new SoundPlayer("sound/start.wav");
		play.start();
	}

	private void initP1() {
		p1_images = new Image[4];
		try {
			p1_images[0] = ImageIO.read(new File("images/p1tankU.gif"));
			p1_images[1] = ImageIO.read(new File("images/p1tankD.gif"));
			p1_images[2] = ImageIO.read(new File("images/p1tankL.gif"));
			p1_images[3] = ImageIO.read(new File("images/p1tankR.gif"));
			p1_bullet_image = ImageIO.read(new File("images/tankmissile.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				for (int i=0; i<map.length; i++){
					for (int j=0; j<map[i].length; j++){
						if (map[i][j] == 6){    // 遍历地图，在地图坐标为6的地方创建p1
							P1TankFactory factory = new P1TankFactory();
							Tank p1 = factory.add(j*60, i*60);
							p1.setStage(stage);
							p1s.add(p1);
						}
					}
				}
			}
		};
		timer.schedule(task, 1000);
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map[i].length; j++){
				if (map[i][j] == 6){    // 遍历地图，在地图坐标为6的地方创建p1
					Born(j*60, i*60);
				}
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, screenWidth, screenHeight);  // 画板区域
		
		drawP1(g);
		drawP2(g);
		drawEnemy1(g);
		drawEnemy2(g);
		drawEnemy3(g);
		drawMap(g);
		drawBorn(g);
		drawHome(g);
		drawBlast(g);
		drawBomb(g);
		draw_timer(g);
		drawGameOver(g);
	}

	private void drawP2(Graphics g) {
		for (int i=0; i<p2s.size(); i++){		// 遍历p2向量
			Tank p2 = p2s.get(i);				// 取出p2
			if (p2.isLive()){					// 如果p2存活，画出它
				switch (p2.getDirect()){
				case 0: g.drawImage(p2_images[0], p2.getX(), p2.getY(), p2.getWidth(), p2.getHeight(), this); break;
				case 1: g.drawImage(p2_images[1], p2.getX(), p2.getY(), p2.getWidth(), p2.getHeight(), this); break;
				case 2: g.drawImage(p2_images[2], p2.getX(), p2.getY(), p2.getWidth(), p2.getHeight(), this); break;
				case 3: g.drawImage(p2_images[3], p2.getX(), p2.getY(), p2.getWidth(), p2.getHeight(), this); break;
				default: g.drawImage(p2_images[0], p2.getX(), p2.getY(), p2.getWidth(), p2.getHeight(), this); break;
				}
				// 画p2子弹
				for (int j=0; j<p2bs.size(); j++){	// 遍历弹夹
					Bullet b = p2bs.get(j);			// 取出每一刻子弹
					if (b.isLive()){				// 如果子弹存活，画出它
						g.drawImage(p2_bullet_image, b.getX(), b.getY(), b.getWidth(), b.getHeight(), this);
					}
				}
			}
		}
	}

	private void drawMap(Graphics g) {
		if (walls != null){
			for (int i=0; i<walls.size(); i++){
				MapItem w = walls.get(i);
				if (w.isLive()){
					g.drawImage(wall_image, w.getX(), w.getY(), w.getWidth(), w.getHeight(), this);
				}
			}
		}
		if (steels != null){
			for (int i=0; i<steels.size(); i++){
				MapItem s = steels.get(i);
				g.drawImage(steel_image, s.getX(), s.getY(), s.getWidth(), s.getHeight(), this);
			}
		}
		if (grasses != null){
			for (int i=0; i<grasses.size(); i++){
				MapItem grass = grasses.get(i);
				g.drawImage(grass_image, grass.getX(), grass.getY(), grass.getWidth(), grass.getHeight(), this);
			}
		}
		if (waters != null){
			for (int i=0; i<waters.size(); i++){
				MapItem w = waters.get(i);
				g.drawImage(water_image, w.getX(), w.getY(), w.getWidth(), w.getHeight(), this);
			}
		}
	}

	private void draw_timer(Graphics g) {
		for (int i=0; i<_timers.size(); i++) {
			MapItem _timer = _timers.get(i);
			if (_timer.isLive()) {
				g.drawImage(_timer_image, _timer.getX(), _timer.getY(), _timer.getWidth(), _timer.getHeight(), this);
			}
		}
	}

	private void drawBomb(Graphics g) {
		for (int i=0; i<bombs.size(); i++){
			MapItem bomb = bombs.get(i);
			if (bomb.isLive()){
				g.drawImage(bomb_image, bomb.getX(), bomb.getY(), bomb.getWidth(), bomb.getHeight(), this);
			}
		}
	}

	private void drawBorn(Graphics g) {
		for (int i=0; i<borns.size(); i++) { 		// 遍历出生向量
			Born born = borns.get(i); 				// 取得一个出生场面
			if (born.isLive) {
				switch (born.getLife()){
				case 8: g.drawImage(born_images[0], born.getX(), born.getY(), born.getWidth(), born.getHeight(), this); break;
				case 7: g.drawImage(born_images[1], born.getX(), born.getY(), born.getWidth(), born.getHeight(), this); break;
				case 6: g.drawImage(born_images[2], born.getX(), born.getY(), born.getWidth(), born.getHeight(), this); break;
				case 5: g.drawImage(born_images[3], born.getX(), born.getY(), born.getWidth(), born.getHeight(), this); break;
				case 4: g.drawImage(born_images[4], born.getX(), born.getY(), born.getWidth(), born.getHeight(), this); break;
				case 3: g.drawImage(born_images[5], born.getX(), born.getY(), born.getWidth(), born.getHeight(), this); break;
				case 2: g.drawImage(born_images[6], born.getX(), born.getY(), born.getWidth(), born.getHeight(), this); break;
				case 1: g.drawImage(born_images[7], born.getX(), born.getY(), born.getWidth(), born.getHeight(), this); break;
				}
			}
		}
	}

	private void drawEnemy3(Graphics g) {
		for (int i=0; i<enemy3s.size(); i++) { 		// 遍历第三种坦克向量
			Tank enemy3 = enemy3s.get(i); 			// 取出一辆第三种坦克
			if (enemy3.isLive()) { 					// 如果坦克存活，按剩余盔甲值画出它
				int armor = ((Enemy)enemy3).getArmor();
				if (armor == 4) {
					switch (enemy3.getDirect()){	    // 根据方向绘制图片
						case 0: g.drawImage(enemy3_images[0], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 1: g.drawImage(enemy3_images[1], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 2: g.drawImage(enemy3_images[2], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 3: g.drawImage(enemy3_images[3], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
					}
				}
				if (armor == 3) {
					switch (enemy3.getDirect()){	    // 根据方向绘制图片
						case 0: g.drawImage(enemy3_images[4], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 1: g.drawImage(enemy3_images[5], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 2: g.drawImage(enemy3_images[6], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 3: g.drawImage(enemy3_images[7], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
					}
				}
				if (armor == 2) {
					switch (enemy3.getDirect()){	    // 根据方向绘制图片
						case 0: g.drawImage(enemy3_images[8], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 1: g.drawImage(enemy3_images[9], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 2: g.drawImage(enemy3_images[10], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 3: g.drawImage(enemy3_images[11], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
					}
				}
				if (armor == 1) {
					switch (enemy3.getDirect()){	    // 根据方向绘制图片
						case 0: g.drawImage(enemy3_images[12], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 1: g.drawImage(enemy3_images[13], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 2: g.drawImage(enemy3_images[14], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
						case 3: g.drawImage(enemy3_images[15], enemy3.getX(), enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), this); break;
					}
				}
				Vector<Bullet> enemy3bs = new Vector<Bullet>(); // enemy3坦克子弹向量
				enemy3bs = ((Enemy) enemy3).getenemybs();
				for (int j=0; j<enemy3bs.size(); j++){
					Bullet b = enemy3bs.get(j);
					if (b.isLive()){				 			// 子弹如果存活，画出它
						g.drawImage(enemy_bullet_image, b.getX(), b.getY(), b.getWidth(), b.getHeight(), this);
					}
				}
			}
		}
	}

	private void drawGameOver(Graphics g) {
		if (gameOver.isLive()){
			g.drawImage(game_over_image, gameOver.getX(), gameOver.getY(), gameOver.getWidth(), gameOver.getHeight(), this);
		}
	}

	private void drawEnemy2(Graphics g) {
		for (int i=0; i<enemy2s.size(); i++) {		// 遍历enemy2向量
			Tank enemy2 = enemy2s.get(i); 			// 取得一辆enemy2
			if (enemy2.isLive()){ 					// 如果enemy2存活，画出它
				switch (enemy2.getDirect()){	    // 根据方向绘制图片
				case 0: g.drawImage(enemy2_images[0], enemy2.getX(), enemy2.getY(), enemy2.getWidth(), enemy2.getHeight(), this); break;
				case 1: g.drawImage(enemy2_images[1], enemy2.getX(), enemy2.getY(), enemy2.getWidth(), enemy2.getHeight(), this); break;
				case 2: g.drawImage(enemy2_images[2], enemy2.getX(), enemy2.getY(), enemy2.getWidth(), enemy2.getHeight(), this); break;
				case 3: g.drawImage(enemy2_images[3], enemy2.getX(), enemy2.getY(), enemy2.getWidth(), enemy2.getHeight(), this); break;
				}
				Vector<Bullet> enemy2bs = new Vector<Bullet>(); // enemy2坦克子弹向量
				enemy2bs = ((Enemy) enemy2).getenemybs();
				for (int j=0; j<enemy2bs.size(); j++){
					Bullet b = enemy2bs.get(j);
					if (b.isLive()){				 			// 子弹如果存活，画出它
						g.drawImage(enemy_bullet_image, b.getX(), b.getY(), b.getWidth(), b.getHeight(), this);
					}
				}
			}
		}
	}

	private void drawEnemy1(Graphics g) {
		for (int i=0; i<enemy1s.size(); i++){
			Tank enemy1 = enemy1s.get(i);
			if (enemy1.isLive()){					// 敌方坦克是否存在
				switch (enemy1.getDirect()){	    // 根据方向绘制图片
				case 0: g.drawImage(enemy1_images[0], enemy1.getX(), enemy1.getY(), enemy1.getWidth(), enemy1.getHeight(), this); break;
				case 1: g.drawImage(enemy1_images[1], enemy1.getX(), enemy1.getY(), enemy1.getWidth(), enemy1.getHeight(), this); break;
				case 2: g.drawImage(enemy1_images[2], enemy1.getX(), enemy1.getY(), enemy1.getWidth(), enemy1.getHeight(), this); break;
				case 3: g.drawImage(enemy1_images[3], enemy1.getX(), enemy1.getY(), enemy1.getWidth(), enemy1.getHeight(), this); break;
				}
				Vector<Bullet> enemy1bs = new Vector<Bullet>(); // enemy1坦克子弹向量
				enemy1bs = ((Enemy) enemy1).getenemybs();
				for (int j=0; j<enemy1bs.size(); j++){
					Bullet b = enemy1bs.get(j);
					if (b.isLive()){				 // 子弹如果存活，画出它
						g.drawImage(enemy_bullet_image, b.getX(), b.getY(), b.getWidth(), b.getHeight(), this);
					}
				}
			}
		}
	}

	private void drawBlast(Graphics g) {
		for (int i=0; i<blasts.size(); i++){	// 遍历爆炸场面向量
			Blast blast = blasts.get(i);		// 得到一个爆炸场面
			if (blast.isLive){					// 如果还活着，就画出来
				if (blast instanceof MapItemBlast){	// 画出击中了强、钢、鹰、边界的场面
					switch (blast.getLife()){
					case 8: g.drawImage(map_item_blast_images[0], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					case 6: g.drawImage(map_item_blast_images[1], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					case 4: g.drawImage(map_item_blast_images[2], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					case 2: g.drawImage(map_item_blast_images[3], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					}
				}
				if (blast instanceof TankBlast){	// 画出击中了坦克的场面
					switch (blast.getLife()){
					case 8: g.drawImage(tank_blast_images[0], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					case 7: g.drawImage(tank_blast_images[1], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					case 6: g.drawImage(tank_blast_images[2], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					case 5: g.drawImage(tank_blast_images[3], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					case 4: g.drawImage(tank_blast_images[4], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					case 3: g.drawImage(tank_blast_images[5], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					case 2: g.drawImage(tank_blast_images[6], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					case 1: g.drawImage(tank_blast_images[7], blast.getX(), blast.getY(), blast.getWidth(), blast.getHeight(), this); break;
					}
				}
			}
		}
	}

	private void drawHome(Graphics g) {
		if (home.isLive()){
			g.drawImage(home_image, home.getX(), home.getY(), home.getWidth(), home.getHeight(), this);
		}
	}

	private void drawP1(Graphics g) {
		for (int i=0; i<p1s.size(); i++){		// 遍历p1向量
			Tank p1 = p1s.get(i);				// 取出p1
			if (p1.isLive()){					// 如果p1存活，画出它
				switch (p1.getDirect()){
				case 0: g.drawImage(p1_images[0], p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight(), this); break;
				case 1: g.drawImage(p1_images[1], p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight(), this); break;
				case 2: g.drawImage(p1_images[2], p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight(), this); break;
				case 3: g.drawImage(p1_images[3], p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight(), this); break;
				default: g.drawImage(p1_images[0], p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight(), this); break;
				}
				// 画p1子弹
				for (int j=0; j<p1bs.size(); j++){	// 遍历弹夹
					Bullet b = p1bs.get(j);			// 取出每一刻子弹
					if (b.isLive()){				// 如果子弹存活，画出它
						g.drawImage(p1_bullet_image, b.getX(), b.getY(), b.getWidth(), b.getHeight(), this);
					}
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!gameOver.isLive()){
			for (int i=0; i<p1s.size(); i++) { 			// 遍历p1向量
				Tank p1 = p1s.get(i); 					// 取出一个p1
				if (p1.isLive()) { 						// 如果p1存活，控制它移动
					if (e.getKeyCode() == KeyEvent.VK_W){
						p1.setDirect(0);
						p1_ismoving = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_S){
						p1.setDirect(1);
						p1_ismoving = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_A){
						p1.setDirect(2);
						p1_ismoving = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_D){
						p1.setDirect(3);
						p1_ismoving = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_J){
						if (p1bs.size() < 2){
							Bullet p1Bullet = ((P1Tank) p1).fire();
							p1bs.add(p1Bullet);
							SoundPlayer play = new SoundPlayer("sound/fire.wav");
							play.start();
						}
					}
				}
			}
			for (int i=0; i<p2s.size(); i++) { 			// 遍历p2向量
				Tank p2 = p2s.get(i); 					// 取出一个p2
				if (p2.isLive()) { 						// 如果p2存活，控制它移动
					if (e.getKeyCode() == KeyEvent.VK_UP){
						p2.setDirect(0);
						p2_ismoving = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN){
						p2.setDirect(1);
						p2_ismoving = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_LEFT){
						p2.setDirect(2);
						p2_ismoving = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_RIGHT){
						p2.setDirect(3);
						p2_ismoving = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_NUMPAD1){
						if (p2bs.size() < 2){
							Bullet p2Bullet = ((P2Tank) p2).fire();
							p2bs.add(p2Bullet);
							SoundPlayer play = new SoundPlayer("sound/fire.wav");
							play.start();
						}
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver.isLive()){
			for (int i=0; i<p1s.size(); i++) { 			// 遍历p1向量
				Tank p1 = p1s.get(i); 					// 取出一个p1
				if (p1.isLive()) { 						// 如果p1存活，控制它移动
					if (e.getKeyCode() == KeyEvent.VK_W){
						p1_ismoving = false;
					}
					if (e.getKeyCode() == KeyEvent.VK_S){
						p1_ismoving = false;
					}
					if (e.getKeyCode() == KeyEvent.VK_A){
						p1_ismoving = false;
					}
					if (e.getKeyCode() == KeyEvent.VK_D){
						p1_ismoving = false;
					}
				}
			}
			for (int i=0; i<p2s.size(); i++) { 			// 遍历p2向量
				Tank p2 = p2s.get(i); 					// 取出一个p2
				if (p2.isLive()) { 						// 如果p2存活，控制它移动
					if (e.getKeyCode() == KeyEvent.VK_UP){
						p2_ismoving = false;
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN){
						p2_ismoving = false;
					}
					if (e.getKeyCode() == KeyEvent.VK_LEFT){
						p2_ismoving = false;
					}
					if (e.getKeyCode() == KeyEvent.VK_RIGHT){
						p2_ismoving = false;
					}
				}
			}
		}
	}

	@Override
	public void run() {
		while (true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
			hitWall();
			hitSteel();
			hitBoundary();
			removeBlast();
			hitTank();
			_Timer();
			Bomb();
			nextStage();
			p1_move();
			p2_move();
			gameover();
		}
	}

	private void gameover() {
		p1_p2_over();
		hitHome();
	}

	private void p1_p2_over() {
		if (p1_over && p2_over){
			((GameOver) gameOver).over(); 	// 游戏结束 
		}
	}

	private void p2_move() {
		if (p2_ismoving){
			for (int i=0; i<p2s.size(); i++){ 
				Tank p2 = p2s.get(i);
				if (p2.isLive()){
					switch (p2.getDirect()){
					case 0:
						p2.moveUp();
						repaint();
						if (p2.isColliding(p2.getDirect())){
							p2.moveDown();
							repaint();
						}
						break;
					case 1:
						p2.moveDown();
						repaint();
						if (p2.isColliding(p2.getDirect())){
							p2.moveUp();
							repaint();
						}
						break;
					case 2:
						p2.moveLeft();
						repaint();
						if (p2.isColliding(p2.getDirect())){
							p2.moveRight();
							repaint();
						}
						break;
					case 3:
						p2.moveRight();
						repaint();
						if (p2.isColliding(p2.getDirect())){
							p2.moveLeft();
							repaint();
						}
						break;
					}
				}
			}
		}
	}

	private void p1_move() {
		if (p1_ismoving){
			for (int i=0; i<p1s.size(); i++){ 
				Tank p1 = p1s.get(i);
				if (p1.isLive()){
					switch (p1.getDirect()){
					case 0:
						p1.moveUp();
						repaint();
						if (p1.isColliding(p1.getDirect())){
							p1.moveDown();
							repaint();
						}
						break;
					case 1:
						p1.moveDown();
						repaint();
						if (p1.isColliding(p1.getDirect())){
							p1.moveUp();
							repaint();
						}
						break;
					case 2:
						p1.moveLeft();
						repaint();
						if (p1.isColliding(p1.getDirect())){
							p1.moveRight();
							repaint();
						}
						break;
					case 3:
						p1.moveRight();
						repaint();
						if (p1.isColliding(p1.getDirect())){
							p1.moveLeft();
							repaint();
						}
						break;
					}
				}
			}
		}
	}

	private void nextStage() {
		if (enemyNum<1 && stage<7){
			enemyNum = 9;
			stage++;
			TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					Introduce.stage++;
					p1s.clear();
					p2s.clear();
					walls.clear();
					steels.clear();
					grasses.clear();
					waters.clear();
					init();
				}
			};
			Timer timer = new Timer();
			timer.schedule(task, 1000);
		}
	}

	private void Bomb() {
		for (int i=0; i<p1s.size(); i++) { 		// 遍历P1坦克
			Tank p1 = p1s.get(i); 				// 取出p1
			p1.setRect(); 						// 设置p1的矩形
			int[] rect1 = p1.getRect(); 		// 取得p1的矩形
			for (int j=0; j<bombs.size(); j++) {
				MapItem bomb = bombs.get(j);
				if (bomb.isLive()) { 							// 如果bomb存活，判 断p1是否与其相撞
					bomb.setRect(); 							// 设置bomb的矩形
					int[] rect2 = bomb.getRect();   			// 取得bomb的矩形
					if (RectCollide.isCollding(rect1, rect2)) { // 如果相撞，bomb死亡，播放声音
						SoundPlayer play = new SoundPlayer("sound/add.wav");
						play.start();
						bomb.setLive(false); 
						bombs.remove(bomb);
						for (int k=0; k<enemys.size(); k++){ 		// 遍历坦克向量
							Tank e = enemys.get(k); 				// 取出坦克
							e.setLive(false); 						// 坦克死亡
							enemyNum--; 							// 坦克数量减一
							TankBlastFactory t_factory = new TankBlastFactory();
							Blast tankBlast = t_factory.add(e.getX()+20, e.getY()+20);
							blasts.add(tankBlast); 					// 产生爆炸
						}
						enemys.clear(); 							// 清空坦克向量
					}
				}
			}
		}
		for (int i=0; i<p2s.size(); i++) { 		// 遍历p2坦克
			Tank p2 = p2s.get(i); 				// 取出p2
			p2.setRect(); 						// 设置p2的矩形
			int[] rect1 = p2.getRect(); 		// 取得p2的矩形
			for (int j=0; j<bombs.size(); j++) {
				MapItem bomb = bombs.get(j);
				if (bomb.isLive()) { 							// 如果bomb存活，判 断p2是否与其相撞
					bomb.setRect(); 							// 设置bomb的矩形
					int[] rect2 = bomb.getRect();   			// 取得bomb的矩形
					if (RectCollide.isCollding(rect1, rect2)) { // 如果相撞，bomb死亡，播放声音
						SoundPlayer play = new SoundPlayer("sound/add.wav");
						play.start();
						bomb.setLive(false); 
						bombs.remove(bomb);
						for (int k=0; k<enemys.size(); k++){ 		// 遍历坦克向量
							Tank e = enemys.get(k); 				// 取出坦克
							e.setLive(false); 						// 坦克死亡
							enemyNum--; 							// 坦克数量减一
							TankBlastFactory t_factory = new TankBlastFactory();
							Blast tankBlast = t_factory.add(e.getX()+20, e.getY()+20);
							blasts.add(tankBlast); 					// 产生爆炸
						}
						enemys.clear(); 							// 清空坦克向量
					}
				}
			}
		}
	}

	private void _Timer() {
		for (int i=0; i<p1s.size(); i++) { 		// 遍历P1坦克
			Tank p1 = p1s.get(i); 				// 取出p1
			p1.setRect(); 						// 设置p1的矩形
			int[] rect1 = p1.getRect(); 		// 取得p1的矩形
			for (int j=0; j<_timers.size(); j++) {
				MapItem _timer = _timers.get(j);
				if (_timer.isLive()) {
					_timer.setRect(); 				// 设置计时器的矩形
					int[] rect2 = _timer.getRect(); // 取得计时器的矩形
					if (RectCollide.isCollding(rect1, rect2)) { 	// 如果相撞，计时器死亡
						_timer.setLive(false); 	
						_timers.remove(_timer);
						for (int k=0; k<enemys.size(); k++){ 		// 遍历坦克向量
							Tank e = enemys.get(k); 				// 取出坦克
							((Enemy)e).setMoving(false); 			// 使坦克停止移动
							TimerTask task = new TimerTask() {
								
								@Override
								public void run() {
									((Enemy)e).setMoving(true);
								}
							};
							Timer timer = new Timer();
							timer.schedule(task, 5000); 			// 5秒钟之后坦克移动
						}
					}
				}
			}
		}
		for (int i=0; i<p2s.size(); i++) { 		// 遍历p2坦克
			Tank p2 = p2s.get(i); 				// 取出p2
			p2.setRect(); 						// 设置p2的矩形
			int[] rect1 = p2.getRect(); 		// 取得p2的矩形
			for (int j=0; j<_timers.size(); j++) {
				MapItem _timer = _timers.get(j);
				if (_timer.isLive()) {
					_timer.setRect(); 				// 设置计时器的矩形
					int[] rect2 = _timer.getRect(); // 取得计时器的矩形
					if (RectCollide.isCollding(rect1, rect2)) { 	// 如果相撞，计时器死亡
						_timer.setLive(false); 	
						_timers.remove(_timer);
						for (int k=0; k<enemys.size(); k++){ 		// 遍历坦克向量
							Tank e = enemys.get(k); 				// 取出坦克
							((Enemy)e).setMoving(false); 			// 使坦克停止移动
							TimerTask task = new TimerTask() {
								
								@Override
								public void run() {
									((Enemy)e).setMoving(true);
								}
							};
							Timer timer = new Timer();
							timer.schedule(task, 5000); 			// 5秒钟之后坦克移动
						}
					}
				}
			}
		}
	}

	private void hitHome() {
		for (int i=0; i<p1bs.size(); i++) {		// 遍历P1坦克的弹夹
			Bullet b = p1bs.get(i);				// 获取一颗子弹
			b.setRect(); 						// 设置子弹的矩形
			int[] rect1 = b.getRect(); 			// 获得子弹的矩形
			home.setRect(); 					// 设置老巢的矩形
			int[] rect2 = home.getRect();  		// 获得老巢的矩形
			if (home.isLive()){
				if (RectCollide.isCollding(rect1, rect2)){	// 如果子弹碰到了老巢
					SoundPlayer play = new SoundPlayer("sound/blast.wav");
					play.start();
					home.setLive(false); 			// 老巢死亡
					b.setLive(false); 				// 子弹死亡
					p1bs.remove(b); 				// 从子弹向量里移除
					b.setTank(true); 				// 相当于坦克爆炸
					Blast blast = b.blast();  		// 出现爆炸场面
					blasts.add(blast); 				// 将爆炸场面加入向量中
					((GameOver) gameOver).over(); 	// 游戏结束 
				}
			}
		}
		for (int i=0; i<p2bs.size(); i++) {		// 遍历p2坦克的弹夹
			Bullet b = p2bs.get(i);				// 获取一颗子弹
			b.setRect(); 						// 设置子弹的矩形
			int[] rect1 = b.getRect(); 			// 获得子弹的矩形
			home.setRect(); 					// 设置老巢的矩形
			int[] rect2 = home.getRect();  		// 获得老巢的矩形
			if (home.isLive()){
				if (RectCollide.isCollding(rect1, rect2)){	// 如果子弹碰到了老巢
					SoundPlayer play = new SoundPlayer("sound/blast.wav");
					play.start();
					home.setLive(false); 			// 老巢死亡
					b.setLive(false); 				// 子弹死亡
					p2bs.remove(b); 				// 从子弹向量里移除
					b.setTank(true); 				// 相当于坦克爆炸
					Blast blast = b.blast();  		// 出现爆炸场面
					blasts.add(blast); 				// 将爆炸场面加入向量中
					((GameOver) gameOver).over(); 	// 游戏结束 
				}
			}
		}
		for (int i=0; i<enemys.size(); i++) { 	 	// 遍历敌方坦克向量
			Tank e = enemys.get(i);  				// 取得一辆敌方坦克
			Vector<Bullet> bs = ((Enemy)e).getenemybs();  // 取得这个坦克的弹夹
			for (int j=0; j<bs.size(); j++) { 		// 遍历这个弹夹
				Bullet b = bs.get(j); 				// 取一颗子弹
				b.setRect(); 						// 设置子弹的矩形
				int[] rect1 = b.getRect(); 			// 获得子弹的矩形
				home.setRect(); 					// 设置老巢的矩形
				int[] rect2 = home.getRect();  		// 获得老巢的矩形
				if (home.isLive()){
					if (RectCollide.isCollding(rect1, rect2)) {  // 如果子弹击中了老巢
						SoundPlayer play = new SoundPlayer("sound/blast.wav");
						play.start();
						home.setLive(false); 			// 老巢死亡
						b.setLive(false); 				// 子弹死亡
						bs.remove(b); 					// 从子弹向量里移除
						b.setTank(true); 				// 相当于坦克爆炸
						Blast blast = b.blast();  		// 出现爆炸场面
						blasts.add(blast); 				// 将爆炸场面加入向量中
						((GameOver) gameOver).over(); 	// 游戏结束 
					}
				}
			}
 		}
	}

	private void removeBlast() {
		for (int i=0; i<blasts.size(); i++){	// 遍历爆炸场面的向量
			Blast blast = blasts.get(i);		// 取出一个爆炸场面
			if (!blast.isLive){					// 如果它已经死了
				blasts.remove(blast);			// 从向量中移除它
			}
		}
	}

	private void hitSteel() {
		for (int i=0; i<steels.size(); i++){  // 遍历钢
			MapItem w = steels.get(i);
			w.setRect();					 // 设置钢的矩形
			int[] rect2 = w.getRect();		 // 得到钢的矩形
			if (p1bs != null){
				for (int j=0; j<p1bs.size(); j++){	// 遍历p1弹夹
					Bullet b = p1bs.get(j);			// 得到一颗子弹
					b.setRect();					// 设置子弹的矩形
					int[] rect1 = b.getRect();		// 得到子弹的矩形
					if (RectCollide.isCollding(rect1, rect2)){	// 如果子弹击中了钢
						b.setLive(false);						// 子弹死亡
						p1bs.remove(b);							// 从弹夹中移除子弹
						Blast blast = b.blast(); 				// 产生爆炸效果
						blasts.add(blast);						// 把爆炸场面加入到对应的向量中
					}
				}
			}
			if (p2bs != null){
				for (int j=0; j<p2bs.size(); j++){	// 遍历p2弹夹
					Bullet b = p2bs.get(j);			// 得到一颗子弹
					b.setRect();					// 设置子弹的矩形
					int[] rect1 = b.getRect();		// 得到子弹的矩形
					if (RectCollide.isCollding(rect1, rect2)){	// 如果子弹击中了钢
						b.setLive(false);						// 子弹死亡
						p2bs.remove(b);							// 从弹夹中移除子弹
						Blast blast = b.blast(); 				// 产生爆炸效果
						blasts.add(blast);						// 把爆炸场面加入到对应的向量中
					}
				}
			}
			for (int j=0; j<enemys.size(); j++){	// 遍历敌人坦克向量
				Tank e = enemys.get(j);				// 获取其中一个坦克
				Vector<Bullet> enemybs = new Vector<Bullet>(); // enemy坦克子弹向量
				enemybs = ((Enemy) e).getenemybs(); // 获取这个坦克的弹夹
				if (enemybs != null){
					for (int k=0; k<enemybs.size(); k++){	// 遍历这个坦克的弹夹
						Bullet b = enemybs.get(k);			// 得到一颗子弹
						b.setRect();						// 设置子弹的矩形
						int[] rect1 = b.getRect();			// 得到子弹的矩形
						if (RectCollide.isCollding(rect1, rect2)){	// 如果子弹击中了钢
							b.setLive(false);						// 子弹死亡
							enemybs.remove(b);						// 从弹夹中移除子弹
							Blast blast = b.blast(); 				// 产生爆炸效果
							blasts.add(blast);						// 把爆炸场面加入到对应的向量中
						}
					}
				}
			}
		}
	}

	private void hitWall() {
		for (int i=0; i<walls.size(); i++){  // 遍历墙
			MapItem w = walls.get(i);
			w.setRect();					 // 设置墙的矩形
			int[] rect2 = w.getRect();		 // 得到墙的矩形
			if (p1bs != null){
				for (int j=0; j<p1bs.size(); j++){	// 遍历p1弹夹
					Bullet b = p1bs.get(j);			// 得到一颗子弹
					b.setRect();					// 设置子弹的矩形
					int[] rect1 = b.getRect();		// 得到子弹的矩形
					if (RectCollide.isCollding(rect1, rect2)){	// 如果子弹击中了墙
						b.setLive(false);						// 子弹死亡
						p1bs.remove(b);							// 从弹夹中移除子弹
						Blast blast = b.blast(); 				// 产生爆炸效果
						blasts.add(blast);						// 把爆炸场面加入到对应的向量中
						w.setLive(false);						// 墙死亡
						walls.remove(w); 						// 从墙的向量中移除这个墙
						int[] pos = w.getPosition(); 			// 获取墙在地图上的坐标
						map[pos[0]][pos[1]] = 0;			// 把坐标改为0
					}
				}
			}
			if (p2bs != null){
				for (int j=0; j<p2bs.size(); j++){	// 遍历p2弹夹
					Bullet b = p2bs.get(j);			// 得到一颗子弹
					b.setRect();					// 设置子弹的矩形
					int[] rect1 = b.getRect();		// 得到子弹的矩形
					if (RectCollide.isCollding(rect1, rect2)){	// 如果子弹击中了墙
						b.setLive(false);						// 子弹死亡
						p2bs.remove(b);							// 从弹夹中移除子弹
						Blast blast = b.blast(); 				// 产生爆炸效果
						blasts.add(blast);						// 把爆炸场面加入到对应的向量中
						w.setLive(false);						// 墙死亡
						walls.remove(w); 						// 从墙的向量中移除这个墙
						int[] pos = w.getPosition(); 			// 获取墙在地图上的坐标
						map[pos[0]][pos[1]] = 0;			// 把坐标改为0
					}
				}
			}
			for (int j=0; j<enemys.size(); j++){	// 遍历敌人坦克向量
				Tank e = enemys.get(j);				// 获取其中一个坦克
				Vector<Bullet> enemybs = new Vector<Bullet>(); // enemy坦克子弹向量
				enemybs = ((Enemy) e).getenemybs(); // 获取这个坦克的弹夹
				if (enemybs != null){
					for (int k=0; k<enemybs.size(); k++){	// 遍历这个坦克的弹夹
						Bullet b = enemybs.get(k);			// 得到一颗子弹
						b.setRect();						// 设置子弹的矩形
						int[] rect1 = b.getRect();			// 得到子弹的矩形
						if (RectCollide.isCollding(rect1, rect2)){	// 如果子弹击中了墙
							b.setLive(false);						// 子弹死亡
							enemybs.remove(b);						// 从弹夹中移除子弹
							Blast blast = b.blast(); 				// 产生爆炸效果
							blasts.add(blast);						// 把爆炸场面加入到对应的向量中
							w.setLive(false);						// 墙死亡
							walls.remove(w); 						// 从墙的向量中移除这个墙
							int[] pos = w.getPosition(); 			// 获取墙在地图上的坐标
							map[pos[0]][pos[1]] = 0;			// 把坐标改为0
						}
					}
				}
			}
		}
	}
	
	private void hitBoundary(){
		if (p1bs != null){
			for (int j=0; j<p1bs.size(); j++){	// 遍历p1弹夹
				Bullet b = p1bs.get(j);			// 得到一颗子弹
				if (b.getX()<=0 || b.getY()<=0 || (b.getX()+b.getWidth())>=780 || (b.getY()+b.getHeight())>=780) {
					// 如果子弹触及了边界
					SoundPlayer play = new SoundPlayer("sound/hit.wav");
					play.start();
					b.setLive(false);						// 子弹死亡
					p1bs.remove(b);							// 从弹夹中移除子弹
					Blast blast = b.blast(); 				// 产生爆炸效果
					blasts.add(blast);						// 把爆炸场面加入到对应的向量中
				}
			}
		}
		if (p2bs != null){
			for (int j=0; j<p2bs.size(); j++){	// 遍历p2弹夹
				Bullet b = p2bs.get(j);			// 得到一颗子弹
				if (b.getX()<=0 || b.getY()<=0 || (b.getX()+b.getWidth())>=780 || (b.getY()+b.getHeight())>=780) {
					// 如果子弹触及了边界
					SoundPlayer play = new SoundPlayer("sound/hit.wav");
					play.start();
					b.setLive(false);						// 子弹死亡
					p2bs.remove(b);							// 从弹夹中移除子弹
					Blast blast = b.blast(); 				// 产生爆炸效果
					blasts.add(blast);						// 把爆炸场面加入到对应的向量中
				}
			}
		}
		for (int i=0; i<enemys.size(); i++){	// 遍历敌人坦克向量
			Tank e = enemys.get(i);				// 获取其中一个坦克
			Vector<Bullet> bs = new Vector<Bullet>();
			bs = ((Enemy)e).getenemybs();		// 获取这个敌方坦克的弹夹
			for (int j=0; j<bs.size(); j++){	// 遍历弹夹
				Bullet b = bs.get(j);			// 取出一颗子弹
				if (b.getX()<=0 || b.getY()<=0 || (b.getX()+b.getWidth())>=780 || (b.getY()+b.getHeight())>=780) {
					// 如果子弹触及了边界
					b.setLive(false);						// 子弹死亡
					bs.remove(b);							// 从弹夹中移除子弹
					Blast blast = b.blast(); 				// 产生爆炸效果
					blasts.add(blast);						// 把爆炸场面加入到对应的向量中
				}
			}
		}
	}
	private void hitTank() {
		for (int i=0; i<p1bs.size(); i++) {		// 遍历P1坦克的弹夹
			Bullet b = p1bs.get(i);				// 获取一颗子弹
			b.setRect(); 						// 设置子弹的矩形
			int[] rect1 = b.getRect(); 			// 取得子弹的矩形
			for (int j=0; j<enemys.size(); j++) {	// 遍历敌方坦克向量
				Tank e = enemys.get(j);				// 取一辆敌方坦克
				e.setRect(); 						// 设置坦克的矩形
				int[] rect2 = e.getRect();			// 取得坦克的矩形
				if (RectCollide.isCollding(rect1, rect2)){		// 如果p1子弹击中了敌方坦克
					int armor = e.getArmor(); 					// 得到坦克的盔甲值
					if (armor > 1) { 							// 如果盔甲值大于1
						SoundPlayer play = new SoundPlayer("sound/hit.wav");
						play.start();
						armor--;								// 盔甲值减一
						e.setArmor(armor);
						b.setLive(false); 						// 子弹死亡
						p1bs.remove(b);							// 从弹夹中移除子弹
						Blast blast = b.blast(); 				// 产生爆炸效果
						blasts.add(blast);						// 把爆炸场面加入到对应的向量中
					} else {
						SoundPlayer play = new SoundPlayer("sound/blast.wav");
						play.start();
						b.setLive(false); 						// 子弹死亡
						p1bs.remove(b);							// 从弹夹中移除子弹
						e.setLive(false); 						// 坦克死亡
						enemys.remove(e);						// 从向量中移除死亡的坦克
						enemyNum--; 							// 坦克数量减一
						b.setTank(true);						// 子弹撞到了坦克
						Blast blast = b.blast(); 				// 产生爆炸效果
						blasts.add(blast);						// 把爆炸场面加入到对应的向量中
					}
				}
			}
		}
		for (int i=0; i<p2bs.size(); i++) {		// 遍历p2坦克的弹夹
			Bullet b = p2bs.get(i);				// 获取一颗子弹
			b.setRect(); 						// 设置子弹的矩形
			int[] rect1 = b.getRect(); 			// 取得子弹的矩形
			for (int j=0; j<enemys.size(); j++) {	// 遍历敌方坦克向量
				Tank e = enemys.get(j);				// 取一辆敌方坦克
				e.setRect(); 						// 设置坦克的矩形
				int[] rect2 = e.getRect();			// 取得坦克的矩形
				if (RectCollide.isCollding(rect1, rect2)){		// 如果p2子弹击中了敌方坦克
					int armor = e.getArmor(); 					// 得到坦克的盔甲值
					if (armor > 1) { 							// 如果盔甲值大于1
						SoundPlayer play = new SoundPlayer("sound/hit.wav");
						play.start();
						armor--;								// 盔甲值减一
						e.setArmor(armor);
						b.setLive(false); 						// 子弹死亡
						p2bs.remove(b);							// 从弹夹中移除子弹
						Blast blast = b.blast(); 				// 产生爆炸效果
						blasts.add(blast);						// 把爆炸场面加入到对应的向量中
					} else {
						SoundPlayer play = new SoundPlayer("sound/blast.wav");
						play.start();
						b.setLive(false); 						// 子弹死亡
						p2bs.remove(b);							// 从弹夹中移除子弹
						e.setLive(false); 						// 坦克死亡
						enemys.remove(e);						// 从向量中移除死亡的坦克
						enemyNum--; 							// 坦克数量减一
						b.setTank(true);						// 子弹撞到了坦克
						Blast blast = b.blast(); 				// 产生爆炸效果
						blasts.add(blast);						// 把爆炸场面加入到对应的向量中
					}
				}
			}
		}
		for (int i=0; i<enemys.size(); i++){	// 遍历敌方坦克向量
			Tank e = enemys.get(i);				// 取出一辆坦克
			Vector<Bullet> bs = new Vector<Bullet>();	// 声明弹夹
			bs = ((Enemy) e).getenemybs(); 				// 获取这个坦克的弹夹
			for (int j=0; j<bs.size(); j++){			// 遍历弹夹
				Bullet b = bs.get(j);					// 取出子弹
				b.setRect(); 							// 设置子弹的矩形
				int[] rect1 = b.getRect(); 				// 取得子弹的矩形
				for (int k=0; k<p1s.size(); k++){		// 遍历p1向量
					Tank p = p1s.get(k);				// 取得p1
					p.setRect(); 						// 设置p1的矩形
					int[] rect2 = p.getRect(); 			// 取得p1的矩形
					if (RectCollide.isCollding(rect1, rect2)) {
						// 如果敌方坦克的子弹击中了p1
						SoundPlayer play = new SoundPlayer("sound/blast.wav");
						play.start();
						b.setLive(false);				// 子弹死亡
						bs.remove(b); 					// 从弹夹中移除子弹
						p.setLive(false); 				// p1死亡
						p1s.remove(p); 					// 从p1向量中移除p1
						b.setTank(true); 				// 子弹撞击坦克
						Blast blast = b.blast(); 		// 产生爆炸效果
						blasts.add(blast);				// 把爆炸场面加入到对应的向量中
						if (p1_life > 0) { 				// 如果生命数大于1
							p1_ismoving = false;
							addP1(); 					// 再创建一个p1
						} else {
							p1_over = true;
						}
					}
				}
				for (int k=0; k<p2s.size(); k++){		// 遍历p2向量
					Tank p = p2s.get(k);				// 取得p2
					p.setRect(); 						// 设置p2的矩形
					int[] rect2 = p.getRect(); 			// 取得p2的矩形
					if (RectCollide.isCollding(rect1, rect2)) {
						// 如果敌方坦克的子弹击中了p2
						SoundPlayer play = new SoundPlayer("sound/blast.wav");
						play.start();
						b.setLive(false);				// 子弹死亡
						bs.remove(b); 					// 从弹夹中移除子弹
						p.setLive(false); 				// p2死亡
						p2s.remove(p); 					// 从p2向量中移除p2
						b.setTank(true); 				// 子弹撞击坦克
						Blast blast = b.blast(); 		// 产生爆炸效果
						blasts.add(blast);				// 把爆炸场面加入到对应的向量中
						if (p2_life > 0) {	 			// 如果生命数大于1
							p2_ismoving = false;
							addp2(); 					// 再创建一个p2
						} else {
							p2_over = true;
						}
					}
				}
			}
		}
	}

	private void addp2() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				for (int i=0; i<map.length; i++){
					for (int j=0; j<map[i].length; j++){
						if (map[i][j] == 7){    // 遍历地图，在地图坐标为6的地方创建p2
							P2TankFactory factory = new P2TankFactory();
							Tank p2 = factory.add(j*60, i*60);
							p2.setStage(stage);
							p2s.add(p2);
						}
					}
				}
			}
		};
		timer.schedule(task, 1000);
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map[i].length; j++){
				if (map[i][j] == 7){    // 遍历地图，在地图坐标为6的地方创建p2
					Born(j*60, i*60);
				}
			}
		}
		p2_life--;
		Introduce.p2_life--;
	}

	private void addP1() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				for (int i=0; i<map.length; i++){
					for (int j=0; j<map[i].length; j++){
						if (map[i][j] == 6){    // 遍历地图，在地图坐标为6的地方创建p1
							P1TankFactory factory = new P1TankFactory();
							Tank p1 = factory.add(j*60, i*60);
							p1.setStage(stage);
							p1s.add(p1);
						}
					}
				}
			}
		};
		timer.schedule(task, 1000);
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map[i].length; j++){
				if (map[i][j] == 6){    // 遍历地图，在地图坐标为6的地方创建p1
					Born(j*60, i*60);
				}
			}
		}
		p1_life--;
		Introduce.p1_life--;
	}
}
