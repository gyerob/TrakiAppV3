package data;

public class Racer extends People {
	String town;
	boolean sex;
	boolean trailer;
	boolean slalom;
	boolean drag;
	String group;
	String point;

	public Racer() {

	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public boolean getSex() {
		return sex;
	}

	public String getTown() {
		return town;
	}

	public boolean getTrailer() {
		return trailer;
	}

	public boolean getSlalom() {
		return slalom;
	}

	public boolean getDrag() {
		return drag;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public void setTrailer(boolean trailer) {
		this.trailer = trailer;
	}

	public void setSlalom(boolean slalom) {
		this.slalom = slalom;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}
}
