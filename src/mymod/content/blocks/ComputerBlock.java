package mymod.content.blocks;

import arc.util.Log;
import arc.scene.ui.layout.Table;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.content.Items;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;
import mymod.content.ui.FolderGuiDemoEnhanced;

/**
 * ComputerBlock.java
 *
 * 修改後的版本：點擊「啟用 Swing」按鈕時僅開啟 Swing 視窗，不關閉配置介面，也不在每次重繪時重複開啟。
 */
public class ComputerBlock {
    public static Wall computer;

    public static void load() {
        computer = new Wall("computer-wall") {{
            requirements(Category.defense, BuildVisibility.shown,
                    ItemStack.with(Items.blastCompound, 30)
            );
            localizedName      = "電腦方塊";
            description        = "點擊配置後可開啟 Swing 檔案管理與終端模擬 (配置介面不會自動關閉)";
            size               = 3;
            health             = 1000;
            update             = false;
            solid              = true;
            hasPower           = false;
            buildVisibility    = BuildVisibility.shown;

            configurable       = true;
            logicConfigurable  = true;

            buildType = () -> new Building() {
                boolean enabled = false;
                boolean hasOpenedSwing = false;

                @Override
                public void buildConfiguration(Table table) {
                    // 按鈕：切換啟用/停用 Swing UI
                    table.button(enabled ? "停用 Swing" : "啟用 Swing", () -> {
                        enabled = !enabled;
                        hasOpenedSwing = false; // 重置，讓下一次點擊才會開 Swing
                        configure(new Object[]{enabled});

                        if (enabled && !hasOpenedSwing) {
                            hasOpenedSwing = true;
                            // 在 Swing 的 EDT 上執行 new Swing 視窗
                            javax.swing.SwingUtilities.invokeLater(() -> {
                                try {
                                    FolderGuiDemoEnhanced demo = new FolderGuiDemoEnhanced();
                                    demo.setVisible(true);
                                } catch (Exception e) {
                                    Log.err("啟動 FolderGuiDemoEnhanced 失敗: " + e.getMessage());
                                }
                            });
                        }
                    }).size(200f, 50f).pad(8f);
                    table.row();

                    // 顯示目前狀態
                    table.label(() -> enabled ? "Swing 介面已啟用" : "Swing 介面尚未啟用")
                            .padTop(8f).row();
                }

                @Override
                public void configure(Object value) {
                    if (value instanceof Object[] arr && arr.length == 1 && arr[0] instanceof Boolean b) {
                        enabled = b;
                    }
                }
            };
        }};
    }
}
