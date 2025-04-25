package mymod.content.bullets;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.gen.Bullet;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Groups;
import mymod.content.effect.BlackHole;

import static mymod.content.effect.BlackHole.blackHoleBulletEffect;
import static mymod.content.effect.BlackHole.blackHoleExplosionEffect;

public class BlackHoleBullet extends BasicBulletType {
    public BlackHoleBullet() {
        super(2.5f, 0);
        lifetime = 90f;
        damage = 25;
        width = height = 12f;
        shrinkX = shrinkY = 0f;
        trailEffect = blackHoleBulletEffect;
        hitEffect   = blackHoleExplosionEffect;
        shootEffect = Fx.none;
        keepVelocity   = false;
        collidesAir    = true;
        collidesGround = true;
        hittable       = false;
        pierce         = true;
        pierceBuilding = true;
        buildingDamageMultiplier = 0.2f;
    }

    @Override
    public void update(Bullet b) {
        // 每兩幀觸發一次拖尾特效
        if (b.time % 2 < 1) {
            blackHoleBulletEffect.at(b.getX(), b.getY());
        }
        // 持續吸附邏輯
        BlackHole.triggerPersistentBlackHole(b);

        float radius = 60f, dps = 5f;
        Groups.unit.intersect(
                b.getX() - radius, b.getY() - radius,
                radius * 2, radius * 2,
                u -> {
                    if (!u.dead && u.team != b.team && u.within(b.getX(), b.getY(), radius)) {
                        float angle = Mathf.atan2(u.getY() - b.getY(), u.getX() - b.getX()) * Mathf.radDeg;
                        float strength = 0.4f;
                        u.vel.add(Mathf.cosDeg(angle) * -strength, Mathf.sinDeg(angle) * -strength);
                        u.damageContinuous(dps);
                    }
                }
        );
    }

    @Override
    public void despawned(Bullet b) {
        BlackHole.triggerMegaBlackHole(b);
        Damage.damage(b.getX(), b.getY(), 60f, 300f);
    }
}
