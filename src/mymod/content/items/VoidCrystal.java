package mymod.content.items;

import mindustry.type.Item;

public class VoidCrystal {
    public static Item voidcrystal;

    public static void load(){
        voidcrystal = new Item("void-crystal"){{
            localizedName="暗能量";
            description="不受電磁力和重力作用，卻是宇宙中佔據質能最多的能量，具有均勻的負壓力。";
            explosiveness=2f;
            charge=0.8f;
            radioactivity=1f;
            cost=3f;
            hardness=4;
        }};
    }
}
