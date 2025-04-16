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
        // 定義一種智能追蹤導彈子彈
        // 智能導彈子彈
        BasicBulletType homingBullet = new BasicBulletType(3f, 45f);
        homingBullet.width = 8f;
        homingBullet.height = 12f;
        homingBullet.lifetime = 100f;
        homingBullet.homingPower = 0.2f;
        homingBullet.homingRange = 160f;
        homingBullet.keepVelocity = false;
        homingBullet.trailEffect = Fx.missileTrail;
        homingBullet.backColor = Color.gray;
        homingBullet.frontColor = Color.valueOf("ff8844");
        homingBullet.hitEffect = Fx.massiveExplosion;
        homingBullet.despawnEffect = Fx.massiveExplosion;
        homingBullet.splashDamage = 120f;
        homingBullet.splashDamageRadius = 32f;
        homingBullet.collidesAir = true;
        homingBullet.collidesGround = true;


        // 定義砲塔本體
        boomturret = new ItemTurret("homing-array"){ {
            requirements(Category.turret, ItemStack.with(copper, 100, silicon, 80, titanium, 60));
            localizedName = "Homing Array";
            description = "Fires self-guided missiles that track down enemies.";
            size = 3;
            health = 480;
            reload = 70f;
            range = 160f;
            inaccuracy = 0f;
            shootSound = Sounds.missile;
            ammoUseEffect = Fx.casing2;
            shootCone = 2f;

            // 使用 silicon 作為彈藥
            ammo(silicon, homingBullet);

            rotateSpeed = 4f;
            targetAir = true;
            targetGround = true;
            coolantMultiplier = 0.6f;
            drawer = new DrawTurret();
            envEnabled |= Env.any;
        } };
    }
}
