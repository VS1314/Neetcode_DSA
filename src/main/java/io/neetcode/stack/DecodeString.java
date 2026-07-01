package io.neetcode.stack;

import java.util.Stack;

public class DecodeString {

    public String decodeString(String s) {
        int[] numstack = new int[30];
        String[] strstack = new String[30];
        int num = 0;
        int top = 0;
        StringBuilder sb = new StringBuilder();
        for (char i : s.toCharArray()) {
            if (Character.isDigit(i))
                num = num * 10 + (i - '0');
            else if (i == '[') {
                numstack[top] = num;
                strstack[top] = sb.toString();
                top++;
                num = 0;
                sb.setLength(0);
            } else if (i == ']') {
                String prevstr = strstack[top - 1];
                int prevnum = numstack[top - 1];
                top--;
                String currstr = sb.toString();
                sb.setLength(0);
                sb.append(prevstr);
                for (int k = 0; k < prevnum; k++) {
                    sb.append(currstr);
                }
            } else
                sb.append(i);
        }
        return sb.toString();
    }

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
