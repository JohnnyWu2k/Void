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
        computer = new Wall("computer") {{
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
                    table.button(enabled ? "停用 Swing" : "啟用 Swing", () -> {
                        enabled = !enabled;
                        configure(new Object[]{enabled});

                        if (enabled && !hasOpenedSwing) {
                            hasOpenedSwing = true;
                            // 啟動 Swing
                            javax.swing.SwingUtilities.invokeLater(() -> {
                                try {
                                    FolderGuiDemoEnhanced demo = new FolderGuiDemoEnhanced();
                                    demo.setVisible(true);
                                } catch (Exception e) {
                                    Log.err("啟動 FolderGuiDemoEnhanced 失敗: " + e.getMessage());
                                }
                            });
                        } else if (!enabled) {
                            // 停用 Swing → 在 EDT 中遍歷並關閉所有的 FolderGuiDemoEnhanced 視窗
                            javax.swing.SwingUtilities.invokeLater(() -> {
                                for (java.awt.Window w : java.awt.Window.getWindows()) {
                                    if (w instanceof FolderGuiDemoEnhanced) {
                                        w.dispose();
                                    }
                                }
                            });
                            hasOpenedSwing = false; // 重置
                        }
                    }).size(200f, 50f).pad(8f);
                    table.row();
                    table.label(() -> enabled ? "Swing 已啟用" : "Swing 已停用")
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
