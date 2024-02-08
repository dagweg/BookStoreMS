package model;
public enum Gender{
    Male("Male"), Female("Female");
    private String name;
    private Gender(String name) { this.name = name ;}
    public String getName() { return this.name; }
};

