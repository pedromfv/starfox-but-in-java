package starfoxFulldido;

public class Particle {
	
	public Vec3 pos;
	public Vec3 vel;
	private int life = 30;
	public double depth;
	public double rotX, rotY, rotZ, rotVelZ, rotVelY, rotVelX;
	
	  public Particle(Vec3 pos) {
	        this.pos = new Vec3(pos.x, pos.y, pos.z);

	        vel = new Vec3(
	            (Math.random() - 0.5) * 0.6,
	            (Math.random() - 0.5) * 0.6,
	            (Math.random() - 0.5) * 0.6
	        );
	        rotX = Math.random() * Math.PI * 2;
	        rotY = Math.random() * Math.PI * 2;
	        rotZ = Math.random() * Math.PI * 2;
	        rotVelX = (Math.random() - 0.5) * 0.2;
	        rotVelY = (Math.random() - 0.5) * 0.2;
	        rotVelZ = (Math.random() - 0.5) * 0.2;
	    }

	    public void update() {
	        pos = pos.add(vel);
	        vel = vel.scale(0.92);

	        rotX += rotVelX;
	        rotY += rotVelY;
	        rotZ += rotVelZ;

	        rotVelX *= 0.98;
	        rotVelY *= 0.98;
	        rotVelZ *= 0.98;

	        life--;
	    }

	    public boolean isDead() {
	        return life <= 0;
	    }
	}
