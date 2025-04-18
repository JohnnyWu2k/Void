// 假設放在 items 子包下
package mymod.content.items; // 或者 package mymod.content; 如果不放子包

import arc.graphics.Color;
import mindustry.type.Item;


// 這是 Norium 物品的定義類別
public class Norium {
    public static Item norium;
    
    public static void load(){
        norium = new Item("norium"){{
            localizedName = "Norium"; // 遊戲內名稱 (推薦使用 bundles/bundle.properties 進行本地化)
            description = "A custom radioactive material."; // 物品描述 (推薦使用 bundles)
            color = Color.valueOf("88aaff"); // 物品顏色 (淡藍色)

            // 物理/遊戲屬性
            hardness = 2;            // 挖掘硬度 (例如，比銅硬)
            cost = 1.5f;             // 基礎成本，影響建造速度等
            radioactivity = 0.6f;    // 具有放射性
            buildable = true;
        }};
    }}
    
  
