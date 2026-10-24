import edu.princeton.cs.algs4.StdDraw;

import java.util.HashMap;
import java.util.Map;

public class ParticleSimulator {
    public Particle[][] particles;
    public int width;
    public int height;

    public static final Map<Character, ParticleFlavor> LETTER_TO_PARTICLE = Map.of(
            's', ParticleFlavor.SAND,
            'b', ParticleFlavor.BARRIER,
            'w', ParticleFlavor.WATER,
            'p', ParticleFlavor.PLANT,
            'f', ParticleFlavor.FIRE,
            '.', ParticleFlavor.EMPTY,
            'n', ParticleFlavor.FOUNTAIN,
            'z', ParticleFlavor.FLOWER
    );

    public ParticleSimulator(int w, int h) {
        width = w;
        height = h;
        particles = new Particle[width][height];
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                particles[i][j] = new Particle(ParticleFlavor.EMPTY);
            }
        }
    }

    @Override
    public String toString() {
        // 1. Build a reverse map to look up characters by Flavor
        Map<ParticleFlavor, Character> flavorToChar = new HashMap<>();
        for (Map.Entry<Character, ParticleFlavor> entry : LETTER_TO_PARTICLE.entrySet()) {
            flavorToChar.put(entry.getValue(), entry.getKey());
        }

        StringBuilder sb = new StringBuilder();

        // Have to iterate from the top so that
        // the top particles are shown first.
        for (int y = height - 1; y >= 0; y -= 1) {
            for (int x = 0; x < width; x += 1) {
                Particle p = particles[x][y];
                sb.append(flavorToChar.get(p.flavor));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void drawParticles() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                StdDraw.setPenColor(particles[x][y].color());
                StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
            }
        }
    }

    public boolean validIndex(int x, int y) {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    private Particle getParticle(int x, int y) {
        if (validIndex(x, y)) {
            return particles[x][y];
        } else {
            return new Particle(ParticleFlavor.BARRIER);
        }
    }

    public Map<Direction, Particle> getNeighbors(int x, int y) {
        return Map.of(
                Direction.UP, getParticle(x, y + 1),
                Direction.DOWN, getParticle(x, y - 1),
                Direction.LEFT, getParticle(x - 1, y),
                Direction.RIGHT, getParticle(x + 1, y)
        );
    }

    public void tick() {
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Map<Direction, Particle> neighbors = getNeighbors(x, y);
                particles[x][y].action(neighbors);
                particles[x][y].decrementLifespan();
            }
        }
    }

    static void main() {
        ParticleSimulator particleSimulator = new ParticleSimulator(150, 150);
        StdDraw.setXscale(0, particleSimulator.width);
        StdDraw.setYscale(0, particleSimulator.height);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);
        ParticleFlavor nextParticleFlavor = ParticleFlavor.SAND;

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                ParticleFlavor oldFlavor = nextParticleFlavor;
                nextParticleFlavor = LETTER_TO_PARTICLE.getOrDefault(key, oldFlavor);
            }

            if (StdDraw.isMousePressed()) {
                int x = (int) StdDraw.mouseX();
                int y = (int) StdDraw.mouseY();
                if (particleSimulator.validIndex(x, y)) {
                    particleSimulator.particles[x][y] = new Particle(nextParticleFlavor);
                }
            }

            particleSimulator.tick();
            particleSimulator.drawParticles();
            StdDraw.show();
            StdDraw.pause(5);
        }
    }

}
