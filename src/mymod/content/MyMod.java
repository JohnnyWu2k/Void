package mymod.content;

import arc.util.Log; // Make sure Log is imported
import mindustry.mod.Mod;

public class MyMod extends Mod {


    @Override
    public void loadContent(){
        Log.info("-----------------------------------------");
        Log.info("MyMod: loadContent() started.");
        Log.info("-----------------------------------------");
        ModItems.load();
        ModUnits.load();
        ModBlocks.load();
    }
}