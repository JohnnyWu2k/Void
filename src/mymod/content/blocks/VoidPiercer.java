package mymod.content.blocks;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Env;
import mymod.content.items.DarkMatter;


public class VoidPiercer {
    public static ItemTurret voidPiercer;

    public static void load() {
        var darkMatterBullet = new LaserBulletType(720f) {{
            length         = 430f;
            lifetime       = 70f;
            pierce         = true;
            pierceBuilding = true;
            collides       = true;
            hittable       = true;
            collidesAir    = true;
            collidesGround = true;
            pierceCap      = 8;
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
                    ItemStack.with(DarkMatter.darkmatter, 250,
                            Items.silicon, 180,
                            Items.surgeAlloy, 60,
                            Items.graphite, 120
                    )
            );
            health        = 1600;
            size          = 4;
            reload        = 80f;
            inaccuracy    = 0f;
            rotateSpeed   = 3f;
            range         = 430f;
            shake         = 4f;
            targetAir     = true;
            targetGround  = true;
            ammoPerShot = 6;
            ammo(DarkMatter.darkmatter, darkMatterBullet);

            drawer = new DrawTurret();
            envEnabled |= Env.any;
        }};
    }
}
