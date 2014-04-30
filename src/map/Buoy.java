package map;

public class Buoy {
	//2 Triangle_Fan
	private float[] vertices = {
			//kúp
			0.0f,		1.0f,		0.0f,
			-0.309f,	-1.0f,		0.951f,
			0.309f,		-1.0f,		0.951f,
			0.809f,		-1.0f,		0.588f,
			1.0f,		-1.0f,		0.0f,
			0.809f,		-1.0f,		-0.588f,
			0.309f,		-1.0f,		-0.951f,
			-0.309f,	-1.0f,		-0.951f,
			-0.809f,	-1.0f,		-0.588f,
			-1.0f,		-1.0f,		0.0f,
			-0.809f,	-1.0f,		0.588f,
			-0.309f,	-1.0f,		0.951f,
			
			//alap
			0.0f,		-1.0f,		0.0f, 		// közép
			0.309f,		-1.0f,		0.951f,		// 1
			-0.309f,	-1.0f,		0.951f,		// 2
			-0.809f,	-1.0f,		0.588f,		// 3
			-1.0f,		-1.0f,		0.0f,		// 4
			-0.809f,	-1.0f,		-0.588f,	// 5
			-0.309f,	-1.0f,		-0.951f, 	// 6
			0.309f,		-1.0f,		-0.951f, 	// 7
			0.809f,		-1.0f,		-0.588f, 	// 8
			1.0f,		-1.0f,		0.0f, 		// 9
			0.809f,		-1.0f,		0.588f,		// 10
			0.309f,		-1.0f,		0.951f,		// lezárás
	};
	private float[] normals = {
			
	};
	private float[] colors = {
			
	};
	private float[] textures = {
			0.5f,		0.5f,
			0.345f,		0.975f,
			0.655f,		0.975f,
			0.904f,		0.794f,
			1.0f,		0.5f,
			0.904f,		0.206f,
			0.655f,		0.025f,
			0.345f,		0.025f,
			0.096f,		0.206f,
			0.0f,		0.5f,
			0.096f,		0.794f,
			0.345f,		0.975f,
			
			//lent
			0.5f,		0.5f,
			0.345f,		0.975f,
			0.655f,		0.975f,
			0.904f,		0.794f,
			1.0f,		0.5f,
			0.904f,		0.206f,
			0.655f,		0.025f,
			0.345f,		0.025f,
			0.096f,		0.206f,
			0.0f,		0.5f,
			0.096f,		0.794f,
			0.345f,		0.975f,
	};
			

	public Buoy() {
	}

	public Buoy(int oldalak) {
	}

	public float[] getNormal() {
		return normals;
	}

	public void setNormal(float[] normal) {
		this.normals = normal;
	}

	public float[] getColor() {
		return colors;
	}

	public void setColor(float[] color) {
		this.colors = color;
	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] pos) {
		this.vertices = pos;
	}

	public float[] getTextures() {
		return textures;
	}

	public void setTextures(float[] baletextures) {
		this.textures = baletextures;
	}
}
