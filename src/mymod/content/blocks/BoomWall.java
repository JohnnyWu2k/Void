package mymod.content.blocks;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.Damage;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;

public class BoomWall {
    public static Wall boomwall;

    public static void load() {
        boomwall = new Wall("boom-wall") {{
            requirements(Category.defense, BuildVisibility.shown, ItemStack.with(Items.titanium, 30));
            localizedName = "Bomb Wall";
            description = "A wall that explodes after a short time.";
            health = 2500;
            size = 2;
            update = true;
            buildCostMultiplier = 2f;

            // 將 buildType 欄位賦值為自訂的建築邏輯
            buildType = () -> new Building() {
                int timer = 0;

                @Override
                public void updateTile(){
                    timer++;
                    if(timer > 60){ // 每 60 tick 約1秒鐘
                        timer = 0;
                        Fx.blastsmoke.at(x, y); // 爆炸煙霧特效
                        Fx.explosion.at(x, y);  // 爆炸光效
                        Damage.damage(x, y, 20f, 100f); // 根據爆炸武器的寫法施加範圍傷害
                        kill(); // 爆炸後自我銷毀
                    }
                }
            };
        }};
}}
