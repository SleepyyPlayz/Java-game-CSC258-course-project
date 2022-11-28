package entities; /** relevant imports*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * The entities.PlayerData class
 */
public class PlayerData implements Serializable {
    /** Stores the health of the entities.PlayerData object */
    public static final int MAXHEALTH = 500;

    /** Stores the attack power of the entities.PlayerData object */
    private int attackPower;

    /** The current event */
    private int eventID;

    /** The username of the player */
    private final String username;

    /** The inventory of the entities.PlayerData stores the name of the item mapped to the entities.ItemData object */
    private HashMap<String, ArrayList<ItemData>> inventory;

    /**
     * Constructor
     * @param username The username of the Player
     * @param eventID The current event
     */
    public PlayerData(String username, int eventID, int ap, HashMap<String, ArrayList<ItemData>> inventory) {
        this.inventory = inventory;
        this.eventID = eventID;
        this.username = username;
        this.attackPower = ap;
    }

    /**
     * Returns the value of the entities.PlayerData object's Attack Power
     * @return attackPower
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Updates the entities.PlayerData's attack power
     * @param attackPower the new value for attackPower
     */
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    /**
     * Returns the eventID of the entities.PlayerData object
     * @return eventID
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * Updates the eventID
     * @param eventID the new value of the eventID
     */
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    /**
     * Returns the username of the entities.PlayerData object
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Adds an entities.ItemData object to the entities.PlayerData object's inventory.
     * Creates a new list of entities.ItemData if there is not already one.
     * @param item The entities.ItemData object that is added to the entities.PlayerData's inventory.
     */
    public void addToInventory(ItemData item) {
        // if the item is not already in the map, create a list for it.
        if (!inventory.containsKey(item.getName())) {
            inventory.put(item.getName(), new ArrayList<ItemData>());
        }

        // add the item to the list in the hashmap.
        inventory.get(item.getName()).add(item);
    }

    /**
     * Removes an entities.ItemData object from the entities.PlayerData object's inventory.
     * @param item The entities.ItemData object that is removed from the inventory.
     * @return removed Whether the entities.ItemData object was removed.
     */
    public boolean removeFromInventory(ItemData item) {
        // boolean to determine if the value was returned successfuly.
        boolean removed = false;
        // assign a pointer to the list contained in the hashmap
        ArrayList<ItemData> pointer = inventory.get(item.getName());

        // removes the item if it is in the inventory
        removed = pointer.remove(item);

        // if the list is empty, we will entirely remove the entry from the hashmap
        if (pointer.isEmpty()) {
            inventory.remove(item.getName());
        }

        // returns whether this was successful
        return removed;
    }


    /**
     * Returns the number of items of a type in a list.
     * @param item The entities.ItemData object that we count in the inventory.
     * @return the number of instances of the item type in the inventory.
     */
    public int itemCount(ItemData item) {
        return inventory.get(item.getName()).size();
    }

    /**
     * Returns a list of the ItemData objects.
     * @return items
     */
    public ArrayList<ItemData> getInventoryItems() {
        // the list of ItemData objects
        ArrayList<ItemData> items = new ArrayList<>();

        // looping through the values of the hashmap
        for (ArrayList<ItemData> itemList : inventory.values()) {
            // looping through the values in each list
            for (ItemData item: itemList) {
                items.add(item);
            }
        }
        return items;
    }
}
