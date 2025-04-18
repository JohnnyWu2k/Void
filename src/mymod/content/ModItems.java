package mymod.content;

import arc.util.Log;
import mindustry.type.Item;
import mymod.content.items.*;

public class ModItems {
    public static Item norium;
    public static void load() {
        Log.info("ModItems: register darkmatter");
        Norium.load();
        DarkMatter.load();// 以後要加更多物品，直接在這裡寫就好
    }
}
