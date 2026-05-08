package mymod.content.blocks;

import arc.Core;
import arc.util.Time;
import arc.scene.ui.layout.Table;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.Damage;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Stat;

public class BoomWall {
    public static Wall boomwall,smallboomwall;

    public static void load(){
        boomwall = new Wall("boom-wall"){{
            requirements(Category.defense, BuildVisibility.shown, ItemStack.with(Items.blastCompound, 30));
            health              = 250;
            size                = 2;
            update              = true;
            buildCostMultiplier = 2f;
            stats.add(Stat.damage, 150f);

            // 支援玩家點擊與 Logic Processor 設定
            configurable        = true;
            logicConfigurable   = true;

            buildType = () -> new Building(){
                boolean enabled   = false;
                float   delayTime = 180f;
                float   timer     = 0f;
                boolean exploded  = false;

                @Override
                public void buildConfiguration(Table table){
                    table.button(Core.bundle.get(enabled ? "ui.void.boom-wall.disable" : "ui.void.boom-wall.enable"), () -> {
                        enabled = !enabled;
                        configure(new float[]{ enabled ? 1f : 0f, delayTime });
                    }).size(120f, 50f);
                    table.row();
                    table.label(() -> Core.bundle.format("ui.void.boom-wall.delay", delayTime / 60f)).padTop(8f).row();
                    table.slider(60f, 900f, 30f, v -> {
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
                    if(enabled){
                        explode();
                    }
                    super.kill();
                }

                @Override
                public void damage(float amount){
                    float prev = health;
                    super.damage(amount);
                    if(prev > 0 && health <= 0){
                        explode();
                    }
                }



                private void explode(){
                    if(exploded) return;
                    exploded = true;
                    Fx.blastsmoke.at(x, y);
                    Fx.explosion.at(x, y);
                    Damage.damage(x, y, 100f, 300f);
                    Sounds.explosion.at(x, y);
                    kill();
                }
            };
        }};


        smallboomwall = new Wall("small-boom-wall"){{
            requirements(Category.defense, BuildVisibility.shown, ItemStack.with(Items.blastCompound, 30));
            health              = 100;
            size                = 1;
            update              = true;
            buildCostMultiplier = 2f;
            stats.add(Stat.damage, 150f);

            // 支援玩家點擊與 Logic Processor 設定
            configurable        = true;
            logicConfigurable   = true;

            buildType = () -> new Building(){
                boolean enabled   = false;
                float   delayTime = 180f;
                float   timer     = 0f;
                boolean exploded  = false;

                @Override
                public void buildConfiguration(Table table){
                    table.button(Core.bundle.get(enabled ? "ui.void.boom-wall.disable" : "ui.void.boom-wall.enable"), () -> {
                        enabled = !enabled;
                        configure(new float[]{ enabled ? 1f : 0f, delayTime });
                    }).size(120f, 50f);
                    table.row();
                    table.label(() -> Core.bundle.format("ui.void.boom-wall.delay", delayTime / 60f)).padTop(8f).row();
                    table.slider(60f, 900f, 30f, v -> {
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
                    if(enabled){
                        explode();
                    }
                    super.kill();
                }

                @Override
                public void damage(float amount){
                    float prev = health;
                    super.damage(amount);
                    if(prev > 0 && health <= 0){
                        explode();
                    }
                }



                private void explode(){
                    if(exploded) return;
                    exploded = true;
                    Fx.blastsmoke.at(x, y);
                    Fx.explosion.at(x, y);
                    Damage.damage(x, y, 50f, 150f);
                    Sounds.explosion.at(x, y);
                    kill();
                }
            };
        }};
    }
}



