package starfoxFulldido;

public class Math3d {
	
	static Vec3 rotateY(Vec3 p, double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double x = p.x *cos + p.z*sin;
		double z = -p.x * sin + p.z *cos;
		return new Vec3(x, p.y, z);
	}
	
	static Vec3 rotateX(Vec3 p, double angle) {
		   double cos = Math.cos(angle);
		    double sin = Math.sin(angle);

		    double y = p.y * cos - p.z * sin;
		    double z = p.y * sin + p.z * cos;

		    return new Vec3(p.x, y, z);
	}
	public static Vec3 rotateZ(Vec3 p, double angle) {
	    double cos = Math.cos(angle);
	    double sin = Math.sin(angle);

	    double x = p.x * cos - p.y * sin;
	    double y = p.x * sin + p.y * cos;

	    return new Vec3(x, y, p.z);
	}
	
	public static Vec3 project(Vec3 p, int width, int height) {
		double f = 100;
		if(p.z <= 0)  return new Vec3(0, 0, p.z);
		double x = (p.x * f) / p.z;
		double y = (p.y * f) / p.z;
		return new Vec3(x + width / 2, y + height / 2, p.z);
	}

}
