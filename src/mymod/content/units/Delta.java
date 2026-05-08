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
            health=850f;
            speed=3.6f;
            armor=6f;
            flying=true;
            rotateSpeed=7f;
            weapons.add(new Weapon("delta weapon"){{
                bullet=new LaserPhases.PhaseOneBeam();
                rotateSpeed=8f;
                rotate=true;
                reload=30f;
                trailColor= Color.red;
            }});
            buildRange=100f;
            buildSpeed=1.2f;
            constructor= UnitEntity::create;
        }};

        delta2 = new UnitType("delta2"){{
            hitSize=20f;
            health=3000f;
            speed=4.6f;
            armor=16f;
            flying=true;
            rotateSpeed=6f;
            weapons.add(new Weapon("delta2 weapon"){{
                bullet=new LaserPhases.PhaseTwoBeam();
                rotateSpeed=8f;
                rotate=true;
                reload=18f;
            }});
            buildRange=140f;
            buildSpeed=1.8f;
            constructor= UnitEntity::create;
        }};
    }
}
