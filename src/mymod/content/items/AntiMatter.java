package mymod.content.items;

import mindustry.type.Item;

public class AntiMatter {
    public static Item antimatter;

    public static void load(){
        antimatter = new Item("anti-matter"){{
            explosiveness = 3f;
            charge = 1.2f;
            cost = 4f;
        }};
    }
}
