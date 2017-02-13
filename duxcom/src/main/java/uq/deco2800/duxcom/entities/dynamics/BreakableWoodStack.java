package uq.deco2800.duxcom.entities.dynamics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.buffs.OnFire;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.entities.EntityType;

/**
 * Created by houraineet 27/08/16
 */
public class BreakableWoodStack extends DynamicEntity implements Targetable {

	private static Logger logger = LoggerFactory.getLogger(BreakableWoodStack.class);
    
    private double health = 25.0;
	private boolean onFire = false;

    public BreakableWoodStack(int x, int y) {
        super(EntityType.TALL, x, y);
    }
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public double getHealth() {
		return health;
	}
	
	public boolean isOnFire() {
		return onFire;
	}
	
	@Override
	public void changeHealth(double damage, DamageType damageType) {
		this.health += damage;
		if (this.health < 0) {
			logger.info("Wood Stack destroyed.");
		}
	}
	
	@Override
	public void addBuff(AbstractBuff buff) {
		if (buff instanceof OnFire) {
			onFire = true;
		}
	}
}
