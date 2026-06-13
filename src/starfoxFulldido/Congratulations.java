package starfoxFulldido;

import java.awt.Canvas;
import java.awt.Graphics;

public class Congratulations extends Canvas implements Runnable {
	
	public static boolean run = false;
	
	public Congratulations() {
		new Thread(this).start();
		run = true;
		
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		
	}

	public void run() {
		while(run) {
			update();
		}
	}

}
