package uq.deco2800.duxcom.buffs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.CallToArms;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Tests for the individual AbstractBuffs
 *
 * Created by jay-grant on 24/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class IndividualBuffTests {

    @Mock
    Entity entityMock;

    @Mock
    AbstractAbility abilityMock;

    @Mock
    AbstractCharacter characterMock;

    @Mock
    AbstractHero heroMock;

    @Mock
    AbstractEnemy enemyMock;

    @Test
    public void generalMethodsTest() {
        AbstractBuff buff = new Bleeding(0.0, 0);
        buff.onAbilityEffect(entityMock, abilityMock);
        buff.onTurn(characterMock);
        buff.onEndTurn(characterMock);
        assertFalse(buff.equals(entityMock));
        int hash = buff.hashCode();
        assertEquals(hash, buff.hashCode());
        String name = buff.getIconTextureName();
        assertEquals(name, "buff_placeholder");
        assertEquals(buff.getOriginalDuration(), 0);
        assertEquals(buff.getType(), BuffRegister.BLEEDING);
    }

    @Test
    public void callToArmsTest() {
        CallToArmsBuff callToArms = new CallToArmsBuff(0.0, 0);
        assertEquals((int) callToArms.getBuffStrength(), 0);
        assertTrue(callToArms.getName().equals("Call To Arms Buff"));
        callToArms.setBuffStrength(1);
        assertEquals((int) callToArms.getBuffStrength(), 1);
    }

    @Test
    public void defenderTest() {
        DefenderBuff defender = new DefenderBuff(0.0, 0);
        assertTrue(defender.getName().equals("Defender Buff"));
    }

    @Test
    public void groundHogTest() {
        DefenderBuff defender = new DefenderBuff(0.0, 0);
        assertTrue(defender.getName().equals("Defender Buff"));
    }

    @Test
    public void damageBuffTest() {
        GroundhogDayBuff groundhogDayBuff = new GroundhogDayBuff(heroMock, 0.0, 0);
        assertTrue(groundhogDayBuff.getName().equals("Ground Hog Day"));
        groundhogDayBuff.onEndTurn(heroMock);
        groundhogDayBuff.onEndTurn(enemyMock);
    }

    @Test
    public void visionBuffTest() {
        ObstructedVision obstructedVision = new ObstructedVision(0.0, 0);
        assertTrue(obstructedVision.getName().equals("Vision Obstructed"));
    }

    @Test
    public void wetBuffTest() {
        Wet wet = new Wet(0.0, 0);
        assertTrue(wet.getName().equals("Soaked with  Water"));
        wet.onAbilityEffect(entityMock, abilityMock);
        when(abilityMock.getDamageType()).thenReturn(DamageType.ELECTRIC);
        wet.onAbilityEffect(entityMock, abilityMock);
    }

    @Test
    public void poisonedBuffTest() {
        Poisoned poisoned = new Poisoned(0.0, 0);
        assertTrue(poisoned.getName().equals("Poisoned"));
        poisoned.onTurn(characterMock);
    }

    @Test
    public void onFireBuffTest() {
        OnFire onFire = new OnFire(0.0, 0);
        assertTrue(onFire.getName().equals("On Fire"));
        assertTrue(onFire.getIconTextureName().equals("onfire_icon"));
    }

    @Test
    public void buffFacoryTest() {
        AbstractBuff bleeding = AbstractBuff.getNewBuff(BuffRegister.BLEEDING, 0.0, 0);
        assertTrue(bleeding.getType() == BuffRegister.BLEEDING);

        AbstractBuff callToArms = AbstractBuff.getNewBuff(BuffRegister.CALL_TO_ARMS, 0.0, 0);
        assertTrue(callToArms.getType() == BuffRegister.CALL_TO_ARMS);

        AbstractBuff defender = AbstractBuff.getNewBuff(BuffRegister.DEFENDER, 0.0, 0);
        assertTrue(defender.getType() == BuffRegister.DEFENDER);

        AbstractBuff groundHog = AbstractBuff.getNewBuff(BuffRegister.GROUND_HOG, 0.0, 0);
        assertTrue(groundHog.getType() == BuffRegister.DEFENDER);

        AbstractBuff armour = AbstractBuff.getNewBuff(BuffRegister.ARMOUR_PLUS, 0.0, 0);
        assertTrue(armour.getType() == BuffRegister.ARMOUR_PLUS);

        AbstractBuff damage = AbstractBuff.getNewBuff(BuffRegister.DAMAGE_PLUS, 0.0, 0);
        assertTrue(damage.getType() == BuffRegister.DAMAGE_PLUS);

        AbstractBuff sight = AbstractBuff.getNewBuff(BuffRegister.SPOTTED, 0.0, 0);
        assertTrue(sight.getType() == BuffRegister.SPOTTED);

        AbstractBuff blind = AbstractBuff.getNewBuff(BuffRegister.BLIND, 0.0, 0);
        assertTrue(blind.getType() == BuffRegister.BLIND);

        AbstractBuff fire = AbstractBuff.getNewBuff(BuffRegister.ON_FIRE, 0.0, 0);
        assertTrue(fire.getType() == BuffRegister.ON_FIRE);

        AbstractBuff poisoned = AbstractBuff.getNewBuff(BuffRegister.POISONED, 0.0, 0);
        assertTrue(poisoned.getType() == BuffRegister.POISONED);

        AbstractBuff slow = AbstractBuff.getNewBuff(BuffRegister.SLOW, 0.0, 0);
        assertTrue(slow.getType() == BuffRegister.SLOW);

        AbstractBuff vulnerable = AbstractBuff.getNewBuff(BuffRegister.VULNERABLE, 0.0, 0);
        assertTrue(vulnerable.getType() == BuffRegister.VULNERABLE);

        AbstractBuff wet = AbstractBuff.getNewBuff(BuffRegister.WET, 0.0, 0);
        assertTrue(wet.getType() == BuffRegister.WET);

        AbstractBuff defaultFactory = AbstractBuff.getNewBuff(BuffRegister.FACTORY_DEFAULT, 0.0, 0);
        assertTrue(defaultFactory.getType() == BuffRegister.DEFENDER);
    }
}
