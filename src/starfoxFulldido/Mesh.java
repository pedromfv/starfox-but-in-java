package starfoxFulldido;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Mesh {
	List<Triangle> tris = new ArrayList<>();
	
	public static Mesh createShip() {
	       Mesh mesh = new Mesh();

	        Vec3[] v = {

	        		  new Vec3(1.199999, 2.442308, -1.8),   //1
	                  new Vec3(0.9, 1.062308, 0.599999),    //2
	                  new Vec3(0.9, 1.602308, -0.66),       //3
	                  new Vec3(-1.200001, 2.442307, -1.8),  //4
	                  new Vec3(-0.9, 1.062307, 0.599999),   //5
	                  new Vec3(-0.9, 1.962308, -0.42),      //6
	                  new Vec3(-0.6, 1.542308, -0.6),       //7
	                  new Vec3(-0.9, 1.602308, -0.66),      //8
	                  new Vec3(0.12, 1.902308, 0.299999),   //9
	                  new Vec3(-0.12, 1.902308, 0.299999),  //10
	                  new Vec3(-0.12, 2.202308, -0.78),     //11
	                  new Vec3(0.12, 2.202308, -0.78),      //12
	                  new Vec3(0.6, 1.542308, -0.6),        //13
	                  new Vec3(0.9, 1.962308, -0.42),       //14
	                  new Vec3(1.2, 1.542308, -0.6),        //15
	                  new Vec3(1.08, 1.422308, -0.42),      //16
	                  new Vec3(0.78, 1.422308, -0.42),      //17
	                  new Vec3(-1.2, 1.542308, -0.6),       //18
	                  new Vec3(-1.08, 1.422307, -0.42),     //19
	                  new Vec3(-0.78, 1.422308, -0.42),     //20
	                  new Vec3(2.1, 0.942309, -3),          //21
	                  new Vec3(1.2, 1.542308, -0.6),        //22
	                  new Vec3(1.619999, 1.242308, -1.8),   //23
	                  new Vec3(1.8, 1.242308, -1.8),        //24
	                  new Vec3(-0.72, 1.482308, -0.6),      //25
	                  new Vec3(0, 1.662308, 3),             //26
	                  new Vec3(0.72, 1.482308, -0.6),       //27
	                  new Vec3(-2.1, 0.942307, -3),         //28
	                  new Vec3(-1.8, 1.242307, -1.8),       //29
	                  new Vec3(-1.62, 1.242307, -1.8),      //30
	                  new Vec3(-0.6, 1.542308, -0.6),       //31
	                  new Vec3(1.2, 1.242308, -1.200001),   //32
	                  new Vec3(0.6, 1.542308, -0.6),        //33
	                  new Vec3(-1.2, 1.242308, -1.200001),  //34
	                  new Vec3(-1.2, 1.542308, -0.6),       //35
	                  new Vec3(0, 1.182308, 0.48),          //36
	                  new Vec3(0, 1.902308, -1.200001),     //37
	                  new Vec3(-0.6, 1.542308, -0.6),       //38
	                  new Vec3(-0.3, 1.602308, -0.66),      //39
	                  new Vec3(0, 1.842308, -0.960001),     //40
	                  new Vec3(0.3, 1.602308, -0.66),       //41
	                  new Vec3(0, 1.662308, 3)              //42
	              };

	              // CORES
	              Color c77 = new Color(180,180,180);
	              Color cBC = new Color(80,120,255);
	              Color cDD = new Color(255,80,80);
	              Color c88 = new Color(120,120,120);
	              Color cAA = new Color(150,150,200);
	              Color cBB = new Color(50,80,200);
	              Color c66 = new Color(80,80,80);
	              Color c55 = new Color(40,40,40);
	              Color cCC = new Color(255,120,120);
	              Color c99 = new Color(255,255,255);
	              Color c44 = new Color(20,20,20);
	              Color c9A = new Color(255,220,50);
	              Color cEE = new Color(255,255,255);

	              // ================= color_77 =================
	              mesh.tris.add(new Triangle(v[2], v[1], v[0], c77));
	              mesh.tris.add(new Triangle(v[5], v[4], v[3], c77));
	              mesh.tris.add(new Triangle(v[7], v[4], v[6], c77));
	              mesh.tris.add(new Triangle(v[8], v[9], v[10], c77));
	              mesh.tris.add(new Triangle(v[8], v[10], v[11], c77));
	              mesh.tris.add(new Triangle(v[13], v[1], v[12], c77));

	              // ================= color_BC =================
	              mesh.tris.add(new Triangle(v[16], v[15], v[14], cBC));
	              mesh.tris.add(new Triangle(v[19], v[18], v[17], cBC));
	              mesh.tris.add(new Triangle(v[17], v[6], v[19], cBC));
	              mesh.tris.add(new Triangle(v[18], v[19], v[6], cBC));
	              mesh.tris.add(new Triangle(v[6], v[17], v[18], cBC));
	              mesh.tris.add(new Triangle(v[15], v[16], v[12], cBC));
	              mesh.tris.add(new Triangle(v[14], v[12], v[16], cBC));
	              mesh.tris.add(new Triangle(v[12], v[14], v[15], cBC));

	              // ================= color_DD =================
	              mesh.tris.add(new Triangle(v[20], v[21], v[12], cDD));
	              mesh.tris.add(new Triangle(v[20], v[23], v[22], cDD));
	              mesh.tris.add(new Triangle(v[24], v[25], v[6], cDD));
	              mesh.tris.add(new Triangle(v[11], v[12], v[8], cDD));
	              mesh.tris.add(new Triangle(v[8], v[25], v[9], cDD));
	              mesh.tris.add(new Triangle(v[12], v[25], v[26], cDD));
	              mesh.tris.add(new Triangle(v[29], v[28], v[27], cDD));
	              mesh.tris.add(new Triangle(v[30], v[17], v[27], cDD));

	              // ================= color_88 =================
	              mesh.tris.add(new Triangle(v[32], v[14], v[31], c88));
	              mesh.tris.add(new Triangle(v[0], v[1], v[13], c88));
	              mesh.tris.add(new Triangle(v[33], v[34], v[6], c88));
	              mesh.tris.add(new Triangle(v[3], v[4], v[7], c88));

	              // ================= color_AA =================
	              mesh.tris.add(new Triangle(v[20], v[12], v[31], cAA));
	              mesh.tris.add(new Triangle(v[25], v[35], v[6], cAA));
	              mesh.tris.add(new Triangle(v[10], v[6], v[36], cAA));
	              mesh.tris.add(new Triangle(v[33], v[27], v[17], cAA));

	              // ================= color_BB =================
	              mesh.tris.add(new Triangle(v[14], v[20], v[31], cBB));
	              mesh.tris.add(new Triangle(v[35], v[25], v[12], cBB));
	              mesh.tris.add(new Triangle(v[9], v[6], v[10], cBB));
	              mesh.tris.add(new Triangle(v[11], v[10], v[36], cBB));
	              mesh.tris.add(new Triangle(v[33], v[6], v[27], cBB));

	              // ================= color_66 =================
	              mesh.tris.add(new Triangle(v[6], v[4], v[5], c66));
	              mesh.tris.add(new Triangle(v[7], v[6], v[3], c66));
	              mesh.tris.add(new Triangle(v[0], v[13], v[12], c66));
	              mesh.tris.add(new Triangle(v[12], v[1], v[2], c66));

	              // ================= color_55 =================
	              mesh.tris.add(new Triangle(v[6], v[5], v[3], c55));
	              mesh.tris.add(new Triangle(v[0], v[12], v[2], c55));

	              // ================= color_CC =================
	              mesh.tris.add(new Triangle(v[37], v[9], v[25], cCC));
	              mesh.tris.add(new Triangle(v[11], v[36], v[12], cCC));

	              // ================= color_99 =================
	              mesh.tris.add(new Triangle(v[36], v[38], v[39], c99));
	              mesh.tris.add(new Triangle(v[40], v[38], v[6], c99));
	              mesh.tris.add(new Triangle(v[36], v[6], v[38], c99));
	              mesh.tris.add(new Triangle(v[39], v[40], v[12], c99));
	              mesh.tris.add(new Triangle(v[36], v[39], v[12], c99));
	              mesh.tris.add(new Triangle(v[40], v[6], v[12], c99));

	              // ================= color_44 =================
	              mesh.tris.add(new Triangle(v[39], v[38], v[40], c44));

	              // ================= color_9A =================
	              mesh.tris.add(new Triangle(v[35], v[12], v[6], c9A));

	              // ================= color_EE =================
	              mesh.tris.add(new Triangle(v[8], v[12], v[41], cEE));

	              return mesh;

	}
	
	
	}



