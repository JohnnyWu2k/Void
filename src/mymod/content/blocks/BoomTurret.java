package mymod.content.blocks;

import arc.graphics.Color;
import arc.util.Log;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Env;

import static mindustry.content.Items.*;

public class BoomTurret {
    public static ItemTurret boomturret;

    public static void load(){
        // 1. 定義導彈子彈
        var missileBullet = new BasicBulletType(8f, 120f){{
            // 使用自家準備的導彈圖片，後面要放 assets/sprites/bullets/rocket-missile.png
            sprite     = "missile";


            // 2. 大小調整
            width  = 15f;
            height = 15f;

            lifetime    = 400f;      // 飛行壽命長一點
            homingPower = 2f;      // 輕微追蹤
            homingRange = 200f;

            // 3. 讓子彈轉向其行進方向

            // 4. 特效與音效
            trailEffect    = Fx.missileTrail;      // 給它火焰尾跡
            shootEffect    = Fx.casing3;
            hitEffect      = Fx.hitFlameBeam;
            despawnEffect  = Fx.massiveExplosion;
            splashDamage        = 500f;
            splashDamageRadius  = 250f;
            collidesAir    = true;
            collidesGround = true;
            ammoMultiplier = 8f;
        }};

        // 既有的普通彈藥
        var siliconBullet = new BasicBulletType(6f, 60f){{
            // …你的既有設定
        }};

        // 定義砲塔本體
        boomturret = new ItemTurret("boom-turret"){{
            Log.info("BoomTurret: register boom-turret");
            requirements(Category.turret,
                    ItemStack.with(
                            copper,  100,
                            silicon,  80,
                            titanium, 60
                    )
            );

            localizedName = "Boom Turret";
            description   = "Fires missile-like projectiles.";
            size          = 3;
            health        = 480;
            reload        = 35f;
            range         = 600f;
            inaccuracy    = 0f;
            rotateSpeed   = 4f;
            shootSound    = Sounds.missile;   // 導彈發射音
            ammoUseEffect = Fx.casing2;
            shootCone     = 2f;

            targetAir     = true;
            targetGround  = true;
            envEnabled   |= Env.any;

            drawer = new DrawTurret();

            // 把剛剛的導彈當作彈藥

            // 也可以保留原本的 silicon 彈
            ammo(silicon,   missileBullet);
        }};
    }
}
