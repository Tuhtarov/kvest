package com.example.kvest;

public class Quest {
    private String questName;
    private String questDescription;
    private double questLatitude;
    private double questLongitude;

    public Quest(String newName, String newDescription, double newLatitude, double newLongitude)
    {
this.questName = newName;
this.questDescription = newDescription;
this.questLatitude = newLatitude;
this.questLongitude = newLongitude;
    }
    public  String getQuestName(){
        return  questName;

    }
    public String getQuestDescription()
    {
        return  questDescription;
    }
    public double getQuestLatitude(){
        return questLatitude;
    }
    public double getQuestLongitude()
    {
        return questLongitude;
    }
}
