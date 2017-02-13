package uq.deco2800.duxcom.particlesystem;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uq.deco2800.duxcom.GameManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Lucas Reher on 20/10/2016.
 */
public class ParticleGen {

    private int particleCount;
    private ArrayList<Particle> particles;
    private GraphicsContext context;
    private Random rnd = new Random();
    private GameManager gameManager;
    private int itterator;

    private boolean isRunning;

    public ParticleGen (GraphicsContext context, GameManager gameManager,
                        int particleCount) {
        this.particleCount = particleCount;
        this.context = context;
        this.particles = new ArrayList<>();
        this.gameManager = gameManager;
        this.itterator = 0;
        this.isRunning = false;
    }

    /**
     * Sets up the image all particles use, and the x and y starting
     * locations. The spread variables determine how far to the right
     * (for x) or down (for y) the particles will be spawned at randomly.
     *
     * To create a particle that can randomly appear in any x value,
     * but always at the top of the screen (weather), we would
     * instantiate the particles with args (image, 0, 0, mapWidth, 0)
     *
     * The angle determines the angle of the particles, and the spread
     * determines what leeway the particles have for angle. vel determines
     * the minimun speed of the particles and velSpread + vel determines
     * the maximum. Particle velocity can range between both.
     *
     * Age Limit is used to determine how long the particle will live for.
     *
     * @param image         Particle Image
     * @param startX        Base X starting position of particle
     * @param startY        Base Y starting position of particle
     * @param xSpread       Randomized spread of x start position
     * @param ySpread       Randomized spread of y start position
     * @param angle         Default particle angle
     * @param angleSpread   Particle angle variance
     * @param vel           Default particle angular velocity
     * @param velSpread     Angular velocity variance
     * @param ageLimit      Time the particle will spend alive
     *
     */
    public void setupParticles (Image image,
                                int startX, int startY,
                                int xSpread, int ySpread,
                                int angle, int angleSpread,
                                int vel, int velSpread,
                                int ageLimit) {

        for (int i = 0; i < this.particleCount; i++) {

            Particle particle = new Particle(this.context,
                    this.gameManager, image, startX + rnd.nextInt(xSpread),
                    startY + rnd.nextInt(ySpread),
                    Math.toRadians((angle + rnd.nextInt(angleSpread))%360),
                    vel + rnd.nextInt(velSpread), ageLimit);
            this.particles.add(i, particle);

        }
    }

    /**
     * Runs through a loop x times, as specifed by perTick and
     * instantiates particles to update. Keeps track of which
     * particles have already been instantiated through the
     * itterator. Use per tick to limit the ammount of particles
     * being spawned at once, a low number will give you lower
     * particle density.
     * @param perTick       How many particles to spawn per update
     * @param lifeLimit     How many times to allow particle spawns
     * @param changeX       Alters the start X coordinates of the particle
     * @param changeY       Alters the start Y coordinates of the particle
     */
    public void update (int perTick, int lifeLimit, int changeX, int changeY) {
        int lifeLimiter = 0;
        int ittDiff = this.itterator + perTick;
        for (int i = this.itterator; i < ittDiff; i++) {
            if (this.itterator == this.particleCount) {
                this.itterator = 0;
                break;
            }
            this.particles.get(i).setUpdatable();
            this.particles.get(i).moveStart(changeX, changeY);
            this.itterator += 1;
        }

        for (int j = 0; j < this.particleCount; j++) {
            this.particles.get(j).particleUpdate();
            if (lifeLimit != 0  &&
                    this.particles.get(j).getLife() == lifeLimit) {
                lifeLimiter += 1;
            }
        }

        // All particles are dead, end running
        if (lifeLimiter != 0) {
            this.setRunning(false);
        }
    }

    /**
     * Allows generator to run
     */
    public void setRunning (boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * Returns whether or not generator is running
     *
     * @return True if generator is running
     */
    public boolean getRunning () {
        return this.isRunning;
    }

    /**
     * Allows for adding secondary textures, for more interesting
     * effects that have multiple sprites eg Blizzard
     *
     * @param image                 Texture to change
     * @param textureProbability    Alters the probability so this
     *                              particle will be this texture
     *                              1/textureProbability is the
     *                              chance the texture will be the
     *                              secondary
     */
    public void addSecondaryTexture (Image image, int textureProbability) {
        for (int i = 0; i < this.particleCount; i++) {
            if (rnd.nextInt(textureProbability) == 1) {
                this.particles.get(i).changeTexture(image);
            }
        }
    }

}
