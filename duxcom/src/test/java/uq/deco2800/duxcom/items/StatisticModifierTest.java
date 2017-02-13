package uq.deco2800.duxcom.items;

import org.junit.Test;

import uq.deco2800.duxcom.entities.heros.HeroStat;

import static org.junit.Assert.*;

public class StatisticModifierTest {

    @Test
    public void initStatisticModifierTest() {
        StatisticModifier statMod = new StatisticModifier(HeroStat.SPEED, StatisticModifyAction.ADD, 5);
        assertEquals(statMod.getStatistic(), HeroStat.SPEED);
        assertEquals(statMod.getAction(), StatisticModifyAction.ADD);
        assertTrue(statMod.getValue() == 5.0);
    }

    @Test
    public void equalStatisticModifierTest() {
        StatisticModifier statMod1 = new StatisticModifier(HeroStat.SPEED, StatisticModifyAction.ADD, 5);
        StatisticModifier statMod2 = new StatisticModifier(HeroStat.SPEED, StatisticModifyAction.ADD, 5);
        StatisticModifier statMod3 = new StatisticModifier(HeroStat.SPEED, StatisticModifyAction.ADD, 4);
        assertTrue(statMod1.equals(statMod1));
        assertTrue(statMod1.equals(statMod2));
        assertTrue(statMod2.equals(statMod1));
        assertFalse(statMod1.equals(statMod3));
        assertFalse(statMod3.equals(statMod2));
        assertEquals(statMod1.hashCode(), statMod2.hashCode());
    }

    @Test
    public void invalidEqualsTest() {
        StatisticModifier statMod1 = new StatisticModifier(HeroStat.SPEED, StatisticModifyAction.ADD, 5);
        StatisticModifier statMod2 = null;
        int notAStatisticModifier = 5;
        assertFalse(statMod1.equals(notAStatisticModifier));
        assertFalse(statMod1.equals(statMod2));
    }

}
