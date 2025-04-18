package mymod.content.items;

import arc.graphics.Color;
import mindustry.type.Item;

public class DarkMatter {
    public static Item darkmatter;

    public static void load() {
        darkmatter = new Item("darkmatter") {{
            // --- 基本顯示 ---
            localizedName = "暗物質";
            description   = "不受電磁力，卻受重力作用，可以讓許多星體能夠保持結構的物質。";
            color         = Color.valueOf("2D2B55");
            // --- 物理／遊戲屬性 ---
            hardness      = 2;      // 挖掘硬度
            cost          = 1.5f;   // 基礎成本
            radioactivity = 0.6f;   // 放射性
            buildable     = true;   // 可用於建築
        }};
    }
}
