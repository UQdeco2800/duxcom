package uq.deco2800.duxcom.particlesystem;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uq.deco2800.duxcom.GameManager;

import java.util.Random;

/**
 * Created by Lucas Reher on 20/10/2016.
 */
public class Particle {

    private Image image;
    private int startX;
    private int startY;
    private int currX;
    private int currY;
    private int startVel;
    private int vel;

    private int ageLimit;
    private int life;
    private double angle;
    private int age;
    private boolean canUpdate;
    private Random rnd = new Random();

    private GraphicsContext context;
    private GameManager gameManager;

    public Particle (GraphicsContext context, GameManager gameManager,
                     Image image, int startX, int startY,
                     double angle, int vel, int ageLimit) {
        this.image = image;
        this.startX = startX;
        this.startY = startY;
        this.startVel = vel;
        this.vel = vel;
        this.angle = angle;


        this.currX = this.startX;
        this.currY = this.startY;

        this.ageLimit = ageLimit;
        this.age = 0;
        this.canUpdate = false;

        this.context = context;
        this.gameManager = gameManager;

    }

    /**
     * Updates the particle (if set to be update-able)
     * by drawing the particle at the current x and y values.
     *
     * Updates x and y positions based on angle and angular
     * velocity.
     *
     * Makes particle age and checks if it has reached its
     * maximum age. If maximum ages is reached, the particle
     * is reset to its initial velocity and position
     */
    public void particleUpdate () {
        if (canUpdate) {
            context.drawImage(this.image, this.currX, this.currY);

            this.currX += this.vel * Math.cos(this.angle);
            this.currY += this.vel * Math.sin(this.angle);

            age++;

            if (this.age % this.ageLimit == 0) {
                this.currX = startX;
                this.currY = startY;
                this.vel = this.startVel;
                this.life += 1;
            }
        }
    }

    /**
     * Allows particle to be turned on and off by the
     * particle generator
     */
    public void setUpdatable(){
        this.canUpdate = true;
    }

    /**
     * Returns the current life of the particle
     * @return      What life the particle is at
     */
    public int getLife () {
        return this.life;
    }


    /**
     * Alters the X and Y coordinates of the particle
     * on the go. Used for constantly changing spawn
     * locations
     *
     * @param changeX       New start X position
     * @param changeY       New start Y position
     */
    public void moveStart (int changeX, int changeY) {
        this.currX += changeX;
        this.currY += changeY;
    }

    /**
     * Changes particle texture on the go
     */
    public void changeTexture (Image image){
        this.image = image;
    }
}
