package io.neetcode.stack;

import java.util.Stack;

public class ValidParentheses {
    public boolean isValid(String s) {
        Stack<Character> st = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '{' || c == '[' || c == '(') st.push(c);
            else if (st.isEmpty()) return false;
            else {
                char ch = st.pop();
                if (c == '}' && ch != '{') return false;
                if (c == ']' && ch != '[') return false;
                if (c == ')' && ch != '(') return false;
            }
        }
        return st.isEmpty();
    }

    public boolean isValid(String s) {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[' || c == '{' || c == '(') {
                st.push(c);
                continue;
            }
            switch (c) {
                case ']':
                    if (!st.isEmpty() && st.peek().equals('[')) st.pop();
                    else return false;
                    break;
                case '}':
                    if (!st.isEmpty() && st.peek().equals('{')) st.pop();
                    else return false;
                    break;
                case ')':
                    if (!st.isEmpty() && st.peek().equals('(')) st.pop();
                    else return false;
                    break;
                default:
                    break;
            }
        }
        return st.isEmpty();
    }
}
