package uq.deco2800.duxcom.loot;

import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.DeathMagma;
import uq.deco2800.duxcom.entities.PickableEntities;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyArcher;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyBear;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyDarkMage;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemySupport;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.inventory.HeroInventory;
import uq.deco2800.duxcom.inventory.LootInventory;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.RarityLevel;
import uq.deco2800.duxcom.items.shield.Shield;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.shop.PlayerWallet;

/**
 * Test LootManager
 *
 * @author The_Magic_Karps
 */
@RunWith(MockitoJUnitRunner.class)
public class LootManagerTest {

    private LootManager lootManager;

    private AtomicBoolean quit;

    @Mock
    private MapAssembly mapMock;

    @Mock
    private AbstractHero currentHeroMock;

    @Mock
    private GameManager gameManagerMock;

    @Before
    public void setUp() {
        lootManager = new LootManager(mapMock);
    }

    @Test
    public void testMakeLoot() {
        PickableEntities deadEnemy = new DeathMagma(5, 5);
        lootManager.makeLoot(deadEnemy, LootRarity.RARE);
        assertNotNull(lootManager.getLoot(deadEnemy));
    }

    /**
     * Helper method to retrieve gameManager
     */
    private void launchGameLoop() {
        quit = new AtomicBoolean(false);
        new GameLoop(50, quit, gameManagerMock);
        when(gameManagerMock.getMap()).thenReturn(mapMock);
        when(gameManagerMock.getGameWallet()).thenReturn(new PlayerWallet());
    }

    @Test
    public void testTakeAllLoot() {
        when(mapMock.getCurrentTurnHero()).thenReturn(currentHeroMock);
        PickableEntities lootBagScrub = new DeathMagma(5, 5);
        lootManager.makeLoot(lootBagScrub, LootRarity.RARE);
        assertNotNull(lootManager.getLoot(lootBagScrub));

        // when enough space
        when(currentHeroMock.getInventory()).thenReturn(new HeroInventory(5));
        launchGameLoop();
        int lootBalance = lootManager.getLoot(lootBagScrub).getCoinBalance();
        assertTrue(lootManager.takeAllLoot(lootBagScrub));
        // wallet still goes in
        assertEquals(GameLoop.getCurrentGameManager().getGameWallet()
                .getBalance(), lootBalance);

        // when not enough space
        when(currentHeroMock.getInventory()).thenReturn(new HeroInventory(1));
        lootManager.makeLoot(lootBagScrub, LootRarity.RARE);
        assertNotNull(lootManager.getLoot(lootBagScrub));
        assertFalse(lootManager.takeAllLoot(lootBagScrub));
    }

    @Test
    @Ignore
    public void testTakeItemFromLoot() {
        when(mapMock.getCurrentTurnHero()).thenReturn(currentHeroMock);
        PickableEntities lootBagScrub = new DeathMagma(5, 5);
        lootManager.makeLoot(lootBagScrub, LootRarity.RARE);
        LootInventory lootInventory = lootManager.getLoot(lootBagScrub);
        assertNotNull(lootManager.getLoot(lootBagScrub));

        // when enough space 
        when(currentHeroMock.getInventory()).thenReturn(new HeroInventory(1));
        assertTrue(lootManager.takeItemFromLoot(lootBagScrub,
                lootManager.getLoot(lootBagScrub).getItemFromSlot(1)));

        // When not enough space
        assertFalse(lootManager.takeItemFromLoot(lootBagScrub,
                lootManager.getLoot(lootBagScrub).getItemFromSlot(2)));

        // testing removing as unlooted
        when(currentHeroMock.getInventory()).thenReturn(new HeroInventory(10));
        for (int i = 1; i < lootInventory.getItems().size() + 1; i++) {
            Item item = lootInventory.getItemFromSlot(i);
            if (lootInventory.containsItem(item) && item != null) {
                lootManager.takeItemFromLoot(lootBagScrub, item);
                assertFalse(lootInventory.containsItem(item));
            }
        }
        // wallet remains
        assertTrue(!lootManager.getAllUnlooted().isEmpty());
    }

