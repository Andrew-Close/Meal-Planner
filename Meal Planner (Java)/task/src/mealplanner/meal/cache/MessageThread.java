package mealplanner.meal.cache;

import mealplanner.meal.datamanager.DataManager;

/**
 * This class is the thread that caches the message to be used with the show operation. This class is used with the MessageCache class.
 */
public class MessageThread extends Thread {
    // Holds the cached message, but it is passed to the MessageCache after the message is cached. This exists just because the run method can only return void
    private String cache;

    public MessageThread() {
        super();
    }

    @Override
    public void run() {
        cache = DataManager.getMessage();
    }

    String getCache() {
        return cache;
    }
}
