package mymod.content;

import mymod.content.blocks.*;

public class ModBlocks {
    public static void load(){
        BoomWall.load();
        BoomTurret.load();
        BlackHoleTurret.load();
        VoidPiercer.load();
        UnitsCrafter.load();
        UnitsUpgrader.load();
        ComputerBlock.load();
    }
}
