package mymod.content.blocks;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Geometry;
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
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mymod.content.effect.BlackHole;
import mymod.content.items.DarkMatter;

import static mindustry.content.Items.*;
import static mymod.content.effect.BlackHole.blackHoleBulletEffect;
import static mymod.content.effect.BlackHole.*;
import mymod.content.bullets.BlackHoleBullet;

public class BlackHoleTurret {
    public static ItemTurret blackholeturret;

    public static void load() {
        var bh = new BlackHoleBullet();
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
            localizedName = "黑洞發射器";
            description = "聚集極度濃縮的暗物質，瞬間生成微型黑洞，對路徑上的敵方單位造成傷害，並在爆裂邊緣釋放毀滅性引力衝擊波。";
            requirements(Category.turret, ItemStack.with(silicon, 120, titanium, 80, plastanium, 60, DarkMatter.darkmatter, 20));
            size = 3;
            health = 680;
            reload = 90f;
            range = 360f;
            shootCone = 15f;
            shootEffect = Fx.none;
            shootSound = Sounds.shootBig;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
            ammo(silicon, bh);

=======
            ammo(DarkMatter.darkmatter, blackHoleBullet);
>>>>>>> Stashed changes
=======
            ammo(DarkMatter.darkmatter, blackHoleBullet);
>>>>>>> Stashed changes
            targetAir = true;
            targetGround = true;
            rotateSpeed = 6f;
            coolantMultiplier = 0.6f;
            drawer = new DrawTurret();
            envEnabled |= Env.any;
        }};
    }
}
