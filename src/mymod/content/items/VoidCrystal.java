package mymod.content.items;

import mindustry.type.Item;

public class VoidCrystal {
    public static Item voidcrystal;

    public static void load(){
        voidcrystal = new Item("void-crystal"){{
            explosiveness=2f;
            charge=0.8f;
            radioactivity=1f;
            cost=3f;
            hardness=4;
            buildable = true;
        }};
    }
}
