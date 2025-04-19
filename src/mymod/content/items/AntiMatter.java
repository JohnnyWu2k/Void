package mymod.content.items;

import mindustry.type.Item;

public class AntiMatter {
    public static Item antimatter;

    public static void load(){
        antimatter = new Item("anti-matter"){{
            localizedName="反物質";
            description="一種與正物質帶電量相反的物質，蘊含著強大的能量。";
        }};
    }
}
