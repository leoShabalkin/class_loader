package sample.impl;

import sample.StatelessClass;

/**
 * Created by Leonid_Shabalkin on 24/10/2016.
 */
public class StatelessClassBadImpl implements StatelessClass {
    private static long timeout = 5*60*1000;

    @Override
    public void print(String word) {
        System.out.println(word + timeout);
    }

    public static long getTimeout() {
        return timeout;
    }
}