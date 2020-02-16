package dsandalgo.design;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SnapshotArray {

    TreeMap<Integer, Integer>[] dataWithSnapshot;
    int snap_id = 0;

    public SnapshotArray(int length) {
        dataWithSnapshot = new TreeMap[length];
        for (int i = 0; i < length; i++) {
            dataWithSnapshot[i] = new TreeMap<Integer, Integer>();
            dataWithSnapshot[i].put(0, 0);
        }
    }

    public void set(int index, int val) {
        dataWithSnapshot[index].put(snap_id, val);
    }

    public int snap() {
        return snap_id++;
    }

    public int get(int index, int snap_id) {
        return dataWithSnapshot[index].floorEntry(snap_id).getValue();
    }
}
