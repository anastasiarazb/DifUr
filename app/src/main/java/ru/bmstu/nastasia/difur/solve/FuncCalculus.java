package ru.bmstu.nastasia.difur.solve;

import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;

public class FuncCalculus {

    public static Double[] calcY(Function f, Double[] x) {
        Double[] y = new Double[x.length];
        for (int i = 0; i < y.length; ++i) {
            y[i] = f.calculate(x[i]);
        }
        return y;
    }

    public static Double[][] calcArrayY(ArrayList<Function> y, Double[] x) {
        Double[][] Y = new Double[y.size()][];
        for (int i = 0; i < y.size(); ++i) {
            Function f = y.get(i);
            if (f == null) {
                continue;
            }
            Y[i] = calcY(f, x);
        }
        return Y;
    }
}
