package main;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	public GameFrame() {
		super("坦克游戏大战");
		setSize(1100, 820);
		setLocationRelativeTo(null);  // 窗口将置于屏幕的中央
		
		Container container = getContentPane();
		BorderLayout layout = new BorderLayout();
		container.setLayout(layout);
		
		GamePanel panel = new GamePanel();
		container.add(panel, BorderLayout.CENTER);
		addKeyListener(panel);
		
		Introduce introduce = new Introduce();
		container.add(introduce, BorderLayout.EAST);
		
		setVisible(true);
	}
}
