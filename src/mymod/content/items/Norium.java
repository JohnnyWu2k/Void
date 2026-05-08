package mymod.content.items;

import arc.graphics.Color;
import mindustry.type.Item;

public class Norium {
    public static Item norium;


    public static void load() {
        norium = new Item("norium") {{
            color = Color.valueOf("88aaff");
            hardness = 2;
            cost = 1.5f;
            radioactivity = 0.6f;
            buildable = true;
        }};
    }
}




