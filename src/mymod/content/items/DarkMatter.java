package mymod.content.items;

import arc.graphics.Color;
import mindustry.type.Item;

public class DarkMatter extends Item {
    public DarkMatter(String name) {
        // 調用父類別 Item 的構造函數，傳入內部名稱
        super(name);

        // --- 在這裡定義 Norium 的所有屬性和特徵 ---
        // 基本顯示
        this.localizedName = "暗物質"; // 遊戲內名稱 (推薦使用 bundles/bundle.properties 進行本地化)
        this.description = "不受電磁力，卻受重力作用，可以讓許多星體能夠保持結構的物質。"; // 物品描述 (推薦使用 bundles)
        this.color = Color.valueOf("2D2B55"); // 物品顏色 (淡藍色)

        // 物理/遊戲屬性
        this.hardness = 2;            // 挖掘硬度 (例如，比銅硬)
        this.cost = 1.5f;             // 基礎成本，影響建造速度等
        this.radioactivity = 0.6f;    // 具有放射性
        this.buildable = true;        // 可以用於建築

    }
}
