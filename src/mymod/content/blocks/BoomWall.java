package mymod.content.blocks;

import arc.struct.ObjectSet;
import arc.util.Time;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.Damage;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Stat;

public class BoomWall {
    public static Wall boomwall,smallboomwall;

    public static void load(){
        boomwall = new Wall("boom-wall"){{
            // 基本設定
            requirements(Category.defense, BuildVisibility.shown, ItemStack.with(Items.blastCompound, 30));
            localizedName       = "Bomb Wall";
            description         = "啟用後可設定延遲秒數自爆，並連鎖爆炸相鄰牆體";
            health              = 250;
            size                = 2;
            update              = true;
            buildCostMultiplier = 2f;
            stats.add(Stat.damage, 150f);

            // 支援玩家點擊與 Logic Processor 設定
            configurable        = true;
            logicConfigurable   = true;

            buildType = () -> new Building(){
                boolean enabled   = false;  // 是否啟用自爆
                float   delayTime = 0f;      // 延遲秒數
                float   timer     = 0f;

                @Override
                public void buildConfiguration(Table table){
                    table.button(enabled ? "Disable" : "Enable", () -> {
                        enabled = !enabled;
                        configure(new float[]{ enabled ? 1f : 0f, delayTime });
                    }).size(120f, 50f);
                    table.row();
                    // 顯示當前延遲秒數
                    table.label(() -> String.format("Delay: %.1f s", delayTime/50)).padTop(8f).row();
                    // 滑桿設定，自動更新 delayTime
                    table.slider(0f, 1000f, 10f, v -> {
                        delayTime = v;
                        configure(new float[]{ enabled ? 1f : 0f, delayTime });
                    }).prefWidth();
                }

                @Override
                public void configure(Object value){
                    if(value instanceof float[] arr && arr.length == 2){
                        enabled   = arr[0] == 1f;
                        delayTime = arr[1];
                    }
                }

                @Override
                public void update(){
                    super.update();
                    if(!enabled) return;
                    timer += Time.delta;
                    if(timer >= delayTime){
                        timer -= delayTime;
                        explode();
                    }
                    if(health == 0){
                        explode();
                    }
                }
                @Override
                public void kill(){
                    // 只要被殺死（health=0 或 kill() 被呼），並且啟用，就爆炸
                    if(enabled){
                        explode();
                    }
                    super.kill();
                }

                @Override
                public void damage(float amount){
                    float prev = health;       // 扣血前的生命值
                    super.damage(amount);      // 真正執行扣血邏輯
                    // 正好從活著到死亡，且功能開啟，觸發爆炸
                    if(prev > 0 && health <= 0){
                        explode();
                    }
                }



                // 統一爆炸邏輯
                private void explode(){
                    Fx.blastsmoke.at(x, y);
                    Fx.explosion.at(x, y);
                    Damage.damage(x, y, 100f, 300f);
                    Sounds.explosion.at(x, y);
                }
            };
        }};


        smallboomwall = new Wall("small-boom-wall"){{
            // 基本設定
            requirements(Category.defense, BuildVisibility.shown, ItemStack.with(Items.blastCompound, 30));
            localizedName       = "Small Bomb Wall";
            description         = "啟用後可設定延遲秒數自爆，並連鎖爆炸相鄰牆體";
            health              = 100;
            size                = 1;
            update              = true;
            buildCostMultiplier = 2f;
            stats.add(Stat.damage, 150f);

            // 支援玩家點擊與 Logic Processor 設定
            configurable        = true;
            logicConfigurable   = true;

            buildType = () -> new Building(){
                boolean enabled   = false;  // 是否啟用自爆
                float   delayTime = 0f;      // 延遲秒數
                float   timer     = 0f;

                @Override
                public void buildConfiguration(Table table){
                    table.button(enabled ? "Disable" : "Enable", () -> {
                        enabled = !enabled;
                        configure(new float[]{ enabled ? 1f : 0f, delayTime });
                    }).size(120f, 50f);
                    table.row();
                    // 顯示當前延遲秒數
                    table.label(() -> String.format("Delay: %.1f s", delayTime/50)).padTop(8f).row();
                    // 滑桿設定，自動更新 delayTime
                    table.slider(0f, 1000f, 10f, v -> {
                        delayTime = v;
                        configure(new float[]{ enabled ? 1f : 0f, delayTime });
                    }).prefWidth();
                }

                @Override
                public void configure(Object value){
                    if(value instanceof float[] arr && arr.length == 2){
                        enabled   = arr[0] == 1f;
                        delayTime = arr[1];
                    }
                }

                @Override
                public void update(){
                    super.update();
                    if(!enabled) return;
                    timer += Time.delta;
                    if(timer >= delayTime){
                        timer -= delayTime;
                        explode();
                    }
                    if(health == 0){
                        explode();
                    }
                }
                @Override
                public void kill(){
                    // 只要被殺死（health=0 或 kill() 被呼），並且啟用，就爆炸
                    if(enabled){
                        explode();
                    }
                    super.kill();
                }

                @Override
                public void damage(float amount){
                    float prev = health;       // 扣血前的生命值
                    super.damage(amount);      // 真正執行扣血邏輯
                    // 正好從活著到死亡，且功能開啟，觸發爆炸
                    if(prev > 0 && health <= 0){
                        explode();
                    }
                }



                // 統一爆炸邏輯
                private void explode(){
                    Fx.blastsmoke.at(x, y);
                    Fx.explosion.at(x, y);
                    Damage.damage(x, y, 50f, 150f);
                    Sounds.explosion.at(x, y);
                }
            };
        }};
    }
}



