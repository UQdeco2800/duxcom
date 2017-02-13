package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.items.EquippableItem;
import uq.deco2800.duxcom.items.StatisticModifier;

public class HeroStatisticsController{
	
	 // Logger
    private static Logger logger = LoggerFactory.getLogger(HeroStatisticsController.class);
	
	private HeroStatisticsController(){
		
	}
	/**
	 * Equip item to hero and modify the statistic accordingly
	 * @param hero
	 * @param item
	 */
	public static void applyHeroStatistic(AbstractHero hero, EquippableItem item, boolean inverse){
		for(StatisticModifier statisticModifier:item.getStatisticModifiers()){
			statisticModify(hero, statisticModifier, inverse);
		}
	}
	
	/**
	 * Modify the hero Statistic according to StatisticModifier defination
	 * 
	 * @param hero
	 * @param statisticModifier
	 */
	public static void statisticModify(AbstractHero hero, StatisticModifier statisticModifier, boolean inverse){
		//Debug use
		logger.debug("Hero STAT '"+ statisticModifier.getStatistic().toString() +"' set to - "+ 
				operation(hero, statisticModifier, inverse)+"("+
				hero.getStats().get(statisticModifier.getStatistic())+" "+statisticModifier.getAction().toString()+(inverse?" INVERSE":"")+" "+
				statisticModifier.getValue()+")");
		hero.changeStat(statisticModifier.getStatistic(), operation(hero, statisticModifier, inverse));
	}
	
	/**
	 * Get value of change according to StatisticModifyAction defined in StatisticModifier
	 * @param action	StatisticModifyAction
	 * @param value		raw Value
	 * @return value	final modify value
	 */
	public static double operation(AbstractHero hero, StatisticModifier statisticModifier, boolean inverse){
		switch(statisticModifier.getAction()){
			case ADD:
				return hero.getStats().get(statisticModifier.getStatistic()) + (inverse?-statisticModifier.getValue():statisticModifier.getValue());
			case MULTIPLY:
				return hero.getStats().get(statisticModifier.getStatistic()) * (inverse?1/statisticModifier.getValue():statisticModifier.getValue());
			case DIVIDE:
				return hero.getStats().get(statisticModifier.getStatistic()) / (inverse?1/statisticModifier.getValue():statisticModifier.getValue());
			case SUBTRACT:
				return hero.getStats().get(statisticModifier.getStatistic()) - (inverse?-statisticModifier.getValue():statisticModifier.getValue());
			default:
				throw new UnsupportedOperationException();
		}
	}
}
