package mealplanner.meal.cache;

import mealplanner.meal.datamanager.legacydatamanager.DataManager;

/**
 *
 *
 *
 * !!!
 * WARNING!! Read the javadoc for MessageCache.java
 * !!!
 * <p>
 *
 *
 *
 * This class is the thread that caches the message to be used with the show operation. This class is used with the MessageCache class.
 */
@Deprecated
public class MessageThread extends Thread {
    // Holds the cached message, but it is passed to the MessageCache after the message is cached. This exists just because the run method can only return void
    private String cache;

    public MessageThread() {
        super();
    }

    @Override
    public void run() {
        cache = DataManager.getMealsMessage("breakfast");
    }

    String getCache() {
        return cache;
    }
}
