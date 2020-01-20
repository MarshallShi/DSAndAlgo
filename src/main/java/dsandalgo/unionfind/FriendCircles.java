package dsandalgo.unionfind;

public class FriendCircles {

    public int findCircleNum(int[][] M) {
        if (M.length == 1) {
            return 1;
        }
        UionFindFC uffc = new UionFindFC(M.length);
        for (int i=0; i<M.length; i++) {
            for (int j=0; j<M[0].length; j++) {
                if (M[i][j] == 1) {
                    uffc.union(i,j);
                }
            }
        }
        return uffc.count;
    }

    public class UionFindFC{

        int[] parent;
        int count;

        public UionFindFC(int n) {
            count = n;
            parent = new int[n];
            for (int i=0; i<n; i++) {
                parent[i] = i;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            parent[i] = find(parent[i]);
            return parent[i];
        }

        public void union(int i, int j) {
            int parentI = find(i);
            int parentJ = find(j);
            if (parentI != parentJ) {
                parent[parentI] = parentJ;
                count--;
            }
        }
    }

    public static void main(String[] args) {

        int[][] input = {{1,1,0},{1,1,0},{0,0,1}};

        FriendCircles fc = new FriendCircles();
        System.out.println(fc.findCircleNum(input));

    }
}
