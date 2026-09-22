import java.util.*;
public class Game2048 {
    static int[][] a = new int[4][4];
    static Random r = new Random();

    static void add() {
        ArrayList<int[]> empty = new ArrayList<>();

        for (int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                if(a[i][j]==0)
                    empty.add(new int[]{i,j});

        if (!empty.isEmpty()) {
            int[] p = empty.get(r.nextInt(empty.size()));
            a[p[0]][p[1]] = r.nextBoolean() ? 2 : 4;
        }
    }

    static void show() {
        System.out.println("\n+----+----+----+----+");

        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++)
                System.out.printf("|%4d", a[i][j]);

            System.out.println("|");
            System.out.println("+----+----+----+----+");
        }
    }

    static void left() {
        for(int i=0;i<4;i++) {
            int[] row = new int[4];
            int k=0;

            for(int j=0;j<4;j++)
                if(a[i][j]!=0)
                    row[k++]=a[i][j];

            for(int j=0;j<3;j++)
                if(row[j]!=0 && row[j]==row[j+1]) {
                    row[j]*=2;
                    row[j+1]=0;
                }

            int[] result = new int[4];
            k=0;

            for(int value:row)
                if(value!=0)
                    result[k++]=value;

            a[i]=result;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        add();
        add();

        System.out.println("===== 2048 GAME =====");
        System.out.println("W = LEFT | E = RIGHT | Q = EXIT");

        while(true) {
            show();
            System.out.print("Move: ");

            char c = sc.next().toLowerCase().charAt(0);

            if(c=='q') break;

            if(c=='w') left();

            if(c=='e') {
                for(int i=0;i<4;i++)
                    for(int j=0;j<2;j++) {
                        int temp=a[i][j];
                        a[i][j]=a[i][3-j];
                        a[i][3-j]=temp;
                    }

                left();

                for(int i=0;i<4;i++)
                    for(int j=0;j<2;j++) {
                        int temp=a[i][j];
                        a[i][j]=a[i][3-j];
                        a[i][3-j]=temp;
                    }
            }

            add();
        }

        sc.close();
    }
}