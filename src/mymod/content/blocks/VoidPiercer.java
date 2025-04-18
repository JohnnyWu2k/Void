package mymod.content.blocks;

import arc.util.Log;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Env;
import mymod.content.ModItems;
import mymod.content.items.DarkMatter;


public class VoidPiercer {
    public static ItemTurret voidPiercer;

    public static void load() {
        var darkMatterBullet = new LaserBulletType(1000f) {{
            lifetime       = 200f;
            pierce         = true;
            pierceBuilding = true;
            collides       = true;
            hittable       = true;
            collidesAir    = true;
            collidesGround = true;
            pierceCap      = 60;
            width          = 12f;
            hitEffect      = Fx.hitLaser;
            shootEffect    = Fx.lancerLaserShoot;
            smokeEffect    = Fx.none;
            trailEffect    = Fx.lancerLaserShootSmoke;
            buildingDamageMultiplier = 0.8f;
            ammoMultiplier = 1f;
        }};
        voidPiercer = new ItemTurret("void-piercer") {{
            requirements(Category.turret,
                    ItemStack.with(DarkMatter.darkmatter, 250,      // ← 直接用靜態匯入的 darkmatter
                            Items.silicon, 180,
                            Items.surgeAlloy, 40,
                            Items.graphite, 120
                    )
            );
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
            ammoPerShot = 6;
            // 彈藥配置
            // ammo好像不支援模組物品匯入
            ammo(DarkMatter.darkmatter, darkMatterBullet);


            // 外觀與環境設定
            drawer = new DrawTurret();
            envEnabled |= Env.any;
        }};
    }
}
