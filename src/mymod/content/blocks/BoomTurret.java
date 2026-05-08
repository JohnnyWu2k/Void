package mymod.content.blocks;

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
        var pyratiteMissile = new BasicBulletType(4.8f, 95f){{
            width  = 15f;
            height = 15f;
            lifetime    = 64f;
            homingPower = 0.08f;
            homingRange = 120f;
            sprite = "void-missileBullet";
            trailEffect    = Fx.missileTrail;
            shootEffect    = Fx.casing3;
            hitEffect      = Fx.hitFlameBeam;
            despawnEffect  = Fx.massiveExplosion;
            splashDamage        = 85f;
            splashDamageRadius  = 42f;
            collidesAir    = true;
            collidesGround = true;
            ammoMultiplier = 3f;
        }};

        var blastMissile = new BasicBulletType(4.4f, 145f){{
            width  = 18f;
            height = 18f;
            lifetime    = 72f;
            homingPower = 0.06f;
            homingRange = 140f;
            sprite = "void-missileBullet";
            trailEffect    = Fx.missileTrail;
            shootEffect    = Fx.casing3;
            hitEffect      = Fx.blastExplosion;
            despawnEffect  = Fx.massiveExplosion;
            splashDamage        = 155f;
            splashDamageRadius  = 58f;
            collidesAir    = true;
            collidesGround = true;
            ammoMultiplier = 2f;
        }};

        boomturret = new ItemTurret("boom-turret"){{
            requirements(Category.turret,
                    ItemStack.with(
                            copper,  100,
                            silicon,  80,
                            titanium, 60,
                            pyratite, 40
                    )
            );

            size          = 3;
            health        = 780;
            reload        = 72f;
            range         = 300f;
            inaccuracy    = 0f;
            rotateSpeed   = 4f;
            shootSound    = Sounds.shootMissile;
            ammoUseEffect = Fx.casing2;
            shootCone     = 2f;

            targetAir     = true;
            targetGround  = true;
            envEnabled   |= Env.any;

            drawer = new DrawTurret();
            ammo(
                    pyratite, pyratiteMissile,
                    blastCompound, blastMissile
            );
        }};
    }
}
