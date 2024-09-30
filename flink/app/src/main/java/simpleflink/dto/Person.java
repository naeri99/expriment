package simpleflink.dto;

public class Person {

    public Person(Integer age, String name){
        this.age = age;
        this.name = name; 
    }
    public Integer age;
    public String name;

    public Integer getAge(){
        return this.age;
    }

    public String getName(){
        return this.name;
    }
}