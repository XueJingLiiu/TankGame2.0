package main;
/*
 * 2.0在1.0的基础上：
 * 地图增多了，有专门的地图类
 * 使用了工厂方法的设计模式
 * 素材图片更丰富
 * 使用了计时器
 * 简化了碰撞算法
 * 可以双人游戏
 * 增加了注释
 * 增加了声音
 */
import javax.swing.JFrame;

public class TankGame {

	public static void main(String[] args) {
		GameFrame tankGame = new GameFrame();
		tankGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}