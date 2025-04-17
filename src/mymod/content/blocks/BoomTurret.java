package mymod.content.blocks;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Env;

import static mindustry.content.Items.*;

public class BoomTurret {
    public static ItemTurret boomturret;

    public static void load(){
        var boomBullet = new BasicBulletType(10f, 100f){{
            width = 25f;
            height = 25f;
            lifetime = 300f;
            homingPower = 2f;
            homingRange = 320f;
            keepVelocity = false;
            trailEffect = Fx.missileTrail;
            backColor = Color.gray;
            frontColor = Color.valueOf("ff8844");
            hitEffect = Fx.hitFlameBeam;
            despawnEffect = Fx.massiveExplosion;
            splashDamage = 500f;
            splashDamageRadius = 250f;
            collidesAir = true;
            collidesGround = true;
        }};



        // 定義砲塔本體
        boomturret = new ItemTurret("boom-turret"){ {
            requirements(Category.turret, ItemStack.with(copper, 100, silicon, 80, titanium, 60));
            localizedName = "Boom Turret";
            description = "Fires boom bullet toward enemies.";
            size = 3;
            health = 480;
            reload = 70f;
            range = 600f;
            inaccuracy = 0f;
            shootSound = Sounds.missile;
            ammoUseEffect = Fx.casing2;
            shootCone = 2f;

            // 使用 silicon 作為彈藥
            ammo(silicon, boomBullet);

            rotateSpeed = 4f;
            targetAir = true;
            targetGround = true;
            coolantMultiplier = 0.6f;
            drawer = new DrawTurret();
            envEnabled |= Env.any;
        } };
    }
}