package ru.bmstu.nastasia.difur.common;

public class FormulaToTex {

    public static String protectSubformulas(String text) {
        return text
                .replaceAll("\\(", "{(")
                .replaceAll("\\)", ")}");
    }
}
