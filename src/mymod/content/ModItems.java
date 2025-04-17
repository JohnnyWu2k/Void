package mymod.content; // 把這個類別放在 content 子包下，保持整潔

import mindustry.type.Item;
// 導入你之後會創建的具體物品類別，例如 NoriumItem
import mymod.content.items.*;
// 假設你把物品類別放在 items 子包


public class ModItems {

    // --- 在這裡宣告你的所有自訂物品 ---
    // 使用 public static Item 宣告變數
    public static Item norium;
    public static Item darkmatter;
    // public static Item anotherCoolItem; // 如果有更多物品...

    // --- 創建一個靜態的 load 方法 ---
    // 這個方法將在 MyMod 的 loadContent() 中被調用
    public static void load() {

        // --- 在這裡實例化你的物品 ---
        // 使用 new 關鍵字創建你在其他 Java 檔案中定義的物品類別的實例
        // "norium" 是內部名稱，需要和 Hjson 檔案名或 Hjson 中的 name 對應
        norium = new NoriumItem("norium"); // <--- 你需要先創建 NoriumItem.java 這個檔案！

        // anotherCoolItem = new AnotherCoolItem("another-cool-item");
         darkmatter = new DarkMatter("dark-matter");

        // 你可以在這裡立即設置一些 Java 屬性，如果它們不在物品自己的類別中設定的話
        // 例如: norium.hardness = 3; (但不推薦，最好在 NoriumItem 類別裡設定)
    }

    // --- (額外補充) 如果你想把物品的 Java 類別也定義在這裡 ---
    // 對於非常簡單的物品，可以這樣做，但不推薦用於複雜物品或多個物品
    /*
    public static class NoriumItem extends Item {
        public NoriumItem(String name) {
            super(name);
            // 在這裡設定屬性...
            this.color = arc.graphics.Color.valueOf("88aaff");
            this.hardness = 2;
            // ...
        }
    }
    */
    // 更好的做法是為 NoriumItem 創建一個單獨的 .java 檔案 (如之前的範例)

}