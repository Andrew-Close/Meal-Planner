package mealplanner.meal.cache;

/**
 *
 *
 *
 * !!!
 * WARNING!! I realized that this whole cache system doesn't work as a thread. Yes, this class uses threads, but main still has to wait for the cache method to complete as this class does
 * not extend Thread. And I tried to get the system to work as a thread, but I couldn't get it to work, so I gave up.
 * !!!
 *
 *
 *
 * This class caches the message to be used with the show operation. It doesn't actually do the caching here. Rather, it uses a MessageThread to cache it. This is so it can use a thread to cache
 * the message while remaining live. If this class was the one which cached the message, then the thread would die after the first cache. So, this class simply creates a new thread everytime it
 * needs to cache the message and discards it afterward.
 */
public class MessageCache {
    // Holds the cached message
    private static String cache;

    /**
     * Caches the message
     * @throws InterruptedException;
     */
    public void cache() throws InterruptedException {
        MessageThread thread = new MessageThread();
        thread.start();
        // If the thread isn't joined, then cache might be set to null
        thread.join();
        cache = thread.getCache();
    }

    public String getCache() {
        return cache;
    }
}
