package ru.bmstu.nastasia.difur.solve;

import android.support.v7.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
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


    // f = y'= f(x, y)
    // f(x1)=y1 - начальные условия
    // x2 - правая граница
    public RungeKutta(Function f, double x1, double y1, double x2) {
//        f(x, y) = x * sqrt(y)
//        f(t) = (((t^2.0) + 4.0) ^ 2) / 16.0

//        sin(x)^2*y
//        Function f = new Function("f(t) = (((t^2.0) + 4.0) ^ 2) / 16.0");
//        System.out.println(f.checkSyntax());

        int n = 100;
        double dt = (x2-x1)/(double)n;
        t_arr = new Double[n+1];
        y_arr = new Double[n+1];
        t_arr[0] = x1;
        y_arr[0] = y1;
        runge(f, t_arr, y_arr, dt);
    }

    public Double[] getY() {
        return y_arr;
    }

    public Double[] getX() {
        return t_arr;
    }
}

//package ru.bmstu.nastasia;
//
//        import org.mariuszgromada.math.mxparser.*;
//
//        import java.lang.reflect.Array;
//        import java.util.ArrayList;
//        import java.util.Scanner;
//
//        import static java.lang.Math.abs;
//
//public class Cauchy {
//
////    static void runge(Function yp_func, double[] t, double[] y, double dt) {
////
////        for (int n = 0; n < t.length - 1; n++) {
////            double dy1 = dt * yp_func.calculate(t[n], y[n]);
////            double dy2 = dt * yp_func.calculate(t[n] + dt / 2.0, y[n] + dy1 / 2.0);
////            double dy3 = dt * yp_func.calculate(t[n] + dt / 2.0, y[n] + dy2 / 2.0);
////            double dy4 = dt * yp_func.calculate(t[n] + dt, y[n] + dy3);
////            t[n + 1] = t[n] + dt;
////            y[n + 1] = y[n] + (dy1 + 2.0 * (dy2 + dy3) + dy4) / 6.0;
////        }
////    }
//
//    static double[] sum(double[] A, double[] B) {
//        assert A.length == B.length;
//        double[] res = new double[A.length];
//        for (int i = 0; i < A.length; ++i) {
//            res[i] = A[i] + B[i];
//        }
//
//        return res;
//    }
//
//    static double[] div(double[] A, double b) {
//        double[] res = new double[A.length];
//        for (int i = 0; i < A.length; ++i) {
//            res[i] = A[i] / b;
//        }
//        return res;
//    }
//
//    static double[] mul(double k, double[] L) {
//        double[] res = new double[L.length];
//        for (int i = 0; i < L.length; ++i) {
//            res[i] = k * L[i];
//        }
//        return res;
//    }
//
//    static double[] concat_args(double t, double[] Y) {
//        double[] args = new double[Y.length + 1];
//        args[0] = t;
//        System.arraycopy(Y, 0, args, 1, Y.length);
//        return args;
//    }
//
//    // Y[n][i] = values iterated by points [n] and functions [i]
//    static void runge(ArrayList<Function> F, double[] t, double[][] Y, double h) {
//        double[] k1 = new double[F.size()];
//        double[] k2 = new double[F.size()];
//        double[] k3 = new double[F.size()];
//        double[] k4 = new double[F.size()];
//
//        for (int n = 0; n < t.length - 1; ++n) {  //Iterate by points from t
//            double[] args = concat_args(t[n], Y[n]);
//            for (int i = 0; i < F.size(); ++i) {  //Iterate by functions from F
//                k1[i] = h * F.get(i).calculate(args);
//            }
//
//            args = concat_args(t[n]+h/2., sum(Y[n], div(k1, 2.)));
//            for (int i = 0; i < F.size(); ++i) {  //Iterate by functions F
//                k2[i] = h * F.get(i).calculate(args);
//            }
//
//            args = concat_args(t[n]+h/2., sum(Y[n], div(k2, 2.)));
//            for (int i = 0; i < F.size(); ++i) {  //Iterate by functions F
//                k3[i] = h * F.get(i).calculate(args);
//            }
//
//            args = concat_args(t[n]+h, sum(Y[n], k3));
//            for (int i = 0; i < F.size(); ++i) {  //Iterate by functions F
//                k4[i] = h * F.get(i).calculate(args);
//            }
//
//            t[n + 1] = t[n] + h;
//            double[] multiplier = sum(k1, sum(mul(2., sum(k2, k3)), k4)); // k1+2*(k2+k3)+k4
//            Y[n + 1] = sum(Y[n], div(multiplier, 6.0));
//        }
//    }
//
//    static double calc_err(Function f, double t, double calc) {
//        double actual = f.calculate(t);
//        return abs(actual - calc);
//    }
//
//    public static void main(String[] args) {
////        f(x, y) = x * sqrt(y)
////        f(t) = (((t^2.0) + 4.0) ^ 2) / 16.0
////        int N = 100;
////        t[0] = 0;
////        Y[0][0] = 1.0;
////        double dt = 0.10;
//
//
////        Function real = new Function("f(t) = (((t^2.0) + 4.0) ^ 2) / 16.0");
////        System.out.println(f.checkSyntax());
//
//        Boolean res = Boolean.FALSE;
//
//        if (res) {
//            System.out.println("Loool");
//        }
//
////        Scanner s = new Scanner(System.in);
////        String f_raw = s.nextLine();
////        String real_raw = s.nextLine();
////        Function f = new Function(f_raw);
////        System.out.println(f_raw+" "+f.checkSyntax());
////        Function real = new Function(real_raw);
////        System.out.println(real_raw+" "+real.checkSyntax());
//
//        String[] raw_funcs = new String[] {
//                "f1(x1, y1, z1) = z1",  // y(0) = 3; y'=z
//                "g1(x, y, z) = 6*y-z"  // z(0) = 1; z'=6y-z
//
////            t[0] = 0.;
////            Y[0][0] = 3.;
////            Y[0][1] = 1.;
////            int N = 10;
////            double dt = 0.10;
//        };
//        //        Function real = new Function("f(t) = (((t^2.0) + 4.0) ^ 2) / 16.0");
//        Function real = new Function("f(x) = e^(-3*x)+2*e^(2*x)");
//        System.out.println(real.calculate(1.));
//
//
////        String[] raw_funcs = new String[] {
////                "f(x, y) = x * sqrt(y)"
////        };
//
//        ArrayList<Function> F = new ArrayList<>(raw_funcs.length);
//        for (String s: raw_funcs) {
//            Function f = new Function(s);
//            assert f.checkSyntax();
//            F.add(f);
//        }
//
//
//        int N = 11;
//        double[] t = new double[N];
//
//        double[][] Y = new double[N][raw_funcs.length];
//        t[0] = 0.;
//        Y[0][0] = 3.;
//        Y[0][1] = 1.;
//
////        t[0] = 0;
////        Y[0][0] = 1.0;
//
//        double dt = 0.10;
//
////        double[] t_arr = new double[101];
////        double[] y_arr = new double[101];
////        y_arr[0] = 1.0;
////        runge(f, t_arr, y_arr, dt);
////        for (int i = 0; i < t_arr.length; i++)
////            if (i % 10 == 0)
////                System.out.printf("y(%.1f) = %.8f Error: %.6f\n",
////                        t_arr[i], y_arr[i],
////                        calc_err(real, t_arr[i], y_arr[i]));
//
//
//        runge(F, t, Y, dt);
//        for (int i = 0; i < t.length; i++)
//            if (i % 10 == 0)
//                System.out.printf("y(%.1f) = %.8f Error: %.6f\n",
//                        t[i], Y[i][0],
//                        calc_err(real, t[i], Y[i][0]));
//
//        //TODO: проверить! Почему z9 не сходится!
//        System.out.println(Y[10][0]);
//        System.out.println(t[10]);
//    }
//}