    @Test
    public void testLootRange() {
        Archer hero = new Archer(5, 5);
        when(mapMock.getCurrentTurnHero()).thenReturn(hero);
        PickableEntities walkingLootBag = new DeathMagma(5, 4);
        lootManager.makeLoot(walkingLootBag, LootRarity.COMMON);
        // In range
        assertTrue(lootManager.getInvInLootArea(hero).contains(walkingLootBag));

        assertTrue(lootManager.getInvInLootArea(5, 5).contains(walkingLootBag));
        assertTrue(lootManager.getInvInLootArea(5, 4).contains(walkingLootBag));
        assertTrue(lootManager.getInvInLootArea(5, 3).contains(walkingLootBag));

        assertTrue(lootManager.getInvInLootArea(4, 4).contains(walkingLootBag));
        assertTrue(lootManager.getInvInLootArea(4, 5).contains(walkingLootBag));
        assertTrue(lootManager.getInvInLootArea(4, 3).contains(walkingLootBag));

        assertTrue(lootManager.getInvInLootArea(6, 3).contains(walkingLootBag));
        assertTrue(lootManager.getInvInLootArea(6, 5).contains(walkingLootBag));
        assertTrue(lootManager.getInvInLootArea(6, 4).contains(walkingLootBag));

        // Not in range
        assertFalse(lootManager.getInvInLootArea(3, 6).contains(walkingLootBag));
        assertFalse(lootManager.getInvInLootArea(5, 6).contains(walkingLootBag));
        assertFalse(lootManager.getInvInLootArea(4, 2).contains(walkingLootBag));
        // not in range
        PickableEntities fleeingLootBag = new DeathMagma(10, 10);
        lootManager.makeLoot(fleeingLootBag, LootRarity.RANDOM);
        assertFalse(lootManager.getInvInLootArea(hero)
                .contains(fleeingLootBag));
        assertFalse(lootManager.getInvInLootArea(5, 5)
                .contains(fleeingLootBag));
    }

    @Test
    public void testGetLoot() {
        Archer hero = new Archer(5, 6);
        when(mapMock.getCurrentTurnHero()).thenReturn(hero);
        PickableEntities deathMagma1 = new DeathMagma(5, 5);
        PickableEntities deathMagma2 = new DeathMagma(5, 7);
        lootManager.makeLoot(deathMagma1, LootRarity.RARE);
        lootManager.makeLoot(deathMagma2, LootRarity.RARE);
        assertNotEquals(lootManager.getLoot(deathMagma1), lootManager
                .getLoot(deathMagma2));

        assertEquals(lootManager.getInvInLootArea(hero).size(), 2);
    }

    @Test
    @Ignore
    public void testTakeLootByItem() {
        when(mapMock.getCurrentTurnHero()).thenReturn(currentHeroMock);
        PickableEntities lootBagScrub = new DeathMagma(5, 5);
        lootManager.makeLoot(lootBagScrub, LootRarity.RARE);
        LootInventory lootInventory = lootManager.getLoot(lootBagScrub);
        assertNotNull(lootManager.getLoot(lootBagScrub));
        when(currentHeroMock.getInventory()).thenReturn(new HeroInventory(1));

        // when item don't exist
        assertFalse(lootManager.takeItemFromLoot(new Shield("Wooden Shield",
                150, 50, "SOMEWHERE", 20, 80, RarityLevel.COMMON)));

        // when enough space 
        assertTrue(lootManager.takeItemFromLoot(lootManager
                .getLoot(lootBagScrub).getItemFromSlot(1)));

        // When not enough space
        assertFalse(lootManager.takeItemFromLoot(lootManager
                .getLoot(lootBagScrub).getItemFromSlot(2)));

        // testing removing as unlooted
        when(currentHeroMock.getInventory()).thenReturn(new HeroInventory(10));
        for (int i = 1; i < lootInventory.getItems().size() + 1; i++) {
            Item item = lootInventory.getItemFromSlot(i);
            if (lootInventory.containsItem(item) && item != null) {
                lootManager.takeItemFromLoot(item);
                assertFalse(lootInventory.containsItem(item));
            }
        }
        // wallet remains
        assertTrue(!lootManager.getAllUnlooted().isEmpty());
        launchGameLoop();
        lootManager.takeCoins(lootInventory);
        assertTrue(lootManager.getAllUnlooted().isEmpty());
    }

    @Test
    public void testRange() {
        assertEquals(lootManager.getLootRange(), 1);
    }

    @Test
    public void testGetDefinedRarity() {
        EnemyBear bear = new EnemyBear(5, 5);
        assertEquals(lootManager.getDefinedRarity(bear),
                LootRarity.RANDOM_COMMON_RARE);
        EnemyKnight knight = new EnemyKnight(5, 5);
        assertEquals(lootManager.getDefinedRarity(knight),
                LootRarity.RARE);
        EnemyDarkMage mage = new EnemyDarkMage(5, 5);
        assertEquals(lootManager.getDefinedRarity(mage),
                LootRarity.RARE);
        EnemySupport support = new EnemySupport(5, 5);
        assertEquals(lootManager.getDefinedRarity(support),
                LootRarity.RARE);
        EnemyArcher archer = new EnemyArcher(5, 5);
        assertEquals(lootManager.getDefinedRarity(archer),
                LootRarity.COMMON);
    }

