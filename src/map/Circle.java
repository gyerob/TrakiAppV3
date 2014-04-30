package map;

public class Circle {
	int verticesnum;
	float centerx, centery, centerz;
	
	float[] vertices;

	public Circle(int verticesnum, float x, float y, float z) {
		this.verticesnum = verticesnum;
		vertices = new float[verticesnum*3];
		centerx = x;
		centery = y;
		centerz = z;
	}
	
	
}
