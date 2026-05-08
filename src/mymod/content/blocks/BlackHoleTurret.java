package mymod.content.blocks;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.gen.Bullet;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Groups;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Env;
import mymod.content.effect.BlackHole;
import mymod.content.items.DarkMatter;
import mymod.content.items.VoidCrystal;

import static mindustry.content.Items.*;
import static mymod.content.effect.BlackHole.blackHoleBulletEffect;
import static mymod.content.effect.BlackHole.*;

public class BlackHoleTurret {
    public static ItemTurret blackholeturret;

    public static void load() {
        var blackHoleBullet = new BasicBulletType(2.5f, 0){
            {
                lifetime = 90f;
                damage = 25;
                width = 12f;
                height = 12f;
                shrinkX = shrinkY = 0f;
                trailEffect = blackHoleBulletEffect;
                hitEffect = blackHoleExplosionEffect;
                shootEffect = Fx.none;
                keepVelocity = false;
                collidesAir = true;
                collidesGround = true;
                hittable = false;
                pierce = true;
                pierceBuilding = true;
                buildingDamageMultiplier = 0.2f;
            }

            @Override
            public void update(Bullet b){
                if(b.time % 2 < 1){
                    blackHoleBulletEffect.at(b.getX(), b.getY());
                }

                BlackHole.triggerPersistentBlackHole(b);

                float radius = 60f;
                float dps = 5f;

                Groups.unit.intersect(b.getX() - radius, b.getY() - radius, radius * 2, radius * 2, u -> {
                    if (!u.dead && u.team != b.team && u.within(b.getX(), b.getY(), radius)) {
                        float angle = Mathf.atan2(u.getY() - b.getY(), u.getX() - b.getX()) * Mathf.radDeg;
                        float strength = 0.4f;
                        u.vel.add(Mathf.cosDeg(angle) * -strength, Mathf.sinDeg(angle) * -strength);
                        u.damageContinuous(dps);
                    }
                });
            }

            @Override
            public void despawned(Bullet b){
                BlackHole.triggerMegaBlackHole(b);
                Damage.damage(b.getX(), b.getY(), 60f, 300f); // 建築 & 單位持續範圍爆炸
            }
        };


        blackholeturret = new ItemTurret("blackhole-turret"){{
            requirements(Category.turret, ItemStack.with(silicon, 220, titanium, 160, plastanium, 120, surgeAlloy, 80, DarkMatter.darkmatter, 120, VoidCrystal.voidcrystal, 60));
            size = 3;
            health = 1800;
            reload = 180f;
            range = 320f;
            shootCone = 15f;
            shootEffect = Fx.none;
            shootSound = Sounds.shootMissileLarge;

            ammo(DarkMatter.darkmatter, blackHoleBullet);
            targetAir = true;
            targetGround = true;
            rotateSpeed = 6f;
            coolantMultiplier = 0.6f;
            drawer = new DrawTurret();
            envEnabled |= Env.any;
        }};
    }
}
