package model;


public class Person{
    private String name;
    private String ssn;
    private Integer age;
    private Gender gender;
    private Integer id;

    public String getName() { return this.name; }
    public Integer getId() { return this.id;}
    public String getSsn() { return this.ssn; }
    public Integer getAge() { return this.age; }
    public String getGender() { 
        try{
            this.gender.getName();
        }
        catch(Exception e){
            return "null";
        }
        return this.gender.getName();
        
    }
    public void setGender(Gender gender) { this.gender = gender; }
    public void setName(String name) { this.name = name; }
    public void setSsn(String ssn) { this.ssn = ssn; }
    public void setAge(Integer age) { this.age = age; }

}