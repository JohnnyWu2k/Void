package mymod.content.blocks;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Damage;
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

public class SmartTurret {
    public static ItemTurret smartTurret;

    public static void load(){
        var smartBullet = new BasicBulletType(4f, 0f){
            {
                lifetime = 320f;
                width = 12f;
                height = 12f;
                splashDamage = 70f;
                splashDamageRadius = 60f;
                backColor = Color.valueOf("ff6347");
                frontColor = Color.white;
                trailEffect = Fx.bubble;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;
                pierce = true;
                pierceCap = 1;
                collidesAir = true;
                collidesGround = true;
                keepVelocity = false;
                hittable = false; // 讓子彈不被打爆

            }

            @Override
            public void update(Bullet b){
                // 子弹初始阶段保持直飞
                float homingDelay = 30f;
                if(b.time < homingDelay) {
                    if(b.time % 3 < 1){
                        Fx.smoke.at(b.getX(), b.getY());
                    }
                    return;
                }

                // 搜索范围及目标锁定
                float searchRange = 300f; // 可根据需要调整
                Unit target = Groups.unit.find(u ->
                        !u.dead && u.team != b.team && u.within(b.getX(), b.getY(), searchRange)
                );

                if(target != null) {
                    // 当前子弹运动方向
                    float currentAngle = b.vel.angle();
                    // 目标方向：从子弹所在位置指向目标中心
                    float targetAngle = Angles.angle(b.getX(), b.getY(), target.getX(), target.getY());

                    // 手动计算角度差，确保处理360度环绕
                    float diff = targetAngle - currentAngle;
                    if(diff < -180) diff += 360;
                    if(diff > 180) diff -= 360;

                    // 插值因子：你可以调整 turnSpeed 和 dt 的数值来优化转向平滑度
                    float turnSpeed = 4f;
                    float dt = 0.02f; // 这里作为一个常量，用于模拟时间因子，实际效果可调

                    // 计算新的角度
                    float newAngle = currentAngle + diff * (turnSpeed * dt);
                    b.vel.setAngle(newAngle);
                }

                // 播放轨迹特效（模拟 trailSpacing 效果）
                if(b.time % 4 < 1){
                    Fx.smoke.at(b.getX(), b.getY());
                }

                // 超过一定存活时间后自爆
                if(b.time > 90f){
                    Damage.damage(b.getX(), b.getY(), 45f, 80f);
                    Fx.blastExplosion.at(b.getX(), b.getY());
                    b.remove();
                }
            }
        };

        smartTurret = new ItemTurret("smart-turret"){{
            localizedName = "Smart Turret";
            description = "Launches explosive drones that orbit enemies and detonate after a delay.";
            requirements(Category.turret, ItemStack.with(silicon, 100, titanium, 80, plastanium, 60));
            size = 3;
            health = 600;
            reload = 1f;
            range = 3000f;
            shootCone = 10f;
            rotateSpeed = 10f;
            coolantMultiplier = 0.6f;
            targetAir = true;
            targetGround = true;
            shootSound = Sounds.explosion;
            shootEffect = Fx.shootBig;
            inaccuracy = 8f;   // 稍微分散
            drawer = new DrawTurret();
            ammo(silicon, smartBullet);
            envEnabled |= Env.any;
        }};
    }
}
