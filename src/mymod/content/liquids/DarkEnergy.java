package mymod.content.liquids;

import arc.graphics.Color;
import mindustry.type.Liquid;

public class DarkEnergy {
    public static Liquid darkEnergy;

    public static void load(){
        darkEnergy = new Liquid("dark-energy", Color.valueOf("1a154f")){{
            viscosity = 0.88f;
            temperature = 0.18f;
            heatCapacity = 1.35f;
            explosiveness = 0.25f;
            lightColor = Color.valueOf("5b35ff66");
            barColor = Color.valueOf("7257ff");
            coolant = true;
        }};
    }
}
