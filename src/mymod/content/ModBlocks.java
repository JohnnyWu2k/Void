package mymod.content;

import mindustry.content.Blocks;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mymod.content.blocks.*;

public class ModBlocks {
    public static void load(){
        BoomWall.load();
        BoomTurret.load();
        VoidPiercer.load();
        BlackHoleTurret.load();
        UnitsCrafter.load();
        UnitsUpgrader.load();
        if(Boolean.getBoolean("void.debugComputer")){
            ComputerBlock.load();
        }
        setupResearch();
    }

    private static void setupResearch(){
        node(BoomWall.boomwall, Blocks.blastMixer);
        node(BoomWall.smallboomwall, Blocks.blastMixer);
        node(BoomTurret.boomturret, Blocks.swarmer);
        node(VoidPiercer.voidPiercer, Blocks.meltdown);
        node(BlackHoleTurret.blackholeturret, VoidPiercer.voidPiercer);
        node(UnitsCrafter.UnitsFactory, Blocks.airFactory);
        node(UnitsUpgrader.UnitsUpgrader, UnitsCrafter.UnitsFactory);
    }

    private static void node(UnlockableContent content, UnlockableContent parent){
        if(content != null && parent != null && content.techNode == null && parent.techNode != null){
            new TechTree.TechNode(parent.techNode, content, content.researchRequirements());
        }
    }
}
