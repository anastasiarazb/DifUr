package ru.bmstu.nastasia;

import org.mariuszgromada.math.mxparser.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;

public class SystemRungeKutta {

//    static void runge(Function yp_func, double[] t, double[] y, double dt) {
//
//        for (int n = 0; n < t.length - 1; n++) {
//            double dy1 = dt * yp_func.calculate(t[n], y[n]);
//            double dy2 = dt * yp_func.calculate(t[n] + dt / 2.0, y[n] + dy1 / 2.0);
//            double dy3 = dt * yp_func.calculate(t[n] + dt / 2.0, y[n] + dy2 / 2.0);
//            double dy4 = dt * yp_func.calculate(t[n] + dt, y[n] + dy3);
//            t[n + 1] = t[n] + dt;
//            y[n + 1] = y[n] + (dy1 + 2.0 * (dy2 + dy3) + dy4) / 6.0;
//        }
//    }

    private ArrayList<Function> F;
    private int N;

    public Double[][] getY() {
        int n = Y.length;
        int m = Y[0].length;
        Double[][] res = new Double[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                res[i][j] = Y[i][j];
            }
        }
        return res;
    }

    public Double[] getX() {
        Double[] res = new Double[t.length];
        for (int i = 0; i < t.length; ++i) {
            res[i] = t[i];
        }
        return res;
    }

    private double[][] Y;
    private double[] t;
    private double x1;
    private double x2;


    static double[] sum(double[] A, double[] B) {
        assert A.length == B.length;
        double[] res = new double[A.length];
        for (int i = 0; i < A.length; ++i) {
            res[i] = A[i] + B[i];
        }

        return res;
    }

    static double[] div(double[] A, double b) {
        double[] res = new double[A.length];
        for (int i = 0; i < A.length; ++i) {
            res[i] = A[i] / b;
        }
        return res;
    }

    static double[] mul(double k, double[] L) {
        double[] res = new double[L.length];
        for (int i = 0; i < L.length; ++i) {
            res[i] = k * L[i];
        }
        return res;
    }

    static double[] concat_args(double t, double[] Y) {
        double[] args = new double[Y.length + 1];
        args[0] = t;
        System.arraycopy(Y, 0, args, 1, Y.length);
        return args;
    }

    // Y[n][i] = values iterated by points [n] and functions [i]
    static void runge(ArrayList<Function> F, double[] t, double[][] Y, double h) {
        double[] k1 = new double[F.size()];
        double[] k2 = new double[F.size()];
        double[] k3 = new double[F.size()];
        double[] k4 = new double[F.size()];

        for (int n = 0; n < t.length - 1; ++n) {  //Iterate by points from t
            double[] args = concat_args(t[n], Y[n]);
            for (int i = 0; i < F.size(); ++i) {  //Iterate by functions from F
                k1[i] = h * F.get(i).calculate(args);
            }

            args = concat_args(t[n]+h/2., sum(Y[n], div(k1, 2.)));
            for (int i = 0; i < F.size(); ++i) {  //Iterate by functions F
                k2[i] = h * F.get(i).calculate(args);
            }

            args = concat_args(t[n]+h/2., sum(Y[n], div(k2, 2.)));
            for (int i = 0; i < F.size(); ++i) {  //Iterate by functions F
                k3[i] = h * F.get(i).calculate(args);
            }

            args = concat_args(t[n]+h, sum(Y[n], k3));
            for (int i = 0; i < F.size(); ++i) {  //Iterate by functions F
                k4[i] = h * F.get(i).calculate(args);
            }

            t[n + 1] = t[n] + h;
            double[] multiplier = sum(k1, sum(mul(2., sum(k2, k3)), k4)); // k1+2*(k2+k3)+k4
            Y[n + 1] = sum(Y[n], div(multiplier, 6.0));
        }
    }

    public static double calc_err(Function f, double t, double calc) {
        double actual = f.calculate(t);
        return abs(actual - calc);
    }

    public SystemRungeKutta(ArrayList<Function> functions, double[] inits, double x1, double x2, int n) {
        F = functions;
        N = n+1; // to ensure that the last point is calculated
        t = new double[N];
        t[0] = x1;
        double dt = (x2-x1)/n;
        this.x1 = x1;
        this.x2 = x2;
        Y = new double[N][F.size()];

        for (int j = 0; j < F.size(); ++j) {
            Y[0][j] = inits[j];
        }
        runge(F, t, Y, dt);
    }

//        f(x, y) = x * sqrt(y)
//        f(t) = (((t^2.0) + 4.0) ^ 2) / 16.0
//        int N = 100;
//        t[0] = 0;
//        Y[0][0] = 1.0;
//        double dt = 0.10;

//                "f1(x, y1, y2) = y2",  // y(0) = 3; y'=z
//                "g1(x, y1, y2) = 6*y1-y2"  // z(0) = 1; z'=6y-z

//            t[0] = 0.;
//            Y[0][0] = 3.;
//            Y[0][1] = 1.;
//            int N = 10;
//            double dt = 0.10;
        //        Function real = new Function("f(t) = (((t^2.0) + 4.0) ^ 2) / 16.0");
//        Function real = new Function("f(x) = e^(-3*x)+2*e^(2*x)");

}