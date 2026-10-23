public class PrintIndexed {
    /**
     * Prints each character of a given string followed by the reverse of its index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
    public static void printIndexed(String s) {
        int len = s.length();
        StringBuilder ans = new StringBuilder();
        for (int i=0; i<len; ++i){
            char c = s.charAt(i);
            ans.append(c);
            ans.append(len - i - 1);
        }
        IO.println(ans.toString());
    }

    void main() {
        printIndexed("hello");
        printIndexed("cat"); // should print c2a1t0
    }
}
