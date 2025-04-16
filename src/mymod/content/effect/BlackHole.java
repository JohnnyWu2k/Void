package mymod.content.effect;

import mindustry.entities.Effect;
import arc.graphics.Color;
import arc.math.Angles;
import arc.util.Tmp;
import arc.math.Mathf;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.Fill;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.game.Team;

public class BlackHole {
    public static final Effect blackHoleBulletEffect = new Effect(60f, e -> {
        float radius = 6f + 12f * e.fin();
        float rotation = e.fin() * 720f;

        Draw.color(Color.black);
        Fill.circle(e.x, e.y, radius);

        Draw.color(Color.black, Color.purple, e.fin());
        Lines.stroke(2f * (1f - e.fin()));
        Lines.circle(e.x, e.y, radius + 2f);

        for (int i = 0; i < 10; i++) {
            float angle = i * 36f + rotation;
            float len = radius + 4f;
            Tmp.v1.trns(angle, len);
            Draw.color(Color.gray, Color.black, e.fin());
            Fill.circle(e.x + Tmp.v1.x, e.y + Tmp.v1.y, 1.5f);
        }

        Draw.reset();
    });

    public static final Effect blackHoleExplosionEffect = new Effect(60f, e -> {
        float radius = 8f + 24f * e.fin();
        float rotation = e.fin() * 360f;

        Draw.color(Color.black);
        Fill.circle(e.x, e.y, radius);

        Draw.color(Color.purple, Color.black, e.fin());
        Lines.stroke(1.5f * (1f - e.fin()));
        Lines.circle(e.x, e.y, radius + 4f);

        for (int i = 0; i < 12; i++) {
            float angle = i * 30f + rotation;
            float len = radius * 0.8f;
            Tmp.v1.trns(angle, len);
            Draw.color(Color.gray, Color.black, e.fin());
            Fill.circle(e.x + Tmp.v1.x, e.y + Tmp.v1.y, 1.8f * (1f - e.fin()));
        }

        Draw.reset();
    });

    public static final Effect megaBlackHole = new Effect(90f, e -> {
        float radius = 35f * e.fin();
        float rotation = e.fin() * 1080f;

        Draw.color(Color.black);
        Fill.circle(e.x, e.y, radius * 0.8f);

        Draw.color(Color.black, Color.purple, e.fout());
        Lines.stroke(4f * (1f - e.fin()));
        Lines.circle(e.x, e.y, radius + 5f);

        for (int i = 0; i < 18; i++) {
            float angle = i * 20f + rotation;
            float len = radius * 1.2f;
            Tmp.v1.trns(angle, len);
            Draw.color(Color.black, Color.purple, e.fout());
            Fill.circle(e.x + Tmp.v1.x, e.y + Tmp.v1.y, 2.5f * (1f - e.fin()));
        }

        for (int i = 0; i < 12; i++) {
            float angle = i * 30f - rotation * 0.5f;
            float len = radius * 0.6f + Mathf.sin(e.time + i * 5f) * 6f;
            Tmp.v1.trns(angle, len);
            Draw.color(Color.purple, Color.black, e.fout());
            Fill.circle(e.x + Tmp.v1.x, e.y + Tmp.v1.y, 1.8f);
        }

        Draw.reset();
    });

    public static final Effect megaBlackHoleFinish = new Effect(40f, e -> {
        float radius = 60f * e.fout();

        Draw.color(Color.white, Color.purple, e.fin());
        Lines.stroke(3f * (1f - e.fin()));
        Lines.circle(e.x, e.y, radius);

        for (int i = 0; i < 20; i++) {
            float angle = i * 18f + e.fin() * 360f;
            Tmp.v1.trns(angle, radius * 0.7f);
            Draw.color(Color.white, Color.purple, e.fin());
            Fill.circle(e.x + Tmp.v1.x, e.y + Tmp.v1.y, 2f * (1f - e.fin()));
        }

        Draw.reset();
    });

    public static void triggerPersistentBlackHole(Bullet b){
        if(b.time % 2 < 1){
            blackHoleExplosionEffect.at(b.getX(), b.getY());
        }
    }

    public static void triggerMegaBlackHole(Bullet b){
        // 特效
        megaBlackHole.at(b.getX(), b.getY());

        // 吸附並傷害敵方單位
        float radius = 90f;
        float damage = 400f;
        float pull = 1.2f;
        Team team = b.team;

        Groups.unit.intersect(b.getX() - radius, b.getY() - radius, radius * 2f, radius * 2f, u -> {
            if(!u.dead && u.team != team && u.within(b.getX(), b.getY(), radius)){
                float angle = Mathf.atan2(u.getY() - b.getY(), u.getX() - b.getX()) * Mathf.radDeg;
                u.vel.add(Mathf.cosDeg(angle) * -pull, Mathf.sinDeg(angle) * -pull);
                u.damage(damage);
            }
        });

        // 最終特效
        megaBlackHoleFinish.at(b.getX(), b.getY());
    }
}
