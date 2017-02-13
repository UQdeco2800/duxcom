/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.deco2800.duxcom.shop;

import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.items.Item;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Duck;
import uq.deco2800.duxcom.inventory.HeroInventory;
import uq.deco2800.duxcom.items.ItemGenerate;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.ammo.Ammo;
import uq.deco2800.duxcom.items.consumable.Consumable;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

/**
 *
 * @author user
 */
@RunWith(MockitoJUnitRunner.class)
public class ShopManagerTest {

    private ShopManager manager;

    @Mock
    private GameManager gameManager;

    @Mock
    private MapAssembly mapAssemblyMock;
    
    private AbstractHero abstractHero;

    @Before
    public void setUp() {
        when(gameManager.getGameWallet()).thenReturn(new PlayerWallet(500));
        abstractHero = new Duck(0,0);
        when(gameManager.getMap()).thenReturn(mapAssemblyMock);
        when(gameManager.getMap().getCurrentTurnHero()).thenReturn(abstractHero);
        manager = new ShopManager(gameManager);
    }

    @Test
    public void getShelvesTest() {
        assertTrue(manager.getShelves() != null);
    }

    @Test
    public void buyItemTest() {
        Item item = manager.getShelves()
                .getAllItemsByType(ItemType.SWORD.name()).get(1);
        assertTrue(manager.getShelves().getItem(item.getType(), item) != null);
        HeroInventory inventory = new HeroInventory(20);
        int startBalance = gameManager.getGameWallet().getBalance();
        gameManager.getGameWallet().addBalance(-500);
        assertFalse(manager.buyItem(inventory, item));
        // Not enough money
        assertEquals(gameManager.getGameWallet().getBalance(), (startBalance - 500));
        assertFalse(inventory.containsItem(item));
        // Enough money
        gameManager.getGameWallet().addBalance(10000);
        manager.buyItem(inventory, item);
        assertEquals(gameManager.getGameWallet().getBalance(),
                (startBalance - 500 + 10000 - item.getCost()));
        assertTrue(inventory.containsItem(item));
    }

    @Test
    public void buyNotExist() {
        HeroInventory inventory = null;
        Item item1 = new Consumable("Potion", 200, 100, "somewhere", HeroStat.HEALTH, 10, ItemType.CONSUMABLE);
        assertFalse(manager.buyItem(inventory, item1));
        inventory = new HeroInventory(20);
        assertFalse(manager.buyItem(inventory, item1));
        Item itemNull = null;
        manager.buyItem(inventory, itemNull);
        assertFalse(manager.buyItem(inventory, itemNull));
    }

    @Test
    public void sellItemTest() {
        Item item = ItemGenerate.axe.BRONZE_AXE.generate();
        HeroInventory inventory = new HeroInventory(20);
        inventory.addItem(item);
        manager.sellItem(inventory, item);
        assertTrue(!inventory.containsItem(item));
        assertEquals(gameManager.getGameWallet().getBalance(), 500 + (int) (item.getCost() * 0.75));

        // item null condition branch
        Item itemNull = null;
        manager.sellItem(inventory, itemNull);
        assertEquals(gameManager.getGameWallet().getBalance(), 500 + (int) (item.getCost() * 0.75));
        //untradeable item
        Item item1 = new Ammo("Cupid Arrow", 200, 100, "somewhere", false);
        inventory.addItem(item1);
        assertFalse(manager.sellItem(inventory, item1));
    }
}
