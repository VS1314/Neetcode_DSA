package io.neetcode.stack;

import java.util.Stack;

public class SimplifyPath {

    public String simplifyPath(String path) {
        String[] tokens = path.split("/");
        String[] stack = new String[tokens.length];
        int top = 0;
        for (String i : tokens) {
            if (i.equals("") || i.equals("."))
                continue;
            else if (i.equals("..")) {
                if (top > 0)
                    top--;
            } else
                stack[top++] = i;
        }
        StringBuilder sb = new StringBuilder();
        if (top == 0)
            return "/";
        for (int i = 0; i < top; i++) {
            sb.append("/").append(stack[i]);
        }
        return sb.toString();
    }

    public String simplifyPath(String path) {
        Stack<String> s = new Stack<>();
        String[] ans = path.split("/");
        for (String i : ans) {
            if (i.equals("") || i.equals("."))
                continue;
            else if (i.equals("..")) {
                if (!s.isEmpty())
                    s.pop();
            } else
                s.push(i);
        }
        return "/" + String.join("/", s);
    }
}
