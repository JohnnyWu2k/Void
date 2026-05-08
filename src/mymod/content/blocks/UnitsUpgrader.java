package mymod.content.blocks;

import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.units.Reconstructor;
import mymod.content.items.AntiMatter;
import mymod.content.items.DarkMatter;
import mymod.content.units.Delta;


public class UnitsUpgrader {
    public static Reconstructor UnitsUpgrader;

    public static void load() {
        UnitsUpgrader = new Reconstructor("up") {{
            requirements(Category.units,
                    ItemStack.with(DarkMatter.darkmatter, 140, AntiMatter.antimatter, 70)
            );

            size   = 4;
            health = 1800;

            addUpgrade(Delta.delta, Delta.delta2);
            consumePower(10f);
            consumeItems(
                    ItemStack.with(DarkMatter.darkmatter, 260, AntiMatter.antimatter, 120)
            );
            constructTime = 2400f;
        }};
    }
}

