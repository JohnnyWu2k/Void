package mymod.content;

import arc.graphics.Color;
import arc.util.Log;
import mindustry.type.Item;
import mymod.content.items.*;

public class ModItems {
    public static void load() {
        DarkMatter.load();
        Norium.load();
        AntiMatter.load();
        VoidCrystal.load();
    }
}
