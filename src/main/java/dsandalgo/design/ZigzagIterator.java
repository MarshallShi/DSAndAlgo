package dsandalgo.design;

import java.util.ArrayList;
import java.util.List;

public class ZigzagIterator {

    private List<Integer> data;
    private int counter;

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        data = new ArrayList<Integer>();
        int idx = 0;
        int size = Math.min(v1.size(), v2.size());
        while (idx < size) {
            data.add(v1.get(idx));
            data.add(v2.get(idx));
            idx++;
        }
        List<Integer> remainingList = null;
        if (size == v1.size()) {
            remainingList = v2;
        } else {
            remainingList = v1;
        }
        for (int i=idx; i<remainingList.size(); i++) {
            data.add(remainingList.get(i));
        }
    }

    public int next() {
        int ret = -1;
        if (hasNext()) {
            ret = data.get(counter);
            counter++;
        }
        return ret;
    }

    public boolean hasNext() {
        if (counter < data.size()) {
            return true;
        }
        return false;
    }
}
