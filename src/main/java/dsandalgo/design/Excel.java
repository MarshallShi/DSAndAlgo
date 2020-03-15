package dsandalgo.design;

/**
 * https://leetcode.com/problems/design-excel-sum-formula/
 *
 */
public class Excel {

    class Cell {
        boolean isNum;
        Integer val;
        String[] formula;

        public void setFormula(String[] formula) {
            this.formula = formula;
            isNum = false;
        }

        public int calFormula() {
            int sum = 0;
            for (String s : formula) {
                int c1 = s.charAt(0) - 'A';
                if (s.length() > 3) {
                    int i = 1;
                    while (s.charAt(i) != ':') {
                        i++;
                    }
                    int r1 = Integer.parseInt(s.substring(1, i)) - 1;
                    int c2 = s.charAt(i + 1) - 'A';
                    int r2 = Integer.parseInt(s.substring(i + 2)) - 1;
                    for (i = r1; i <= r2; i++) {
                        for (int j = c1; j <= c2; j++) {
                            if (grid[i][j].isNum) {
                                sum += grid[i][j].val;
                            } else {
                                sum += grid[i][j].calFormula();
                            }
                        }
                    }
                } else {
                    int r1 = Integer.parseInt(s.substring(1)) - 1;
                    if (grid[r1][c1].isNum) {
                        sum += grid[r1][c1].val;
                    } else {
                        sum += grid[r1][c1].calFormula();
                    }
                }
            }

            val = sum;
            return val;
        }

        public void setNum(int val) {
            this.val = val;
            isNum = true;
        }
    }

    Cell[][] grid;

    public Excel(int H, char W) {
        grid = new Cell[H][W - 'A' + 1];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Cell();
                grid[i][j].setNum(0);
            }
        }
    }

    public void set(int r, char c, int v) {
        grid[r - 1][c - 'A'].setNum(v);
    }

    public int get(int r, char c) {
        if (grid[r - 1][c - 'A'].isNum) {
            return grid[r - 1][c - 'A'].val;
        } else {
            return grid[r - 1][c - 'A'].calFormula();
        }
    }

    public int sum(int r, char c, String[] strs) {
        grid[r - 1][c - 'A'].setFormula(strs);
        return grid[r - 1][c - 'A'].calFormula();
    }
}
