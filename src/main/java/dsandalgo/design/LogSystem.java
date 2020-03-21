package dsandalgo.design;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/design-log-storage-system/
 */
public class LogSystem {

    private List<String> allLogTimeStamp;
    private List<Integer> allLogId;

    public LogSystem() {
        allLogTimeStamp = new ArrayList<>();
        allLogId = new ArrayList<>();
    }

    public void put(int id, String timestamp) {
        allLogTimeStamp.add(timestamp);
        allLogId.add(id);
    }

    public List<Integer> retrieve(String s, String e, String gra) {
        List<Integer> res=new ArrayList<>();
        int digits=-1;

        switch (gra) {
            case "Year":
                digits=4;
                break;
            case "Month":
                digits=7;
                break;
            case "Day":
                digits=10;
                break;
            case "Hour":
                digits=13;
                break;
            case "Minute":
                digits=16;
                break;
            case "Second":
                digits=19;
        }

        for (int i = 0; i< allLogTimeStamp.size(); i++) {
            String temp = allLogTimeStamp.get(i);
            for(int j=0; j<digits; j++){
                //Compare the string with the start and digits, the start and digits being chopped based on gra.
                if (temp.substring(0,digits).compareTo(s.substring(0,digits))>=0  && temp.substring(0,digits).compareTo(e.substring(0,digits))<=0){
                    if ( j == digits-1)
                        res.add(allLogId.get(i));
                    continue;
                } else {
                    break;
                }
            }
        }

        return res;
    }

}
