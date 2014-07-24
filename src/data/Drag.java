package data;

public class Drag extends People {
	String ido1;
	String ido2;
	String legjobb;

	public Drag() {

	}
	
	public void setIdo1(String time) {
		ido1 = time;
	}
	
	public void setIdo2(String time) {
		ido2 = time;
	}
	
	public void setLegjobbIdo(String time) {
		legjobb = time;
	}
	
	public String getIdo1() {
		return ido1;
	}
	
	public String getIdo2() {
		return ido2;
	}
	
	public String getLegjobbIdo() {
		return legjobb;
	}
}