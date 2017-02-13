package uq.deco2800.duxcom.objectives;

import uq.deco2800.duxcom.items.ItemType;

/**
 * Represents an objective for collecting a certain amount
 * of a particular item.
 * @require type != null && targetValue > 0
 * @ensure description = "Collect -value- -itemName-s"
 * Created by Tyler Beutel on 9/14/2016.
 */
public class ItemObjective extends Objective {

    public ItemObjective(ItemType type, int targetValue) {
        super(type, targetValue, "Collect " + targetValue + " " +
                type.toString().toLowerCase() + "s.");
    }

}