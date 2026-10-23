public class StarTriangleN {
    /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on.
     */
    public static String starLine(int star,int N){
        StringBuilder s = new StringBuilder();
        for (int i=0; i<N; ++i) {
            if (i < N - star) s.append(" ");
            else s.append("*");
        }
        return s.toString();
    }
    public static void starTriangle(int N) {
        for (int i = 1; i <= N; ++i) {
            IO.println(starLine(i,N));
        }
    }

    void main() {
        starTriangle(7);
    }
}
