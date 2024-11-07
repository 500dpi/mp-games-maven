package edu.grinnell.csc207.util.logic;

import java.util.Random;


public class BingoNumbers { 

    int min = 1;
    int max = 48;

    int randomValue;

    public BingoNumbers() {
        this.randomValue = 0;
    }

    public void findRandom() {
        Random rand = new Random();
        this.randomValue = rand.nextInt(max - min + 1) + min;
    }

    public int getRandom() {
        return this.randomValue;
    }
}