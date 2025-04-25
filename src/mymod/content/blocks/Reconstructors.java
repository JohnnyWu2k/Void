package mymod.content.blocks;

import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.units.UnitFactory;
import mymod.content.units.Delta;
import mymod.content.items.*;
import mindustry.world.blocks.units.UnitFactory.UnitPlan;

import static mindustry.content.Items.*;

public class Reconstructors {
    public static UnitFactory deltaFactory;
//要用的是MultiplicativeReconstructor，但還沒找到
    public static void load() {
        deltaFactory = new UnitFactory("delta-factory") {{
            requirements(Category.units, ItemStack.with(DarkMatter.darkmatter, 20, AntiMatter.antimatter, 12));
            size=2;
            health=3000;

            // Tier1 → Tier2
            plans.add(new UnitPlan(Delta.delta,        // 輸入
                    240f,              // 耗時（4 秒）
                    ItemStack.with(DarkMatter.darkmatter, 200, AntiMatter.antimatter, 120)));

            // Tier2 → Tier3
            plans.add(new UnitPlan(Delta.delta2,
                    360f,
                    ItemStack.with(DarkMatter.darkmatter, 400, AntiMatter.antimatter, 360)));
        }};
    }
}
