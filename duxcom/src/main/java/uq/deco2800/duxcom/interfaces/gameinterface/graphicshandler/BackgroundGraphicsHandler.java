package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

/**
 * Created by elliot on 19/08/2016.
 * (Thanks to @Liamdm for the help!)
 */
public class BackgroundGraphicsHandler extends GraphicsHandler {
	
	//tVal for each background colour
	private double tVal1 = 0.5;
	private double tVal2 = 0;
	private double tVal3 = 0;
	private double tVal4 = 0;

    //Ratio for each background colour, corresponds with tVal
    private double ratio1 = 0;
    private double ratio2 = 0;
    private double ratio3 = 0;
    private double ratio4 = 0;

    private double currentTime;
	
    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

        graphicsRects.clear();

        currentTime = currentGameManager.getDayNightClock().getTime();
        //finding tVal for every background
        ratio1 = getRatio1(currentTime);
        if(Math.abs(tVal1 - ratio1) > 0.002d){
            tVal1 += tVal1 > ratio1 ?  -0.002d : 0.002d;
        }
        
        ratio2 = getRatio2(currentTime);
        if(Math.abs(tVal2 - ratio2) > 0.002d){
            tVal2 += tVal2 > ratio2 ?  -0.002d : 0.002d;
        }
        
        ratio3 = getRatio3(currentTime);
        if(Math.abs(tVal3 - ratio3) > 0.002d){
            tVal3 += tVal3 > ratio3 ?  -0.002d : 0.002d;
        }
      
        ratio4 = getRatio4(currentTime);
        if(Math.abs(tVal4 - ratio4) > 0.002d){
            tVal4 += tVal4 > ratio4 ?  -0.002d : 0.002d;
        }

        drawBackground(graphicsContext);

    }

    private void drawBackground(GraphicsContext graphicsContext) {
        // Orange at time 0-6
        if (currentTime < 6 && currentTime > 0) {
            graphicsRects.add(new RectToAdd(new Color(1, 0.56, 0.003, tVal1), 0, 0,
                    graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight()));
        }

        //Blue at time 6-12
        else if (currentTime > 6 && currentTime < 12) {
            graphicsRects.add(new RectToAdd(new Color(0.3, 0.55, 1, tVal2), 0, 0,
                    graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight()));
        }

        //Orange at time 12-18
        else if (currentTime > 12 && currentTime < 18) {
            graphicsRects.add(new RectToAdd(new Color(1, 0.55, 0.003, tVal3), 0, 0,
                    graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight()));
        }
        //Blue/Black at time 18-0
        else if (currentTime > 18 && currentTime < 23.99) {
            graphicsRects.add(new RectToAdd(new Color(0, 0, 0.3, tVal4), 0, 0,
                    graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight()));
        }
/*       //Faint white beneath everything
        graphicsRects.add(new RectToAdd(new Color(1, 1, 1, 0.2), 0, 0,
                graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight()));*/

        currentGameManager.setBackgroundChanged(false);
    }

    @Override
    public boolean needsUpdating() {
        if (currentGameManager.isGameChanged()) {
            updated = true;
        }
        return currentGameManager.isBackgroundChanged();
    }
   

	/**
     * Gets the ratio for the background colours -
     * @param clockValue - value of the current day/night clock
     * @return end point of gradient (or ratio)
     */
    public double getRatio1(double clockValue) {
 
        if (Math.abs(clockValue-0) < 0.002d){
            ratio1 = 0.5;
        }
        else if (Math.abs(clockValue - 6) < 0.002d){
        	ratio1 = 0;
        }
        
        return ratio1;
    }
    public double getRatio2(double clockValue) {
        
        if (Math.abs(clockValue - 6) < 0.002d){
            ratio2 = 0.5;
        }
        else if (Math.abs(clockValue - 12) < 0.002d){
        	ratio2 = 0;
        }
        return ratio2;
    }
    public double getRatio3(double clockValue) {
        if (Math.abs(clockValue - 12) < 0.002d){
            ratio3 = 0.5;
        }
        else if (Math.abs(clockValue - 18) < 0.002d){
        	ratio3 = 0;
        }
        return ratio3;
    }
    public double getRatio4(double clockValue) {
        
        if (Math.abs(clockValue - 18) < 0.002d){
            ratio4 = 0.3;
        }
        else if (Math.abs(clockValue - 0) < 0.002d){
        	ratio4 = 0;
        }
        return ratio4;
    }
    
    
}
