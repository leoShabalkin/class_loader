package sample.impl;

import sample.StatelessClass;

/**
 * Created by Leonid_Shabalkin on 24/10/2016.
 */
public class StatelessClassImpl implements StatelessClass {
    @Override
    public void print(String word) {
        System.out.println(word);
    }
}