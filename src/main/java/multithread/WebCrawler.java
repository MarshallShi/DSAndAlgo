package multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;

/**
 * https://leetcode.com/problems/web-crawler-multithreaded/
 * Given a url startUrl and an interface HtmlParser, implement a Multi-threaded web crawler to crawl all links that are under the same hostname as startUrl.
 *
 * Return all urls obtained by your web crawler in any order.
 *
 * Your crawler should:
 *
 * Start from the page: startUrl
 * Call HtmlParser.getUrls(url) to get all urls from a webpage of given url.
 * Do not crawl the same link twice.
 * Explore only the links that are under the same hostname as startUrl.
 *
 *
 * As shown in the example url above, the hostname is example.org. For simplicity sake, you may assume all urls use http protocol without any port specified. For example, the urls http://leetcode.com/problems and http://leetcode.com/contest are under the same hostname, while urls http://example.org/test and http://example.com/abc are not under the same hostname.
 *
 * The HtmlParser interface is defined as such:
 *
 * interface HtmlParser {
 *   // Return a list of all urls from a webpage of given url.
 *   // This is a blocking call, that means it will do HTTP request and return when this request is finished.
 *   public List<String> getUrls(String url);
 * }
 * Note that getUrls(String url) simulates performing a HTTP request. You can treat it as a blocking function call which waits for a HTTP request to finish. It is guaranteed that getUrls(String url) will return the urls within 15ms.  Single-threaded solutions will exceed the time limit so, can your multi-threaded web crawler do better?
 *
 * Below are two examples explaining the functionality of the problem, for custom testing purposes you'll have three variables urls, edges and startUrl. Notice that you will only have access to startUrl in your code, while urls and edges are not directly accessible to you in code.
 *
 *
 *
 * Follow up:
 *
 * Assume we have 10,000 nodes and 1 billion URLs to crawl. We will deploy the same software onto each node.
 * The software can know about all the nodes. We have to minimize communication between machines and make sure each node does equal amount of work.
 * How would your web crawler design change?
 * What if one node fails or does not work?
 * How do you know when the crawler is done?
 */
public class WebCrawler {

    interface HtmlParser{
        List<String> getUrls(String url);
    }

    public List<String> crawl(String startUrl, final HtmlParser htmlParser) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        ConcurrentSkipListSet<String> seen = new ConcurrentSkipListSet<>();

        queue.offer(startUrl);
        seen.add(startUrl);

        final List<String> result = new ArrayList<>();
        final String host = startUrl.split("/")[2];
        int nThreads = 4;
        final CountDownLatch latch = new CountDownLatch(nThreads);

        final Runnable worker = new Runnable() {
            public void run() {
                try {
                    while(true) {
                        final String url = queue.poll();
                        if (url == null) {
                            break;
                        }
                        result.add(url);
                        for(String nextUrl: htmlParser.getUrls(url)) {
                            if(seen.add(nextUrl) && host.equals(startUrl.split("/")[2])) {
                                queue.offer(nextUrl);
                            }
                        }
                    }
                } finally {
                    latch.countDown();
                }
            }
        };

        for(int i = 0; i < nThreads; i++) {
            new Thread(worker).start();
        }

        try {
            latch.await();
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }
        return result;
    }
}
