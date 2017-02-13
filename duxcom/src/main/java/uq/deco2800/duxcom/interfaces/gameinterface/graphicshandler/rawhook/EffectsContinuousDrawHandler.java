package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.rawhook;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.async.weathergetter.WeatherGetter;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.particlesystem.ParticleGen;

import java.util.Random;

/**
 * Used for drawing continuous effects on screen.
 *
 * Created by liamdm on 18/10/2016.
 */
public class EffectsContinuousDrawHandler extends ContinuousDrawHandler {

    // Misc
    private GameManager gameManager;
    private Random rnd = new Random();

    // Weather Generation
    private ParticleGen weatherEffects;
    private boolean weatherInit;
    private int weatherMode;
    private int weatherDensity;

    // Specialized Effects
    private ParticleGen lightningEffect;
    private boolean addLightning;
    private int effectTimer;
    private int lightningTimer;
    private int lightningLimiter;

    private int lightningVelX;
    private int lightningVelY;
    private int lightningAccX;
    private int lightningAccY;

    /*
     * Create the effects drawer
     */
    public EffectsContinuousDrawHandler(long refreshInterval,
                                        GameManager gameManager) {
        super(refreshInterval);
        this.gameManager = gameManager;
        this.effectTimer = 0;
        this.weatherMode = rnd.nextInt(5);

        this.lightningTimer = 200 + rnd.nextInt(200);
        this.lightningLimiter = 4;
        this.lightningVelX = 0;
        this.lightningVelY = 4;
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public void handle(GraphicsContext context) {
        context.clearRect(0, 0, context.getCanvas().getWidth(),
                context.getCanvas().getHeight());
        /*
            Has weather is true, therefore uses real life weather
            Cloudy weather gives snow, rainy gives rain and storm will
            give blizzard. On the off chance of snow in australia
            raindrops replaced with ducks (not implemented yet)
        */
        if (WeatherGetter.hasWeather()) {
            if (WeatherGetter.getCurrentWeather().toLowerCase().contains("cloudy")) {
                weatherMode = 13;
            } else if (WeatherGetter.getCurrentWeather().toLowerCase().contains("rain")) {
                weatherMode = 14;
            } else if (WeatherGetter.getCurrentWeather().toLowerCase().contains("storm")) {
                weatherMode = 15;
            }
        }

        // Initalizing weather
        while (!weatherInit) {
            if (weatherMode == 3 || weatherMode == 13) {
                // Creates snow effect
                weatherEffects =
                        new ParticleGen(context, this.gameManager, 950);
                weatherEffects.setupParticles(
                        TextureRegister.getTexture("snowsmall"),
                        -100, 0, (int) context.getCanvas().getWidth(),
                        10, 80, 1, 2, 2, 950);
                weatherEffects.setRunning(true);
                weatherDensity = 1;
            } else if (weatherMode == 4 || weatherMode == 14) {
                // Creates rain effect
                weatherEffects =
                        new ParticleGen(context, this.gameManager, 800);
                weatherEffects.setupParticles(
                        TextureRegister.getTexture("rain_4small"),
                        -100, 0, (int) context.getCanvas().getWidth(),
                        10, 70, 1, 8, 1, 200);
                weatherEffects.setRunning(true);
                weatherDensity = 3;

                this.addLightning = true;
            } else if (weatherMode == 5 || weatherMode == 15) {
                // Creates blizzard effect
                weatherEffects = new ParticleGen(
                        context, this.gameManager, 1000);
                weatherEffects.setupParticles(
                        TextureRegister.getTexture("rain_4small"),
                        -100, 0, (int) context.getCanvas().getWidth(),
                        10, 75, 5, 10, 1, 200);
                weatherEffects.setRunning(true);
                // Adds secondary texture for hail, 1/7 chance of hail texture
                weatherEffects.addSecondaryTexture(
                        TextureRegister.getTexture("hailsmall"), 7);
                weatherDensity = 3;

                this.addLightning = true;
            } else {
                // No weather
                weatherEffects = new ParticleGen(context, this.gameManager, 0);
                weatherEffects.setRunning(false);
            }
            weatherInit = true;
        }

        this.effectTimer += 1;

        /*
             Particles don't appear if the game is zoomed out enough, particles
             don't look good at that zoom.
         */
        if (weatherEffects.getRunning()) {
            weatherEffects.update(weatherDensity, 0, 0 ,0);
        }

        // Lightning manager
        if (this.addLightning && effectTimer % this.lightningTimer == 0) {
            lightningEffect = new ParticleGen(context, this.gameManager, 1200);
            lightningEffect.setupParticles(
                    TextureRegister.getTexture("lightning"),
                    1 + rnd.nextInt((int)context.getCanvas().getWidth()), -100,
                    1, 1, 90, 1, 0, 1, 100);
            lightningEffect.setRunning(true);

            this.lightningTimer = 200 + rnd.nextInt(200);

            this.lightningVelX = 0;
            this.lightningVelY = 0;
            this.lightningAccX = 0;
        }

        if (lightningEffect !=  null && lightningEffect.getRunning()) {
            this.lightningAccX += -2 + rnd.nextInt(5);
            this.lightningAccY = 12;
            this.lightningVelX += this.lightningAccX;
            this.lightningVelY += this.lightningAccY;

            // Limit lightning x acceleration
            if (this.lightningAccX > this.lightningLimiter) {
                this.lightningAccX = this.lightningLimiter;
            } else if (this.lightningAccX < -this.lightningLimiter) {
                this.lightningAccX = -this.lightningLimiter;
            }

            lightningEffect.update(50, 2,
                    this.lightningVelX - 2 + rnd.nextInt(4),
                    this.lightningVelY);
        }

        // Particles wont go over UI
        context.clearRect(context.getCanvas().getWidth()/5,
                context.getCanvas().getHeight() -
                        context.getCanvas().getHeight()/7,
                context.getCanvas().getWidth(),
                context.getCanvas().getHeight());
    }
}
