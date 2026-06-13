package starfoxFulldido;

public class Enemy {

    Vec3 pos;
    double radius = 3.0;
    double speed = 0.01;
    int health;
    boolean dead;
    Mesh mesh;
    boolean counted;
    private int deathTimer = 10;
    public boolean exploded = false;

    EnemyState state = EnemyState.APPROACH;
    Vec3 target = new Vec3(0, 0, 0);

    double attackDistance = 5.0;
    double evadeTime = 0;

    public Enemy(Vec3 pos) {
        this.pos = pos;
        this.mesh = Mesh.createShip();
        this.dead = false;
        this.counted = false;
        this.health = 3;
    }

    public enum EnemyState {
        APPROACH,
        ATTACK,
        EVADE,
        DEAD
    }

    private void moveTowards(Vec3 target, double spd) {
        Vec3 dir = new Vec3(
            target.x - pos.x,
            target.y - pos.y,
            target.z - pos.z
        ).normalize();

        pos = pos.add(dir.scale(spd));
    }

    private void evade() {
        double time = System.currentTimeMillis() * 0.005;

        pos.x += Math.sin(time) * 0.2;
        pos.y += Math.cos(time) * 0.1;
    }

    public void update(Vec3 playerPos) {

        this.target = playerPos;

        if (dead) {
            state = EnemyState.DEAD;
            deathTimer--;
            return;
        }

        double dist = new Vec3(
            target.x - pos.x,
            target.y - pos.y,
            target.z - pos.z
        ).length();

        switch (state) {

            case APPROACH:
                moveTowards(target, speed);

                double time = System.currentTimeMillis() * 0.002;
                pos.x += Math.sin(time + pos.z) * 0.03;

                if (dist < attackDistance) {
                    state = EnemyState.ATTACK;
                }
            break;

            case ATTACK:
                moveTowards(target, speed * 1.5);

                if (Math.random() < 0.01) {
                    state = EnemyState.EVADE;
                    evadeTime = 60;
                }
            break;

            case EVADE:
                evade();

                evadeTime--;
                if (evadeTime <= 0) {
                    state = EnemyState.APPROACH;
                }
            break;

            case DEAD:
            break;
        }
    }

    public void takeDamage(int damage) {
        if (dead) return;

        health -= damage;

        if (health <= 0) {
            dead = true;
        }
    }

    public boolean canRemove() {
        return dead && deathTimer <= 0;
    }

    public boolean isDead() {
        return dead;
    }
}
