package dsandalgo.design;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TopVotedCandidate {

    Map<Integer, Integer> m = new HashMap<>();

    int[] time;

    public TopVotedCandidate(int[] persons, int[] times) {
        int n = persons.length, lead = -1;
        Map<Integer, Integer> count = new HashMap<>();
        time = times;
        for (int i = 0; i < n; ++i) {
            count.put(persons[i], count.getOrDefault(persons[i], 0) + 1);
            if (i == 0 || count.get(persons[i]) >= count.get(lead)) {
                lead = persons[i];
            }
            m.put(times[i], lead);
        }
    }

    public int q(int t) {
        int i = Arrays.binarySearch(time, t);
        if (i < 0) {
            return m.get(time[-i-2]);
        } else {
            return m.get(time[i]);
        }
    }


    public static void main(String[] args) {
        int[] times = {0,6,39,52,75};
        int[] person = {0,0,0,0,1};
        TopVotedCandidate exe = new TopVotedCandidate(person, times);
        System.out.println(exe.q(59));
        System.out.println(exe.q(68));
        System.out.println(exe.q(42));
        System.out.println(exe.q(37));
        System.out.println(exe.q(99));
        System.out.println(exe.q(26));
        System.out.println(exe.q(78));
        System.out.println(exe.q(43));
    }
}
