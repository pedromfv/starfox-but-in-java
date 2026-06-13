package starfoxFulldido;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, ActionListener, MouseMotionListener{
	
	JFrame f = new JFrame("Java Starfox");
	private Robot robot;
	private boolean recentering = false;
	private boolean running = true;
	private Mesh mesh;
	private Renderer renderer;
	private double pitch = 0;
	private double roll = 0;
	private int score = 0;
	private double pitchVel = 0;
	private double rollVel = 0;
	private double shipX = 0;
	private double shipY = 0;
	private double groundOffset = 0;
	private int playerHealth = 100;
	private int maxHealth = 100;
	private int damageCooldown = 0;
	private List<Shot> shot = new ArrayList<Shot>();
	private List<Enemy> enemies = new ArrayList<>();
	private List<Particle> particle = new ArrayList<>();
	private List<Vec3> stars = new ArrayList<>();
	
	public Game() {
		ImageIcon icon = new ImageIcon(getClass().getResource("icon.png"));
		f.setIconImage(icon.getImage());
		mesh = Mesh.createShip();
		renderer = new Renderer();
		f.add(this);
		f.setSize(1000,800);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		Music.playLoop("corneria.wav");
		f.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
		        running = false;
		        f.dispose();
		    }
		});
		   this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
	                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
	                new Point(),
	                null));

	        this.addMouseMotionListener(this);
	        this.addMouseListener(new MouseAdapter() {
	        	
	        	public void mousePressed(MouseEvent e) {
	        		shoot();
	        	}
	        });

	        try {
	            robot = new Robot();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        centerMouse();
	        for (int i = 0; i < 1200; i++) {

	            double screenX = Math.random() * getWidth();
	            double screenY = Math.random() * (getHeight() / 2);

	            double x = (screenX - getWidth()/2) / 20.0;
	            double y = (screenY - getHeight()/2) / 20.0;
	            double z = Math.random() * 100 + 1;

	            stars.add(new Vec3(x, y, z));
	        }
	     
	}
	private void spawnExplosion(Vec3 pos) {
	    for (int k = 0; k < 20; k++) {
	        particle.add(new Particle(pos));
	    }
	    
	 /*
	    if(score >= 40000) {
	    	Congratulations cong = new Congratulations();
	    	f.dispose();
	    	Congratulations.run = true;
	    	new Thread(cong).start();
	    }
	 */
	}
	  
	private void shoot() {
		Sound.play("shoot.wav", -5f);
		Vec3 start = new Vec3(0, 1.5, 2.5);
		Vec3 dir = new Vec3(0,0,1);
		start = Math3d.rotateZ(start,roll);
		start = Math3d.rotateX(start, pitch);
		dir = Math3d.rotateZ(dir,roll);
		dir = Math3d.rotateX(dir,pitch);
		double yaw = roll * 0.5;
		start = Math3d.rotateY(start, yaw);
		dir = Math3d.rotateY(dir,yaw);
		dir = dir.normalize();
		start.z += 4;
		if(shot.size() < 50) {
			shot.add(new Shot(start,dir));
		}
	}
	
	private void centerMouse() {
	    if (robot == null) return; 

	    Point p = this.getLocationOnScreen();
	    int centerX = p.x + getWidth() / 2;
	    int centerY = p.y + getHeight() / 2;

	    recentering = true;
	    robot.mouseMove(centerX, centerY);
	}

	public void run() {
		while(running) {
			update();
			render();
			
			try {
				Thread.sleep(16);
			}catch(Exception e) {
				
			}
		}
		
	}
	
	 private void update() {

	        pitch += pitchVel;
	        roll += rollVel;
	        shipX += roll * 0.1;
	        shipY += pitch * 0.1;

	        shipX = Math.max(-3, Math.min(3, shipX));
	        shipY = Math.max(-2, Math.min(2, shipY));

	        pitchVel *= 0.9;
	        rollVel *= 0.9;

	        pitch *= 0.98;
	        roll *= 0.98;

	        pitch = Math.max(-0.5, Math.min(0.5, pitch));
	        roll = Math.max(-0.5, Math.min(0.5, roll));

	        for (int i = 0; i < shot.size(); i++) {
	            Shot s = shot.get(i);
	            s.update();

	            if (s.isDead()) {
	                shot.remove(i);
	                i--;
	            }
	        }

	        for (int i = 0; i < shot.size(); i++) {
	            Shot s = shot.get(i);

	            for (int j = 0; j < enemies.size(); j++) {
	                Enemy e = enemies.get(j);

	                double dx = s.pos.x - e.pos.x;
	                double dy = s.pos.y - e.pos.y;
	                double dz = s.pos.z - e.pos.z;

	                double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);

	                if (dist < e.radius) {
	                    shot.remove(i);
	                    e.takeDamage(1);
	                    i--;
	                    break;
	                }
	            }
	        }
	        for (int i = 0; i < enemies.size(); i++) {
	            Enemy e = enemies.get(i);
	            Vec3 playerPos = new Vec3(shipX, shipY, 4);
	            e.update(playerPos);

	            if (!e.isDead()) {
	            	double dx = e.pos.x - shipX;
	            	double dy = e.pos.y - shipY;
	            	double dz = e.pos.z - 4;
	            	double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);


	                if (dist < 2.2 && damageCooldown == 0) {
	                	playerHealth -= 10;
	                    damageCooldown = 20;
	                    e.dead = true;
                        if(!e.exploded) {
                        	spawnExplosion(e.pos);e.exploded = true;
                        }
	                }
	            }

	            if (e.isDead() && !e.counted) {
	                score += 100;
	                e.counted = true;
	                Sound.play("explosion.wav", -10f);
	                for (int k = 0; k < 20; k++) {
	                    particle.add(new Particle(e.pos));
	                }
	            }

	            if (e.canRemove()) {
	                enemies.remove(i);
	                i--;
	            }
	         
	       
	        }

	        if (Math.random() < 0.02) {
	            double x = (Math.random() - 0.5) * 8;
	            double y = (Math.random() - 0.5) * 6;
	            double z = 20 + Math.random() * 10;

	            enemies.add(new Enemy(new Vec3(x, y, z)));
	        }

	        groundOffset += 0.1;

	        if (damageCooldown > 0) damageCooldown--;

	        playerHealth = Math.max(0, playerHealth);

	        if (playerHealth <= 0) {
	            System.out.println("GAME OVER");
	            running = false;
	        }
	        for(int b = 0; b < particle.size(); b++) {
            	Particle p = particle.get(b);
            	p.update();
            	if(p.isDead()) {
            		particle.remove(b);
            		b--;
            	}
            }

	        for (Vec3 s : stars) {
	            s.z -= 2;

	            s.x -= shipX * 0.05;
	            s.y -= shipY * 0.05;

	            if (s.z <= 1) {
	                s.z = 100;
	                s.x = (Math.random() - 0.5) * 900;
	                s.y = (Math.random() - 0.5) * 900;
	            }
	        }

	     }
	 private void render() {
		    List<Triangle> trianglesToDraw = new ArrayList<>();

		    BufferStrategy bs = this.getBufferStrategy();
		    if (bs == null || !running) {
		        this.createBufferStrategy(3);
		        return;
		    }

		    Graphics g = bs.getDrawGraphics();

		    g.setColor(Color.BLACK);
		    g.fillRect(0, 0, getWidth(), getHeight());

		    int horizon = getHeight() / 2;

		    for (int y = horizon; y < getHeight(); y++) {

		        double perspective = (double)(y - horizon) / (getHeight() - horizon);
		        if (perspective < 0.0000000000001) continue;

		        double z = 1 / perspective;
		        if (z < 1) z = 1;

		        double scale = 600 / Math.sqrt(z);
		        int shade = Math.max(50, (int)(150 - z * 5));

		        g.setColor(new Color(shade, shade, 0));

		        int lineWidth = (int)Math.min(scale, getWidth());
		        int centerX = getWidth()/2 + (int)(roll * 200) - (int)(shipX * 50);

		        g.drawLine(centerX - lineWidth, y, centerX + lineWidth, y);

		    }
		    g.setColor(Color.WHITE);
		    for (Vec3 s : stars) {

		        Vec3 proj = Math3d.project(
		            new Vec3(s.x, s.y, s.z),
		            getWidth(),
		            getHeight()
		        );

		        if (proj.y > getHeight() / 2) continue;

		        int brightness = (int)(200 + Math.random() * 55);
		        g.setColor(new Color(brightness, brightness, brightness));

		        int size = (int)(6 / s.z);
		        size = Math.max(1, size);

		        g.fillRect((int)proj.x, (int)proj.y, size, size);
		    }

		    for (Triangle t : mesh.tris) {

		        Vec3 v1 = new Vec3(t.v1.x, t.v1.y, t.v1.z);
		        Vec3 v2 = new Vec3(t.v2.x, t.v2.y, t.v2.z);
		        Vec3 v3 = new Vec3(t.v3.x, t.v3.y, t.v3.z);

		        v1 = Math3d.rotateZ(v1, roll);
		        v2 = Math3d.rotateZ(v2, roll);
		        v3 = Math3d.rotateZ(v3, roll);

		        v1 = Math3d.rotateX(v1, pitch);
		        v2 = Math3d.rotateX(v2, pitch);
		        v3 = Math3d.rotateX(v3, pitch);

		        double yaw = roll * 0.5;

		        v1 = Math3d.rotateY(v1, yaw);
		        v2 = Math3d.rotateY(v2, yaw);
		        v3 = Math3d.rotateY(v3, yaw);

		        v1.z += 4; v2.z += 4; v3.z += 4;
		        v1.x -= shipX; v2.x -= shipX; v3.x -= shipX;
		        v1.y -= shipY; v2.y -= shipY; v3.y -= shipY;

		        double depth = (v1.z + v2.z + v3.z) / 3;

		        Vec3 p1 = Math3d.project(v1, getWidth(), getHeight());
		        Vec3 p2 = Math3d.project(v2, getWidth(), getHeight());
		        Vec3 p3 = Math3d.project(v3, getWidth(), getHeight());

		        Triangle projected = new Triangle(p1, p2, p3,t.color);
		        projected.v1.z = depth;


		        trianglesToDraw.add(projected);
		    }

		    for (Shot s : shot) {

		        Vec3 p1 = new Vec3(-0.5, -0.05, 1);
		        Vec3 p2 = new Vec3(0.5, -0.05, 1);
		        Vec3 p3 = new Vec3(1, 0.1, 1);

		        double yaw = Math.atan2(s.dir.x, s.dir.z);
		        double pitch = Math.atan2(s.dir.y, s.dir.z);

		        p1 = Math3d.rotateY(p1, yaw);
		        p2 = Math3d.rotateY(p2, yaw);
		        p3 = Math3d.rotateY(p3, yaw);

		        p1 = Math3d.rotateX(p1, -pitch);
		        p2 = Math3d.rotateX(p2, -pitch);
		        p3 = Math3d.rotateX(p3, -pitch);

		        p1 = p1.add(s.pos);
		        p2 = p2.add(s.pos);
		        p3 = p3.add(s.pos);

		        p1.x -= shipX; p2.x -= shipX; p3.x -= shipX;
		        p1.y -= shipY; p2.y -= shipY; p3.y -= shipY;

		        Vec3 proj1 = Math3d.project(p1, getWidth(), getHeight());
		        Vec3 proj2 = Math3d.project(p2, getWidth(), getHeight());
		        Vec3 proj3 = Math3d.project(p3, getWidth(), getHeight());

		        Triangle proj = new Triangle(proj1, proj2, proj3, Color.YELLOW);
		        proj.v1.z = (p1.z + p2.z + p3.z) / 3;

		        trianglesToDraw.add(proj);
		    }

		    for (Enemy e : enemies) {
		        for (Triangle t : e.mesh.tris) {

		            Vec3 v1 = Math3d.rotateY(new Vec3(t.v1.x, t.v1.y, t.v1.z), Math.PI);
		            Vec3 v2 = Math3d.rotateY(new Vec3(t.v2.x, t.v2.y, t.v2.z), Math.PI);
		            Vec3 v3 = Math3d.rotateY(new Vec3(t.v3.x, t.v3.y, t.v3.z), Math.PI);

		            v1 = v1.add(e.pos);
		            v2 = v2.add(e.pos);
		            v3 = v3.add(e.pos);

		            v1.z += 4; v2.z += 4; v3.z += 4;

		            double depth = (v1.z + v2.z + v3.z) / 3;

		            Vec3 p1 = Math3d.project(v1, getWidth(), getHeight());
		            Vec3 p2 = Math3d.project(v2, getWidth(), getHeight());
		            Vec3 p3 = Math3d.project(v3, getWidth(), getHeight());

		            Triangle proj = new Triangle(p1, p2, p3,Color.MAGENTA);
		            proj.v1.z = depth;

		            trianglesToDraw.add(proj);
		        }
		    }

	
		    trianglesToDraw.sort((a, b) -> Double.compare(b.v1.z, a.v1.z));

		    for (Triangle t : trianglesToDraw) {
		        renderer.drawTriangle(g, t);
		    }


		    for (Particle p : particle) {

		        Vec3 v1 = new Vec3(-0.2, -0.2, 0);
		        Vec3 v2 = new Vec3(0.2, -0.2, 0);
		        Vec3 v3 = new Vec3(0, 0.3, 0);

		        v1 = Math3d.rotateX(v1, p.rotX);
		        v1 = Math3d.rotateY(v1, p.rotY);
		        v1 = Math3d.rotateZ(v1, p.rotZ);

		        v2 = Math3d.rotateX(v2, p.rotX);
		        v2 = Math3d.rotateY(v2, p.rotY);
		        v2 = Math3d.rotateZ(v2, p.rotZ);

		        v3 = Math3d.rotateX(v3, p.rotX);
		        v3 = Math3d.rotateY(v3, p.rotY);
		        v3 = Math3d.rotateZ(v3, p.rotZ);

		        v1 = v1.add(p.pos);
		        v2 = v2.add(p.pos);
		        v3 = v3.add(p.pos);

		        v1.x -= shipX; v1.y -= shipY;
		        v2.x -= shipX; v2.y -= shipY;
		        v3.x -= shipX; v3.y -= shipY;

		        v1.z += 4; v2.z += 4; v3.z += 4;

		        Vec3 p1 = Math3d.project(v1, getWidth(), getHeight());
		        Vec3 p2 = Math3d.project(v2, getWidth(), getHeight());
		        Vec3 p3 = Math3d.project(v3, getWidth(), getHeight());

		        Triangle t = new Triangle(p1, p2, p3,Color.ORANGE);
		        t.v1.z = (v1.z + v2.z + v3.z) / 3;
		        renderer.drawTriangle(g, t);
		    }

		    g.setColor(new Color(0, 0, 0, 150));
		    g.fillRect(10, 10, 120, 30);

		    g.setColor(Color.WHITE);
		    g.drawString("HITS: " + score, 20, 30);

		    g.setColor(Color.DARK_GRAY);
		    g.fillRect(20, 50, 200, 20);

		    int healthWidth = (int)((playerHealth / (double)maxHealth) * 200);

		    g.setColor(Color.RED);
		    g.fillRect(20, 50, healthWidth, 20);

		    g.setColor(Color.WHITE);
		    g.drawRect(20, 50, 200, 20);
		    g.drawString("HP: " + playerHealth, 20, 45);

		    g.dispose();
		    bs.show();
		}

	
	public static void main(String[] args) {
		new Thread(new Game()).start();
	}

	public void actionPerformed(ActionEvent e) {
		
		}

	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public void mouseMoved(MouseEvent e) {
		   if (recentering) {
		        recentering = false;
		        return;
		    }

		   int dx = e.getX() - getWidth() / 2;
		   int dy = e.getY() - getHeight() / 2;

		   double sens = 0.0005;

		   rollVel += dx * sens;
		   pitchVel += dy * sens;

		   centerMouse();
	    }
		
	}