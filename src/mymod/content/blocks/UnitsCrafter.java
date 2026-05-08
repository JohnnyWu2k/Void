package mymod.content.blocks;

import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.units.UnitFactory;
import mymod.content.units.Delta;
import mymod.content.items.*;
import mindustry.world.blocks.units.UnitFactory.UnitPlan;



public class UnitsCrafter {
    public static UnitFactory UnitsFactory;

    public static void load() {
        UnitsFactory = new UnitFactory("uc") {{
            requirements(Category.units, ItemStack.with(DarkMatter.darkmatter, 80, AntiMatter.antimatter, 30));
            size=3;
            health=1000;
            plans.add(new UnitPlan(Delta.delta,
                    1800f,
                    ItemStack.with(DarkMatter.darkmatter, 120, AntiMatter.antimatter, 35)));
        }};
    }
}
