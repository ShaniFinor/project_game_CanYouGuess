package com.example.shanifinor_project.ui.common;

public class Common {
    public static char[] user_submit_answer;
    public static int count = 0;
    public static String[] alphabet_character =
            {"a", "b", "c", "d", "e", "f", "g", "h",
                    "i", "j", "k", "l", "m", "n", "o",
                    "p", "q", "r", "s", "t", "u", "v",
                    "w", "x", "y", "z"};

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Common.count = count;
    }

    public static char[] getUser_submit_answer() {
        return user_submit_answer;
    }

    public static void setUser_submit_answer(char[] user_submit_answer) {
        Common.user_submit_answer = user_submit_answer;
    }

    public static String[] getAlphabet_character() {
        return alphabet_character;
    }

    public static void setAlphabet_character(String[] alphabet_character) {
        Common.alphabet_character = alphabet_character;
    }
}
