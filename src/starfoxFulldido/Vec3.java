package starfoxFulldido;

public class Vec3 {
        double x, y, z;
        
        Vec3(double x, double y, double z){
        	this.x = x;
        	this.y = y;
        	this.z = z;
        	
        }
        
        Vec3 add(Vec3 v) {
        	return new Vec3(x + v.x, y + v.y,  z + v.z);
        }
        
        Vec3 sub(Vec3 v) {
        	return new Vec3(x - v.x, y - v.y, z - v.z );
        }
        
        Vec3 scale(double s) {
        	return new Vec3(x * s, y * s, z * s);
        }
        Vec3 normalize() {
        	double len = Math.sqrt(x * x + y * y + z * z);
        	return new Vec3(x/len, y/len, z/len);
        	
        }
        public double length() {
            return Math.sqrt(x * x + y * y + z * z);
        }
}
