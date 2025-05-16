package mymod.content.effect;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import mindustry.entities.Effect;

public class RedEffects {
    public static final Effect redLaserTrail = new Effect(30f, e -> {
        float f1 = e.fin();  // 0→1
        float f2 = e.fout(); // 1→0

        // 1) 中心光暈：由亮紅到暗紅，透明度隨 f2 漸消
        Draw.color(Color.valueOf("ff4444"), Color.valueOf("880000"), f2);
        Fill.circle(e.x, e.y, Mathf.lerp(4f, 0f, f1));
        Draw.color(); // 還原

        // 2) 火花端點：紅→橙，個別小圓表示
        for (int i = 0; i < 6; i++) {
            float angle = Mathf.random(360f);
            float len   = Mathf.lerp(1f, 6f, Mathf.random());
            float px = e.x + Mathf.cosDeg(angle) * len * f2;
            float py = e.y + Mathf.sinDeg(angle) * len * f2;

            Draw.color(Color.valueOf("ff8888"), Color.valueOf("ff0000"), 1f - f1);
            Fill.circle(px, py, Mathf.lerp(1.5f, 0f, f1));
            Draw.color();
        }

        // 3) 散射小碎片：深紅小圓，僅在初始階段產生
        if (f2 > 0.5f) {
            for (int i = 0; i < 4; i++) {
                float angle = Mathf.random(360f);
                float dist  = Mathf.random(0.5f, 2f);
                float px = e.x + Mathf.cosDeg(angle) * dist;
                float py = e.y + Mathf.sinDeg(angle) * dist;

                Draw.color(Color.valueOf("ff3333"), Color.valueOf("ff0000"), f2);
                Fill.circle(px, py, Mathf.lerp(1.5f, 0f, f1));
                Draw.color();
            }
        }
    });
}