    @Test
    public void testGetAllUnlooted() {
        EnemyBear bear = new EnemyBear(5, 5);
        EnemyKnight knight = new EnemyKnight(5, 6);
        DeathMagma deadEntity = new DeathMagma(5, 5);
        DeathMagma deadEntity2 = new DeathMagma(5, 6);
        lootManager.makeLoot(deadEntity, lootManager.getDefinedRarity(bear));
        lootManager.makeLoot(deadEntity2, lootManager.getDefinedRarity(knight));
        assertEquals(lootManager.getAllUnlooted().size(), 2);
        assertTrue(lootManager.getAllUnlooted().contains(deadEntity));
        assertTrue(lootManager.getAllUnlooted().contains(deadEntity2));
    }

    @Test
    public void testDiscardItem() {
        DeathMagma deadEntity = new DeathMagma(5, 5);
        lootManager.makeLoot(deadEntity, LootRarity.RANDOM);
        LootInventory lootInventory = lootManager.getLoot(lootManager.getAllUnlooted().get(0));
        assertFalse(lootManager.getAllUnlooted().isEmpty());

        // if item don't exist
        lootManager.discardItem(new Shield("Wooden Shield",
                150, 50, "SOMEWHERE", 20, 80, RarityLevel.COMMON));
        assertEquals(lootInventory.getItems().size(), lootInventory.getInventorySize());

        // items exists
        for (int i = 1; i < lootInventory.getItems().size() + 1; i++) {
            Item item = lootInventory.getItemFromSlot(i);
            if (lootInventory.containsItem(item) && item != null) {
                lootManager.discardItem(item);
                assertFalse(lootInventory.containsItem(item));
            }
        }
        // wallet remains
        launchGameLoop();
        assertTrue(!lootManager.getAllUnlooted().isEmpty());
        lootManager.discardCoins(lootInventory);
        assertEquals(GameLoop.getCurrentGameManager().getGameWallet()
                .getBalance(), 0);
        assertTrue(lootManager.getAllUnlooted().isEmpty());
    }

    @Test
    public void testEmptyTakeItemWithItem() {
        when(mapMock.getCurrentTurnHero()).thenReturn(currentHeroMock);
        when(currentHeroMock.getInventory()).thenReturn(new HeroInventory(5));
        DeathMagma deadEntity = new DeathMagma(5, 5);
        lootManager.makeLoot(deadEntity, LootRarity.RANDOM);
        LootInventory lootInventory = lootManager.getLoot(lootManager.getAllUnlooted().get(0));
        assertFalse(lootManager.getAllUnlooted().isEmpty());

        // discard wallet
        assertTrue(!lootManager.getAllUnlooted().isEmpty());
        lootManager.discardCoins(lootInventory);
        assertTrue(!lootManager.getAllUnlooted().isEmpty());

        // take items
        for (int i = 1; i < lootInventory.getItems().size() + 1; i++) {
            Item item = lootInventory.getItemFromSlot(i);
            if (lootInventory.containsItem(item) && item != null) {
                lootManager.takeItemFromLoot(item);
                assertFalse(lootInventory.containsItem(item));
            }
        }
        assertTrue(lootManager.getAllUnlooted().isEmpty());
    }

    @Test
    public void testEmptyDiscardItem() {
        when(mapMock.getCurrentTurnHero()).thenReturn(currentHeroMock);
        when(currentHeroMock.getInventory()).thenReturn(new HeroInventory(5));
        DeathMagma deadEntity = new DeathMagma(5, 5);
        lootManager.makeLoot(deadEntity, LootRarity.RANDOM);
        LootInventory lootInventory = lootManager.getLoot(lootManager.getAllUnlooted().get(0));
        assertFalse(lootManager.getAllUnlooted().isEmpty());

        // discard wallet
        int lootBalance = lootManager.getLoot(deadEntity).getCoinBalance();
        assertTrue(!lootManager.getAllUnlooted().isEmpty());
        lootManager.discardCoins(lootInventory);
        assertTrue(!lootManager.getAllUnlooted().isEmpty());

        // take items
        for (int i = 1; i < lootInventory.getItems().size() + 1; i++) {
            Item item = lootInventory.getItemFromSlot(i);
            if (lootInventory.containsItem(item) && item != null) {
                lootManager.discardItem(item);
                assertFalse(lootInventory.containsItem(item));
            }
        }
        assertTrue(lootManager.getAllUnlooted().isEmpty());
    }

