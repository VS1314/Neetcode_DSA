package io.neetcode.stack;

import java.util.Stack;

public class SimplifyPath {
    public String simplifyPath(String path) {
        Stack<String> s = new Stack<>();
        String[] ans = path.split("/");
        for (String i : ans) {
            if (i.equals("") || i.equals(".")) continue;
            else if (i.equals("..")) {
                if (!s.isEmpty()) s.pop();
            } else s.push(i);
        }
        return "/" + String.join("/", s);
    }
}
