package uq.deco2800.duxcom.entities.dynamics;

import uq.deco2800.duxcom.entities.EntityType;

/**
 * Uses Fluent Interface Pattern for initialisation and to replace setter methods.
 * 
 * @author houraineet
 */
public class Phase {
	private EntityType entityType = null;
	private String name = "";
	private double transmissionRate = -1;
	private double damage;	
	private long tickInterval;
	
	public Phase() {
		// Do nothing because potato said so (really, nothing for now)
	}
	
	public Phase(EntityType entityType) {
		this.entityType = entityType;
	}
	
	public Phase entityType(EntityType entityType) {
		this.entityType = entityType;
		return this;
	}	
	
	public Phase name(String name) {
		this.name = name;
		return this;
	}
	
	public Phase transmissionRate(double transmissionRate) {
		this.transmissionRate = transmissionRate;
		return this;
	}
	
	public Phase damage(double damage) {
		this.damage = damage;
		return this;
	}
	
	public Phase tickInterval(long tickInterval) {
		this.tickInterval = tickInterval;
		return this;
	}
	
	
	
	public EntityType getEntityType() {
		return entityType;
	}
	
	public String getName() {
		return name;
	}
	
	public double getTransmissionRate() {
		return transmissionRate;
	}
	
	public boolean transmits() {
		if (transmissionRate < 0) {
			return false;
		} else {
			return Math.random() < this.transmissionRate;
		}
	}
	
	public double getDamage() {
		return damage;
	}
	
	public long getTickInterval() {
		return tickInterval;
	}
}
