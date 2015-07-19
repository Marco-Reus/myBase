package de.bvb.mybistudy.entity;

public class MeBean {
	public int code;
	public String name;
	public int icon;

	public MeBean(int code, String name, int icon) {
		super();
		this.code = code;
		this.name = name;
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "MeBean [code=" + code + ", name=" + name + ", icon=" + icon + "]";
	}

}
