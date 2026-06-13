package starfoxFulldido;

import java.awt.Color;
import java.awt.Graphics;

public class Renderer {
	
	  public void drawTriangle(Graphics g, Triangle t) {

	        int[] xPoints = {
	            (int) t.v1.x,
	            (int) t.v2.x,
	            (int) t.v3.x
	        };

	        int[] yPoints = {
	            (int) t.v1.y,
	            (int) t.v2.y,
	            (int) t.v3.y
	        };

	        // cor do triângulo
	        g.setColor(t.color);
	        g.fillPolygon(xPoints, yPoints, 3);

	        // borda preta (estilo SNES)
	        g.setColor(Color.BLACK);
	        g.drawPolygon(xPoints, yPoints, 3);
	    }

}
