package com.artyom.physics.model;

public class utils {
    public static int combination(int n, int k){
        return calculateFactorial(n) / (calculateFactorial(k) * calculateFactorial(n - k));
    }

    static int calculateFactorial(int n){
        int result = 1;
        for (int i = 1; i <=n; i ++){
            result = result*i;
        }
        return result;
    }
}
