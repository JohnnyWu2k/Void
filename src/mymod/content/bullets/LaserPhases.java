package mymod.content.bullets;

import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mymod.content.effect.RedEffects;

public class LaserPhases {

    // 第一階段子彈
    public static class PhaseOneBeam extends BasicBulletType {
        public PhaseOneBeam() {
            speed = 12f;
            damage =  60f;
            width = 4f;
            hitEffect   = Fx.hitLaser;
            shootEffect = Fx.railShoot;
            pierce = true;
        }
        @Override
        public void update(Bullet b) {
            super.update(b);
            if (b.time % 2 < 1) {
                RedEffects.redLaserTrail.at(b.x, b.y);
            }
        }
    }

    // 第二階段子彈
    public static class PhaseTwoBeam extends BasicBulletType {
        public PhaseTwoBeam() {
            speed = 18f;
            damage = 100f;
            width = 6f;
            hitEffect     = Fx.hitLaser;
            shootEffect   = Fx.railShoot;
            pierce         = true;
            pierceBuilding = true;
        }

        @Override
        public void update(Bullet b) {
            super.update(b);
            if (b.time % 2 < 1) {
                RedEffects.redLaserTrail.at(b.x, b.y);
            }
        }
    }

    // 第三階段子彈
    public static class PhaseThreeBeam extends BasicBulletType {
        public PhaseThreeBeam() {
            speed = 24f;
            damage = 150f;
            width = 8f;
            hitEffect   = Fx.hitLaser;
            shootEffect = Fx.railShoot;
            pierce = true;
            // 你可以再加更多屬性…
        }
    }
}
