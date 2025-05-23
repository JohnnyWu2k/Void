package mymod.content.blocks;

import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;
import mymod.content.units.Delta;
import mymod.content.items.*;
import mindustry.world.blocks.units.UnitFactory.UnitPlan;



public class UnitsCrafter {
    public static UnitFactory UnitsFactory;
//要用的是MultiplicativeReconstruct，但還沒找到
    public static void load() {
        UnitsFactory = new UnitFactory("uc") {{
            requirements(Category.units, ItemStack.with(DarkMatter.darkmatter, 20, AntiMatter.antimatter, 12));
            localizedName="單位製造器";
            description="可以製各個單位的第一階段";
            size=3;
            health=1000;
            // Tier1 → Tier2
            plans.add(new UnitPlan(Delta.delta,        // 輸入
                    240f,              // 耗時（4 秒）
                    ItemStack.with(DarkMatter.darkmatter, 200, AntiMatter.antimatter, 120)));
        }};
    }
}
