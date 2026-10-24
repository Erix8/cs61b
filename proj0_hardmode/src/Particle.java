import edu.princeton.cs.algs4.StdRandom;

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
                double ratio = (double) Math.clamp(lifespan, 0, PLANT_LIFESPAN) / PLANT_LIFESPAN;
                int g = 120 + (int) Math.round((255 - 120) * ratio);
                return new Color(0, g, 0);
            }
            case ParticleFlavor.FIRE -> {
                double ratio = (double) Math.clamp(lifespan, 0, FIRE_LIFESPAN) / FIRE_LIFESPAN;
                int r = (int) Math.round(255 * ratio);
                return new Color(r, 0, 0);
            }
            case ParticleFlavor.FLOWER -> {
                double ratio = (double) Math.clamp(lifespan, 0, FLOWER_LIFESPAN) / FLOWER_LIFESPAN;
                int r = 120 + (int) Math.round((255 - 120) * ratio);
                int g = 70 + (int) Math.round((141 - 70) * ratio);
                int b = 80 + (int) Math.round((161 - 80) * ratio);
                return new Color(r, g, b);
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

    public void fall(Map<Direction, Particle> neighbors) {
        Particle down = neighbors.get(Direction.DOWN);
        if (down.flavor == ParticleFlavor.EMPTY) {
            moveInto(down);
        }
    }

    private void flow(Map<Direction, Particle> n, Direction d) {
        Particle p = n.get(d);
        if (p.flavor == ParticleFlavor.EMPTY) {
            moveInto(p);
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int chance = StdRandom.uniformInt(3);
        switch (chance) {
            case 1 -> flow(neighbors, Direction.LEFT);

            case 2 -> flow(neighbors, Direction.RIGHT);
            default -> {}
        }
    }

    private void grow(Map<Direction, Particle> n, Direction d) {
        Particle p = n.get(d);
        if (p.flavor == ParticleFlavor.EMPTY) {
            p.flavor = flavor;
            p.lifespan = LIFESPANS.get(flavor);
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int chance = StdRandom.uniformInt(10);
        switch (chance) {
            case 0 -> grow(neighbors, Direction.UP);
            case 1 -> grow(neighbors, Direction.LEFT);
            case 2 -> grow(neighbors, Direction.RIGHT);
            default -> {}
        }
    }

    private void burn(Particle p) {
        if (p.flavor == ParticleFlavor.PLANT || p.flavor == ParticleFlavor.FLOWER) {
            int chance = StdRandom.uniformInt(10);
            if (chance < 4) {
                p.flavor = ParticleFlavor.FIRE;
                p.lifespan = LIFESPANS.get(ParticleFlavor.FIRE);
            }
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
        burn(neighbors.get(Direction.LEFT));
        burn(neighbors.get(Direction.RIGHT));
    }

    public void action(Map<Direction, Particle> neighbors) {
        if (flavor == ParticleFlavor.EMPTY) {
            return;
        }
        if (flavor != ParticleFlavor.BARRIER) {
            fall(neighbors);
        }
        if (flavor == ParticleFlavor.WATER) {
            flow(neighbors);
        }
        if (flavor == ParticleFlavor.PLANT || flavor == ParticleFlavor.FLOWER) {
            grow(neighbors);
        }
        if (flavor == ParticleFlavor.FIRE) {
            burn(neighbors);
        }
    }

    public void decrementLifespan() {
        if (lifespan == 0) {
            flavor = ParticleFlavor.EMPTY;
            lifespan = -1;
        } else if (lifespan > 0) {
            lifespan -= 1;
        }
    }

}
