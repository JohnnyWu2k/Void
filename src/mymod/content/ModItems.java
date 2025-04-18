package mymod.content;

import arc.graphics.Color;
import arc.util.Log;
import mindustry.type.Item;
import mymod.content.items.*;

public class ModItems {
    public static void load() {
        /* norium=new Item("norium", Color.valueOf("6495ED")){{
            localizedName="norium";
        }};
        /*darkmatter=new Item("darkmatter",Color.valueOf("2D2B55")){{
            localizedName="暗物質";
            description= "不受電磁力，卻受重力作用，可以讓許多星體能夠保持結構的物質。";
            explosiveness= 2f;
            flammability= 2f;
        }};
        */
        DarkMatter.load();
        Norium.load();
        //Log.info("ModItems: register darkmatter");
        //Norium.load();
        //DarkMatter.load();// 以後要加更多物品，直接在這裡寫就好
    }
}
