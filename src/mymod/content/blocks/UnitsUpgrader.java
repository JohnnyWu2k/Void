package mymod.content.blocks;

import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.units.Reconstructor;
import mymod.content.items.AntiMatter;
import mymod.content.items.DarkMatter;
import mymod.content.units.Delta;


import static mindustry.content.Items.*;

public class UnitsUpgrader {
    public static Reconstructor UnitsUpgrader;

    public static void load() {
        UnitsUpgrader = new Reconstructor("up") {{
            // 建造升級機成本（UI 上 Requirements）

            requirements(Category.units,
                    ItemStack.with(DarkMatter.darkmatter, 50, AntiMatter.antimatter, 50)
            );


            localizedName = "單位升級器";
            description = "升級模組內的一階單位";

            // 方塊大小 & 耐久（不寫預設 size=1、health=base）
            size   = 4;
            health = 1800;

            // ■ 升級設定 ■
            // 用 addUpgrade(輸入單位, 輸出單位)
            addUpgrade(Delta.delta,      // Tier-1 →
                    Delta.delta2); // Tier-2

            // 升級消耗：電力／物品／時間
            consumePower(10f);


            consumeItems(
                    ItemStack.with(DarkMatter.darkmatter,500,AntiMatter.antimatter,500)
            );
            constructTime = 180f;  // 用 constructTime 而非 consumeTime
        }};
    }
}

