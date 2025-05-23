package mymod.content.bullets;

import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mymod.content.effect.RedEffects;

public class LaserBeamBullet extends BasicBulletType {
    public LaserBeamBullet() {




        speed=18f;
        damage=100f;

        // 光束寬度
        width = 6f;
        collidesAir=true;
        collidesGround=true;
        hittable=true;
        // 特效
        hitEffect     = Fx.hitLaser;   // 碰到目標的特效
        shootEffect   = Fx.railShoot; // 開火時的瞬間特效
        despawnEffect = Fx.none;       // 消失時不額外特效

        // 行為
        pierce         = true;  // 穿透單位
        pierceBuilding = true;  // 穿透建築

    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        // 在光束行進路徑上加點拖尾閃光
        if (b.time % 2 < 1) {
            RedEffects.redLaserTrail.at(b.x, b.y);
        }
    }
}
