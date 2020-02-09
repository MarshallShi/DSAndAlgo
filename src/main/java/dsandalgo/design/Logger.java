package dsandalgo.design;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/logger-rate-limiter/
 *
 */
public class Logger {

    int const_delay = 10;
    Map<String, Integer> map;
    public Logger() {
        map = new HashMap<String, Integer>();
    }

    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
     If this method returns false, the message will not be printed.
     The timestamp is in seconds granularity. */
    public boolean shouldPrintMessage(int timestamp, String message) {
        if (map.containsKey(message)) {
            int prev = map.get(message);
            if (timestamp - prev >= const_delay) {
                map.put(message, timestamp);
                return true;
            } else {
                return false;
            }
        } else {
            map.put(message, timestamp);
            return true;
        }
    }
}