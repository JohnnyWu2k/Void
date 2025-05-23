package mymod.content.units;

import arc.graphics.Color;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mymod.content.bullets.*;

public class Delta {
    public static UnitType delta,delta2;
    public static void load(){
        delta = new UnitType("delta"){{
            hitSize=10f;
            health=1000f;
            speed=6f;
            armor=10f;
            flying=true;
            rotateSpeed=10f;
            weapons.add(new Weapon("delta weapon"){{
                bullet=new LaserPhases.PhaseOneBeam();
                rotateSpeed=20f;
                rotate=true;
                reload=20f;
                trailColor= Color.red;
            }});
            buildRange=300f;
            buildSpeed=100f;
            constructor= UnitEntity::create;
        }};

        delta2 = new UnitType("delta2"){{
            hitSize=20f;
            health=3000f;
            speed=20f;
            armor=1000f;
            flying=true;
            rotateSpeed=100f;
            weapons.add(new Weapon("delta2 weapon"){{
                bullet=new LaserPhases.PhaseTwoBeam();
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