    @Test
    public void testEmptyTakeItem() {
        when(mapMock.getCurrentTurnHero()).thenReturn(currentHeroMock);
        when(currentHeroMock.getInventory()).thenReturn(new HeroInventory(5));
        DeathMagma deadEntity = new DeathMagma(5, 5);
        lootManager.makeLoot(deadEntity, LootRarity.RANDOM);
        LootInventory lootInventory = lootManager.getLoot(lootManager.getAllUnlooted().get(0));
        assertFalse(lootManager.getAllUnlooted().isEmpty());

        // discard wallet
        int lootBalance = lootManager.getLoot(deadEntity).getCoinBalance();
        assertTrue(!lootManager.getAllUnlooted().isEmpty());
        lootManager.discardCoins(lootInventory);
        assertTrue(!lootManager.getAllUnlooted().isEmpty());

        // take items
        for (int i = 1; i < lootInventory.getItems().size() + 1; i++) {
            Item item = lootInventory.getItemFromSlot(i);
            if (lootInventory.containsItem(item) && item != null) {
                lootManager.takeItemFromLoot(deadEntity, item);
                assertFalse(lootInventory.containsItem(item));
            }
        }
        assertTrue(lootManager.getAllUnlooted().isEmpty());
    }

    @Test
    public void testDiscardItem_2args() {
        DeathMagma deadEntity = new DeathMagma(5, 5);
        lootManager.makeLoot(deadEntity, LootRarity.RANDOM);
        LootInventory lootInventory = lootManager.getLoot(deadEntity);
        assertFalse(lootManager.getAllUnlooted().isEmpty());

        // if item don't exist
        lootManager.discardItem(deadEntity, new Shield("Wooden Shield",
                150, 50, "SOMEWHERE", 20, 80, RarityLevel.COMMON));
        assertEquals(lootInventory.getItems().size(), lootInventory.getInventorySize());

        // items exists
        for (int i = 1; i < lootInventory.getItems().size() + 1; i++) {
            lootManager.discardItem(deadEntity, lootManager.getLoot(deadEntity).getItemFromSlot(i));
            assertFalse(lootInventory.containsItem(lootManager.getLoot(deadEntity).getItemFromSlot(i)));
        }
        // wallet remains
        launchGameLoop();
        assertTrue(!lootManager.getAllUnlooted().isEmpty());
        lootManager.discardCoins(lootInventory);
        assertEquals(GameLoop.getCurrentGameManager().getGameWallet()
                .getBalance(), 0);
        assertTrue(lootManager.getAllUnlooted().isEmpty());
    }

    @Test
    public void testEmptyDiscardItem_2args() {
        when(mapMock.getCurrentTurnHero()).thenReturn(currentHeroMock);
        when(currentHeroMock.getInventory()).thenReturn(new HeroInventory(5));
        DeathMagma deadEntity = new DeathMagma(5, 5);
        lootManager.makeLoot(deadEntity, LootRarity.RANDOM);
        LootInventory lootInventory = lootManager.getLoot(lootManager.getAllUnlooted().get(0));
        assertFalse(lootManager.getAllUnlooted().isEmpty());
        
        // item don't exist
         assertFalse(lootManager.takeItemFromLoot(deadEntity, new Shield("Wooden Shield",
                150, 50, "SOMEWHERE", 20, 80, RarityLevel.COMMON)));
         assertEquals(lootInventory.getItems().size(), lootInventory.getInventorySize());

        // discard wallet
        int lootBalance = lootManager.getLoot(deadEntity).getCoinBalance();
        assertTrue(!lootManager.getAllUnlooted().isEmpty());
        lootManager.discardCoins(lootInventory);
        assertTrue(!lootManager.getAllUnlooted().isEmpty());

        // discard items
        for (int i = 1; i < lootInventory.getItems().size() + 1; i++) {
            Item item = lootInventory.getItemFromSlot(i);
            if (lootInventory.containsItem(item) && item != null) {
                lootManager.discardItem(deadEntity, item);
                assertFalse(lootInventory.containsItem(item));
            }
        }
        assertTrue(lootManager.getAllUnlooted().isEmpty());
    }
}
