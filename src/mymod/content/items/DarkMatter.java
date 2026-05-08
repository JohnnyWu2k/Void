package mymod.content.items;

import arc.graphics.Color;
import mindustry.type.Item;

public class DarkMatter {
    public static Item darkmatter;

    public static void load() {
        darkmatter = new Item("darkmatter") {{
            color         = Color.valueOf("2D2B55");
            hardness      = 2;
            cost          = 1.5f;
            radioactivity = 0.6f;
            buildable     = true;
        }};
    }
}
