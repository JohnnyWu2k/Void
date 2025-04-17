package mymod.content.blocks;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Env;
import mymod.content.ModItems;

import static mindustry.content.Items.*;

public class VoidPiercer {
    public static ItemTurret voidPiercer;

    public static void load(){
        // 暗物質彈藥
        var darkMatterBullet = new LaserBulletType(1000f) {{
            lifetime = 200f;
            pierce = true;
            pierceBuilding = true;
            collides       = true;
            hittable       = true;
            collidesAir    = true;
            collidesGround = true;
            pierceCap = 60;
            width = 12f;
            hitEffect = Fx.hitLaser;
            shootEffect = Fx.lancerLaserShoot;
            smokeEffect = Fx.none;
            trailEffect = Fx.lancerLaserShootSmoke;
            buildingDamageMultiplier = 0.8f;
            ammoMultiplier = 1f;
        }};

        // 鈦彈
        var titaniumBullet = new LaserBulletType(700f) {{
            lifetime = 40f;
            pierce = true;
            pierceBuilding = true;
            collides       = true;
            hittable       = true;
            collidesAir    = true;
            collidesGround = true;
            pierceCap = 2;
            width = 10f;
            hitEffect = Fx.hitBulletBig;
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke;
            trailEffect = Fx.railTrail;
            buildingDamageMultiplier = 0.6f;
            ammoMultiplier = 2f;
        }};

        // 定義暗物質炮
        voidPiercer = new ItemTurret("void-piercer") {{
            requirements(Category.turret, ItemStack.with(titanium, 250, silicon, 180, surgeAlloy, 40, graphite, 120));
            localizedName = "暗物質炮";
            description   = "利用電磁加速技術轉換暗物質成超強能量光束";
            health        = 1600;
            size          = 4;
            reload        = 80f;
            inaccuracy    = 0f;
            rotateSpeed   = 3f;
            range         = 600f;
            shake         = 4f;
            targetAir     = true;
            targetGround  = true;


            // 彈藥配置
            ammo(ModItems.darkmatter, darkMatterBullet); //-->待修正
            ammo(Items.titanium, titaniumBullet);

            // 外觀與環境設定
            drawer = new DrawTurret();
            envEnabled |= Env.any;
        }};
    }
}
