package io.neetcode.stack;

import java.util.Stack;

public class DecodeString {
    public String decodeString(String s) {
        Stack<Integer> ns = new Stack<>();
        Stack<String> ss = new Stack<>();
        StringBuilder n = new StringBuilder();
        int num = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '[') {
                ns.push(num);
                num = 0;
                ss.push(n.toString());
                n = new StringBuilder();
            } else if (c == ']') {
                int a = ns.pop();
                String prev = ss.pop();
                StringBuilder temp = new StringBuilder(prev);
                for (int i = 0; i < a; i++) {
                    temp.append(n);
                }
                n = temp;
            } else {
                n.append(c);
            }
        }
        return n.toString();
    }
}
