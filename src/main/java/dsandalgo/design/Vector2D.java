package dsandalgo.design;

import java.util.ArrayList;
import java.util.List;

public class Vector2D {

    private List<Integer> data;
    private int counter;

    public Vector2D(int[][] v) {
        data = new ArrayList<Integer>();
        for (int i=0; i<v.length; i++) {
            for (int j=0; j<v[i].length; j++) {
                data.add(v[i][j]);
            }
        }
        counter = 0;
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
