package starfoxFulldido;

public class Shot {
	
	Vec3 pos;
	Vec3 dir;
	double speed = 0.5;
	double life = 0;
	
	public Shot(Vec3 pos, Vec3 dir) {
		this.pos = pos;
		this.dir = dir;
		
	}
	
	public void update() {
		pos = pos.add(dir.scale(speed));
		life += 1;
	}
	
	public boolean isDead() {
		return life > 200 || pos.z > 50;
	}

}
