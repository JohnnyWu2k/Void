package mymod.content.units;

import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mymod.content.bullets.BlackHoleBullet;

public class Delta {
    public static UnitType delta,delta2;
    public static void load(){
        var bh=new BlackHoleBullet();
        delta = new UnitType("delta"){{
            hitSize=20f;
            health=1000f;
            speed=3f;
            armor=10f;
            flying=true;
            rotateSpeed=10f;
            weapons.add(new Weapon("delta weapon"){{
                bullet=bh;
                rotateSpeed=20f;
                rotate=true;
                reload=75f;
            }});
            buildRange=300f;
            buildSpeed=100f;
            constructor= UnitEntity::create;
        }};

        delta2 = new UnitType("delta2"){{
            hitSize=40f;
            health=10000f;
            speed=30f;
            armor=1000f;
            flying=true;
            rotateSpeed=100f;
            weapons.add(new Weapon("delta2 weapon"){{
                bullet=bh;
                rotateSpeed=200f;
                rotate=true;
                reload=7f;
            }});
            buildRange=3000f;
            buildSpeed=1000f;
            constructor= UnitEntity::create;
        }};
    }
}
