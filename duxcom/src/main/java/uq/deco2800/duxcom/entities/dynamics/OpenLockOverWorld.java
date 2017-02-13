package uq.deco2800.duxcom.entities.dynamics;

import uq.deco2800.duxcom.entities.EntityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarahamos on 12/10/2016.
 * This class has been made to load the animation of the LOCK_
 */


public class OpenLockOverWorld extends DynamicEntity {

    private static final List<Phase> PHASES = new ArrayList<>();
    static{

        PHASES.add(new Phase(EntityType.LOCK_0001).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0002).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0003).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0004).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0005).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0006).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0007).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0008).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0009).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0010).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0011).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0012).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0013).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0014).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0015).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0016).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0017).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0018).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0019).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0020).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0021).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0022).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0025).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0026).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0027).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0028).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0029).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0030).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0031).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0032).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0033).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0034).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0035).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0036).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0037).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0038).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0039).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0040).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0041).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0042).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0043).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0044).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0045).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0046).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0047).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0048).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0049).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0050).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0051).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0052).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0053).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0054).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0055).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0056).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0057).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0058).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0059).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0060).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0061).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0062).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0063).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0064).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0065).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0066).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0067).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0068).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0069).transmissionRate(0.25));
        PHASES.add(new Phase(EntityType.LOCK_0070).transmissionRate(0.25));

    }
    
    public OpenLockOverWorld(int x, int y){
        super(EntityType.LOCK_0001, x, y);
        setPhases(PHASES);
    }

    public void turnTick(){
        dynamicsManager.forEachSurrounding(getX(), getY(), 1, false,
                (Integer x, Integer y)-> {
                    if ((dynamicsManager.isEmpty(x,y)) && getCurrentPhase().transmits()){
                        dynamicsManager.addEntity(new OpenLockOverWorld(x,y));
                    }
                });
        this.advanceCurrentPhase();
    }

}
