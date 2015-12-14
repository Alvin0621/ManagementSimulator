package com.example.administrator.managementsimulator;

import android.app.Application;

/**
 * Created by Administrator on 2015-12-09.
 */
public class Player extends Application {

    private int level = 1;
    private int exp = 0;
    private int year = 1;
    private int date = 1;
    private int money = 50000;
    final static int EXPBASE = 100;
    final static int EXPNEED = 150;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int levelUpCheck(int experience) {
        int check = 0;
        if (level >= 15) {
            return check;
        } else {
            exp += experience;
        }

        while (exp > EXPBASE + EXPNEED * level) {
            exp -= EXPBASE + EXPNEED * level;
            level++;
            check++;
        }
        return check;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void addDate(int totalCost) {
        this.money -= totalCost;
        if (this.date == 12) {
            this.year++;
            this.date = 1;
        } else {
            this.date++;
        }
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
