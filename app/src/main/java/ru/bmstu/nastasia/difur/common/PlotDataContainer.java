package ru.bmstu.nastasia.difur.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class PlotDataContainer {

    public static class ParamNames {
        final public static String y = "y array";
        final public static String x = "x array";
        final public static String y2 = "y2 array";
        final public static String equation = "equation";
        final public static String user_solution = "user solution";
    }

    public Double[] getY() {
        return y;
    }

    public Double[] getY2() {
        return y2;
    }

    public Double[] getX() {
        return x;
    }

    public String getEquation() {
        return equation;
    }

    public String getUserSolution() {
        return user_solution;
    }

    private Double[] y;
    private Double[] y2;
    private Double[] x;

    private String equation;
    private String user_solution;

    public boolean hasUserSolution() {
        return user_solution != null;
    }

    public PlotDataContainer(Double[] y, Double[] x, String equation,
                             @Nullable Double[] y2, @Nullable String user_solution) {
        this.x = x;
        this.y = y;
        this.y2 = y2;
        this.equation = equation;
        this.user_solution = user_solution;
    }

    public static ArrayList<PlotDataContainer> genArray(@NonNull Bundle bundle) {
        if (!bundle.containsKey(ParamNames.y)
                || !bundle.containsKey(ParamNames.x)
                || !bundle.containsKey(ParamNames.equation)
                || (bundle.containsKey(ParamNames.y2) != bundle.containsKey(ParamNames.user_solution))) {
            throw new IllegalArgumentException("PlotDataContainer: bundle is not filled properly");
        }
        Double[][] yy = (Double[][])bundle.get(ParamNames.y);
        Double[] x = (Double[])bundle.get(ParamNames.x);
        String[] equations = (String[])bundle.get(ParamNames.equation);

        if (yy == null || x == null || equations == null) {
            throw new IllegalArgumentException("PlotDataContainer: bundle contains a null array");
        }

        int n = equations.length;

        ArrayList<PlotDataContainer> container = new ArrayList<>(n);

        if (bundle.containsKey(ParamNames.user_solution)) {
            String[] user_solutions = (String[])bundle.get(ParamNames.user_solution);
            Double[][] yy2 = (Double[][])bundle.get(ParamNames.y2);
            if (user_solutions == null || yy2 == null ) {
                throw new IllegalArgumentException("PlotDataContainer: bundle contains a null array");
            }
            for (int i = 0; i < n; ++i) {
                container.add(new PlotDataContainer(yy[i], x, equations[i], yy2[i], user_solutions[i]));
            }
        } else {
            for (int i = 0; i < n; ++i) {
                container.add(new PlotDataContainer(yy[i], x, equations[i], null, null));
            }
        }
        return container;
    }
}
