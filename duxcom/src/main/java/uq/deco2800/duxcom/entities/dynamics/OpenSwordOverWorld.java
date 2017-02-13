package uq.deco2800.duxcom.entities.dynamics;


import uq.deco2800.duxcom.entities.EntityType;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by sarahamos on 12/10/2016.
 * This class has been made to load the animation of the SWORD_
 */


public class OpenSwordOverWorld extends DynamicEntity {

    private static final List <Phase> PHASES = new ArrayList<>();
    static{
        

    	PHASES.add(new Phase(EntityType.SWORD_0001).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0002).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0003).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0004).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0005).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0006).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0007).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0008).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0009).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0010).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0011).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0012).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0013).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0014).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0015).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0016).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0017).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0018).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0019).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0020).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0021).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0022).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0025).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0026).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0027).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0028).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0029).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0030).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0031).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0032).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0033).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0034).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0035).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0036).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0037).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0038).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0039).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0040).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0041).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0042).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0043).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0044).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0045).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0046).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0047).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0048).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0049).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0050).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0051).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0052).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0053).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0054).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0055).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0056).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0057).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0058).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0059).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.SWORD_0060).transmissionRate(0.25));

    }
    public OpenSwordOverWorld(int x, int y){
        super(EntityType.SWORD_0001, x, y);
        setPhases(PHASES);
    }

    public void turnTick(){
        dynamicsManager.forEachSurrounding(getX(), getY(), 1, false,
                (Integer x, Integer y)-> {
                    if ((dynamicsManager.isEmpty(x,y)) && getCurrentPhase().transmits()){
                        dynamicsManager.addEntity(new OpenSwordOverWorld(x,y));
                    }
                });
        this.advanceCurrentPhase();
    }

}
