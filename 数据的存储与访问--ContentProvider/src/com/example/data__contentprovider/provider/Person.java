package com.example.data__contentprovider.provider;

public class Person {
	private int personid;
	private String name;
	private int age;
	
	
	public int getPersonid() {
		return personid;
	}
	public void setPersonid(int personid) {
		this.personid = personid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Person [id=" + personid + ", name=" + name + ", age=" + age + "]";
	}
	
	
}
