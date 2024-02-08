package model;


public enum Genre {
    FICTION("Fiction"),
    DRAMA("Drama"),
    POETRY("Poetry"),
    NON_FICTION("Non-fiction"),
    AUTOBIOGRAPHY("Autobiography"),
    BIOGRAPHY("Biography"),
    ESSAY("Essay");

    private final String name;

    private Genre(String name){
        this.name = name;
    }

    public String getName() { return name; }
}