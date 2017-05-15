package evolution.pojo;

import evolution.annotation.Alias;

public class AnyPojo {
	@Alias("姓名")
	private String name;
	@Alias("性别")
	private String gender;
	@Alias("年龄")
	private int age;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}

	public AnyPojo() {

	}

	public AnyPojo(String name, String gender, int age) {
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

	@Override
	public String toString() {
		return "AnyPojo [name=" + name + ", gender=" + gender + ", age=" + age + "]";
	}
}
