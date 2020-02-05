package dsandalgo.design;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PeekingIterator implements Iterator<Integer> {

    List<Integer> lst;
    int cur;

    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        lst = new ArrayList<Integer>();
        while (iterator.hasNext()) {
            lst.add(iterator.next());
        }
        cur = -1;
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        if (cur != lst.size() - 1) {
            return lst.get(cur + 1);
        }
        return -1;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        if (hasNext()) {
            cur++;
            return lst.get(cur);
        }
        return -1;
    }

    @Override
    public boolean hasNext() {
        if (cur + 1 < lst.size()) {
            return true;
        }
        return false;
    }
}
