package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Introduce extends JPanel implements Runnable{
	static int stage=1, p1_life=2, p2_life=2;
	public Introduce() {
		setPreferredSize(new Dimension(300, 780));
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 300, 780);
		Font font1 = new Font("sans serif", Font.PLAIN, 20);
		g.setFont(font1);
		g.setColor(Color.black);
		g.drawString("游戏说明", 20, 80);
		g.drawString("本游戏共有7关", 20, 110);
		g.drawString("每关有9辆坦克", 20, 140);
		g.drawString("每关有两种道具", 20, 170);
		g.drawString("5秒后出现定时道具", 20, 200);
		g.drawString("20秒后出现炸弹道具", 20, 230);
		g.drawString("道具持续8秒", 20, 260);
		g.drawString("定时道具可以定时5秒", 20, 290);
		g.drawString("炸弹可以引爆所有坦克", 20, 320);
		
		g.drawString("按键说明", 20, 370);
		g.drawString("p1： wsad移动，j射击", 20, 400);
		g.drawString("p2：↑↓←→移动， 1射击", 20, 430);
		
		g.drawString("生命剩余", 20, 480);
		g.drawString("p1：" + String.valueOf(p1_life), 20, 510);
		g.drawString("p2：" + String.valueOf(p2_life), 20, 540);
		
		Font font2 = new Font("宋体", Font.BOLD, 40);
		g.setFont(font2);
		g.drawString("第" + String.valueOf(stage) + "关", 100, 40);
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
		}
	}
}
