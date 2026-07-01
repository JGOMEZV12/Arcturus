package com.eu.habbo.habbohotel.roleplay.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * InventoryManager: Manages user inventory and items.
 */
public class InventoryManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryManager.class);
    private final int userId;
    private final ConcurrentHashMap<Integer, InventoryItem> items;
    private static final int MAX_INVENTORY_SIZE = 64;

    public InventoryManager(int userId) {
        this.userId = userId;
        this.items = new ConcurrentHashMap<>();
    }

    /**
     * Add item to inventory
     */
    public boolean addItem(int itemId, int quantity) {
        if (isFull()) {
            LOGGER.warn("Inventory full for user {}", userId);
            return false;
        }

        InventoryItem existing = items.get(itemId);
        if (existing != null) {
            existing.addQuantity(quantity);
        } else {
            items.put(itemId, new InventoryItem(itemId, quantity));
        }

        LOGGER.debug("Item added to inventory - User: {}, ItemID: {}, Quantity: {}", userId, itemId, quantity);
        return true;
    }

    /**
     * Remove item from inventory
     */
    public boolean removeItem(int itemId, int quantity) {
        InventoryItem item = items.get(itemId);
        if (item == null || item.getQuantity() < quantity) {
            LOGGER.warn("Item not found or insufficient quantity - User: {}, ItemID: {}", userId, itemId);
            return false;
        }

        item.removeQuantity(quantity);
        if (item.getQuantity() <= 0) {
            items.remove(itemId);
        }

        LOGGER.debug("Item removed from inventory - User: {}, ItemID: {}, Quantity: {}", userId, itemId, quantity);
        return true;
    }

    /**
     * Get item from inventory
     */
    public InventoryItem getItem(int itemId) {
        return items.get(itemId);
    }

    /**
     * Check if inventory is full
     */
    public boolean isFull() {
        return items.size() >= MAX_INVENTORY_SIZE;
    }

    /**
     * Get inventory size
     */
    public int getSize() {
        return items.size();
    }

    /**
     * Get all items
     */
    public ConcurrentHashMap<Integer, InventoryItem> getItems() {
        return items;
    }

    /**
     * InventoryItem class
     */
    public static class InventoryItem {
        private final int itemId;
        private int quantity;

        public InventoryItem(int itemId, int quantity) {
            this.itemId = itemId;
            this.quantity = quantity;
        }

        public void addQuantity(int amount) { this.quantity += amount; }
        public void removeQuantity(int amount) { this.quantity -= amount; }

        public int getItemId() { return itemId; }
        public int getQuantity() { return quantity; }
    }
}
