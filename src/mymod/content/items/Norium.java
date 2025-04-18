package mymod.content.items;

import arc.Events;
import arc.util.Log;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.EntityGroup;      // ← 物品實體的類別
import mindustry.game.EventType;
import mindustry.gen.Groups;                     // ← 包含 all、unit、bullet…等群組
import mindustry.type.Item;

public class Norium {
    public static Item norium;


    public static void load() {
        Log.info("NoriumContent: registering norium with explosive drop");
        norium = new Item("norium") {{
            localizedName = "Norium";
            description = "A custom radioactive material that explodes on drop.";
            color = Color.valueOf("88aaff");
            hardness = 2;
            cost = 1.5f;
            radioactivity = 0.6f;
            buildable = true;
        }};
    }
}




