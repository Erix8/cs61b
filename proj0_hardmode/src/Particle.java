import java.util.Map;
import java.awt.Color;

public class Particle {

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                    ParticleFlavor.PLANT, PLANT_LIFESPAN,
                    ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public ParticleFlavor flavor;
    public int lifespan;

    public Particle(ParticleFlavor f) {
        flavor = f;
        lifespan = LIFESPANS.getOrDefault(f, -1);
    }

    public Color color() {
        switch (flavor) {
            case ParticleFlavor.EMPTY -> {
                return Color.BLACK;
            }
            case ParticleFlavor.SAND -> {
                return Color.YELLOW;
            }
            case ParticleFlavor.BARRIER -> {
                return Color.GRAY;
            }
            case ParticleFlavor.WATER -> {
                return Color.BLUE;
            }
            case ParticleFlavor.FOUNTAIN -> {
                return Color.CYAN;
            }
            case ParticleFlavor.PLANT -> {
                return new Color(0, 255, 0);
            }
            case ParticleFlavor.FIRE -> {
                return new Color(255, 0, 0);
            }
            case ParticleFlavor.FLOWER -> {
                return new Color(255, 141, 161);
            }
            default -> {
                return Color.WHITE;
            }
        }
    }

    public void moveInto(Particle other) {
        other.flavor = flavor;
        other.lifespan = lifespan;
        flavor = ParticleFlavor.EMPTY;
        lifespan = -1;
    }
}
