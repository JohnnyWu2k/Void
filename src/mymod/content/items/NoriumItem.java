// 假設放在 items 子包下
package mymod.content.items; // 或者 package mymod.content; 如果不放子包

import arc.graphics.Color;
import mindustry.type.Item;
import mindustry.content.Fx; // 需要導入爆炸效果
import mindustry.entities.Damage; // 需要導入傷害工具



// 這是 Norium 物品的定義類別
public class NoriumItem extends Item {

    // --- 在這裡定義爆炸相關的常數 ---
    /**
     * 物品掉落後多少 tick (遊戲幀) 開始爆炸 (60 ticks ≈ 1 秒)
     */
    private static final float explosionDelay = 60f * 5f; // 例如：5 秒後爆炸
    /**
     * 爆炸半徑 (像素)
     */
    private static final float explosionRadius = 5f * 8f; // 例如：5 個格子寬 (5 * tilesize)
    /**
     * 爆炸傷害
     */
    private static final float explosionDamage = 50f;    // 例如：造成 50 點傷害

    public NoriumItem(String name) {
        // 調用父類別 Item 的構造函數，傳入內部名稱
        super(name);

        // --- 在這裡定義 Norium 的所有屬性和特徵 ---
        // 基本顯示
        this.localizedName = "Norium"; // 遊戲內名稱 (推薦使用 bundles/bundle.properties 進行本地化)
        this.description = "A custom radioactive material."; // 物品描述 (推薦使用 bundles)
        this.color = Color.valueOf("88aaff"); // 物品顏色 (淡藍色)

        // 物理/遊戲屬性
        this.hardness = 2;            // 挖掘硬度 (例如，比銅硬)
        this.cost = 1.5f;             // 基礎成本，影響建造速度等
        this.radioactivity = 0.6f;    // 具有放射性
        this.buildable = true;        // 可以用於建築

        // 其他可以設定的屬性 (可以保持預設值 0 或 false)
        // this.flammability = 0.0f;
        // this.explosiveness = 0.0f;
        // this.charge = 0.0f;
        // this.lowPriority = false; // 鑽頭是否優先挖掘

        // 如果需要動畫 (通常在 Hjson 設定)
        // this.frames = 0;
        // this.frameTime = 5f;
        // this.transitionFrames = 0;
    }
}

    // 如果需要覆寫 Item 的其他行為，可以在這裡添加方法
    // 例如:
    // @Override
    // public void update(ItemEntity entity){
    //     // 當物品在地上時的特殊邏輯 (很少用到)
    // }
    /**
     * 這個方法會在代表 Norium 掉落在地上的每個 ItemEntity 每一幀被調用。
     * @param entity 代表掉落在地上的 Norium 的實體。
     */
