package ru.bmstu.nastasia.difur.solve;

import android.support.v7.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.Function;

import java.util.Scanner;

import static java.lang.Math.abs;

public class RungeKutta{

    Double[] y_arr;
    Double[] t_arr;

    static void runge(Function yp_func, Double[] t, Double[] y, double dt) {

        for (int n = 0; n < t.length - 1; n++) {
            double dy1 = dt * yp_func.calculate(t[n], y[n]);
            double dy2 = dt * yp_func.calculate(t[n] + dt / 2.0, y[n] + dy1 / 2.0);
            double dy3 = dt * yp_func.calculate(t[n] + dt / 2.0, y[n] + dy2 / 2.0);
            double dy4 = dt * yp_func.calculate(t[n] + dt, y[n] + dy3);
            t[n + 1] = t[n] + dt;
            y[n + 1] = y[n] + (dy1 + 2.0 * (dy2 + dy3) + dy4) / 6.0;
        }
    }

    static double calc_err(Function f, double t, double calc) {
        double actual = f.calculate(t);
        return abs(actual - calc);
    }

    public RungeKutta(Function f, double y1, double y2, double x1, double x2) {
//        f(x, y) = x * sqrt(y)
//        f(t) = (((t^2.0) + 4.0) ^ 2) / 16.0

//        sin(x)^2*y
//        Function f = new Function("f(t) = (((t^2.0) + 4.0) ^ 2) / 16.0");
//        System.out.println(f.checkSyntax());

        int n = 100;
        double dt = (x2-x1)/(double)n;
        t_arr = new Double[n+1];
        y_arr = new Double[n+1];
        t_arr[0] = y1;
        y_arr[0] = y2;
        runge(f, t_arr, y_arr, dt);
    }

    public Double[] getY() {
        return y_arr;
    }

    public Double[] getX() {
        return t_arr;
    }
}
