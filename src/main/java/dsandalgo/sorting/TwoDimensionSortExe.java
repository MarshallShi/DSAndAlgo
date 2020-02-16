package dsandalgo.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class TwoDimensionSortExe {

    public static void main(String[] args){
        TwoDimensionSortExe exe = new TwoDimensionSortExe();
        //["h","eiy","cq","h","cq","txldsscx","cq","txldsscx","h","cq","cq"]
        //[527896567,334462937,517687281,134127993,859112386,159548699,51100299,444082139,926837079,317455832,411747930]
        //["hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","yljmntrclw","hibympufi","yljmntrclw"]
        String[] u = {"h","eiy","cq","h","cq","txldsscx","cq","txldsscx","h","cq","cq"};
        int[] t = {527896567,334462937,517687281,134127993,859112386,159548699,51100299,444082139,926837079,317455832,411747930};
        String[] w = {"hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","yljmntrclw","hibympufi","yljmntrclw"};
        System.out.println(exe.mostVisitedPattern(u,t,w));
    }

    /**
     * https://leetcode.com/problems/analyze-user-website-visit-pattern/
     */
    class VisitedTime{
        String website;
        int time;
        public VisitedTime(String _website, int _time){
            website = _website;
            time = _time;
        }
    }
    class Pair{
        String threeSequence;
        int counter;
        public Pair(String _threeSequence, int _counter){
            threeSequence = _threeSequence;
            counter = _counter;
        }
    }
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        Map<String, List<VisitedTime>> userVisitWebsiteMap = new HashMap<String, List<VisitedTime>>();
        int n = username.length;
        for (int i=0; i<n; i++) {
            userVisitWebsiteMap.putIfAbsent(username[i], new ArrayList<VisitedTime>());
            userVisitWebsiteMap.get(username[i]).add(new VisitedTime(website[i], timestamp[i]));
        }
        Map<String, Integer> threeSequenceCounter = new HashMap<String, Integer>();
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1.counter == o2.counter) {
                    return o1.threeSequence.compareTo(o2.threeSequence);
                }
                return o2.counter - o1.counter;
            }
        });
        for (Map.Entry<String, List<VisitedTime>> entry : userVisitWebsiteMap.entrySet()) {
            List<VisitedTime> data = entry.getValue();
            Collections.sort(data, new Comparator<VisitedTime>() {
                @Override
                public int compare(VisitedTime o1, VisitedTime o2) {
                    return o1.time - o2.time;
                }
            });
            int s = data.size();
            Set<String> visited = new HashSet<String>();
            for (int i=0; i<s-2; i++) {
                for (int j=i+1; j<s-1; j++) {
                    for (int k=j+1; k<s; k++) {
                        String threeSeq = data.get(i).website + "," + data.get(j).website + "," + data.get(k).website;
                        if (visited.contains(threeSeq)) {
                            continue;
                        }
                        threeSequenceCounter.putIfAbsent(threeSeq, 0);
                        threeSequenceCounter.put(threeSeq, threeSequenceCounter.get(threeSeq) + 1);
                        visited.add(threeSeq);
                    }
                }
            }
        }
        for (Map.Entry<String,Integer> entry : threeSequenceCounter.entrySet()) {
            pq.offer(new Pair(entry.getKey(), entry.getValue()));
        }
        String[] ret = pq.poll().threeSequence.split(",");
        List<String> ans = new ArrayList<String>();
        for (String str : ret) {
            ans.add(str);
        }
        return ans;
    }
}
