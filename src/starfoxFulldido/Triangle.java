package starfoxFulldido;

import java.awt.Color;

public class Triangle {

    public Vec3 v1, v2, v3;
    public Color color;

    public Triangle(Vec3 v1, Vec3 v2, Vec3 v3, Color color) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
    }
}