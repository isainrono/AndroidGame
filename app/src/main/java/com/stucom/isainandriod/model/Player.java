package com.stucom.isainandriod.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Player {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("from")
    private String from;
    @SerializedName("totalScore")
    private int totalScore;
    @SerializedName("lastLevel")
    private int lastLevel;
    @SerializedName("lastScore")
    private int lastScore;
    @SerializedName("Dwarf")
    private int dwarf;


    public Player(int id, String name, String image, String from, int totalScore, int lastLevel, int lastScore){
        this.id = id;
        this.name = name;
        this.image = image;
        this.from = from;
        this.totalScore = totalScore;
        this.lastLevel = lastLevel;
        this.lastScore = lastScore;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }

    public int getLastScore() {
        return lastScore;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    public boolean isDwarf() {
        return (dwarf == 1);
    }

    public void setDwarf(boolean dwarf) {
        this.dwarf = dwarf ? 1 : 0;
    }

    public static class customComparator implements Comparator<Player>{

        @Override
        public int compare(Player o1, Player o2) {
            int p1 = o1.totalScore;
            int p2 = o2.totalScore;
            if(p1 == p2) return 0;
            if(p1 > p2) return -1;
            else return 1;
        }
    }
}
