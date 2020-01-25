package dsandalgo.string;

import java.util.ArrayList;
import java.util.List;

public class StringIterator {

    class CharCount{
        Character ch;
        int count;
        public CharCount(char _ch, int _count){
            ch = _ch;
            count = _count;
        }
    }

    List<CharCount> list;
    int idx = 0;
    int curCharCounter = 0;

    public StringIterator(String compressedString) {
        list = new ArrayList<CharCount>();
        StringBuilder intVals = new StringBuilder();
        Character curChar = null;
        for (int i = 0; i<compressedString.length();i++) {
            if (compressedString.charAt(i) <= '9' && compressedString.charAt(i) >= '0') {
                intVals.append(compressedString.charAt(i));
            } else {
                if (intVals.length() != 0) {
                    CharCount cc = new CharCount(curChar, Integer.valueOf(intVals.toString()));
                    list.add(cc);
                    intVals = new StringBuilder();
                }
                curChar = compressedString.charAt(i);
            }
        }
        CharCount cc = new CharCount(curChar, Integer.valueOf(intVals.toString()));
        list.add(cc);
        idx = 0;
        curCharCounter = 0;
    }

    public char next() {
        if (idx < list.size()) {
            if (curCharCounter >= list.get(idx).count) {
                curCharCounter = 0;
                idx++;
            }
            if (idx == list.size()) {
                return ' ';
            } else {
                curCharCounter++;
                return list.get(idx).ch;
            }
        } else {
            return ' ';
        }
    }

    public boolean hasNext() {
        if (idx < list.size() - 1) {
            return true;
        } else {
            if (idx == list.size() - 1) {
                return curCharCounter < list.get(idx).count;
            } else {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        StringIterator exe = new StringIterator("L1e2t1C1o1d1e1");
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
        System.out.println(exe.next());
    }
}
