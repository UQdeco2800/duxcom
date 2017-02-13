package uq.deco2800.duxcom.abilities;

import javafx.application.Platform;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.tiles.Tile;

/**
 * Detached class for handling the Ability Animation Runtime
 * <p>
 * Created by jay-grant on 21/10/2016.
 */
public class AbilityAnimationHandler {

    /**
     * Some shit
     * <p>
     * Target type:
     * 1 -> Friend
     * 2 -> Foe
     * 3 -> Point
     *
     * @param owner             the Caster of the AbstractAbility
     * @param ability           the AbstractAbility
     * @param targetX           the target x int
     * @param targetY           the target y int
     * @param gameManager       the GameManager
     * @param targetDesignation the TargetDesignation
     */
    public static void executeCompleteTurn(AbstractCharacter owner, AbstractAbility ability,
                                           int targetX, int targetY,
                                           GameManager gameManager, TargetDesignation targetDesignation) {

        // Apply cast indicator
        displayCastIndicator(gameManager, ability, owner);

        // Apply attack indicator
        displayAbilityIndicator(gameManager, targetX, targetY);

        Targetable target;
        Tile targetTile = gameManager.getMap().getTile(targetX, targetY);

        if (targetDesignation == TargetDesignation.FRIEND) {
            // Friend
            target = (AbstractHero) targetTile.getEntities().get(0);
            ability.useOnFriend(owner, target);
        } else if (targetDesignation == TargetDesignation.FOE) {
            // Foe
            AbstractCharacter characterTarget;
            if (!targetTile.getEntities().isEmpty()) {
                target = (Targetable) targetTile.getEntities().get(0);
            } else {
                target = (Targetable) targetTile.getLiveTile();
            }
            gameManager.setCurrentAbilityTarget(targetX, targetY, false);
            gameManager.setTurnGraphicsChanged(true);
            ability.useOnFoe(owner, target);

            // Case of: Hero attacks Enemy
            if (target instanceof AbstractEnemy) {
                characterTarget = (AbstractEnemy) target;
                gameManager.processEnemyHitsAndDeaths((AbstractEnemy) characterTarget);
                if (characterTarget.getHealth() <= 0) {
                    gameManager.getGameState().updateEnemyKills(((AbstractEnemy) target).getEnemyType(), 1);
                }
            }
        } else {
            // Point
            ability.useOnPoint(owner, targetX, targetY, 0);
        }

        gameManager.setGameChanged(true);
        delay(500);
        gameManager.setCurrentAbilityTarget(-1, -1, false);
    }

    private static void displayCastIndicator(GameManager gameManager, AbstractAbility ability, AbstractCharacter owner) {
        gameManager.setCurrentAbilityCast(ability);
        gameManager.setCurrentAbilityOwner(owner.getX(), owner.getY());
        gameManager.setTurnGraphicsChanged(true);
        delay(600);
        gameManager.setCurrentAbilityOwner(-1, -1);
        gameManager.setTurnGraphicsChanged(true);
    }

    private static void displayAbilityIndicator(GameManager gameManager, int targetX, int targetY) {
        gameManager.setCurrentAbilityTarget(targetX, targetY, true);
        gameManager.setTurnGraphicsChanged(true);
        delay(600);
        gameManager.setCurrentAbilityTarget(-1, -1, false);
        gameManager.setTurnGraphicsChanged(true);
    }

    /**
     * Perform a Delay for the current thread.
     *
     * @param millisec duration of delay in milliseconds
     */
    public static void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            Platform.exit();
            Thread.currentThread().interrupt();
        }
    }
}
