package map;

public class Plane {
	private float[] vertices = {
			// órajárással ellentétesen.
			// X,			Y,			Z
			// 1.
			-1.0f,	0.0f,	-1.0f,
			-1.0f,	0.0f,	1.0f,
			1.0f,	0.0f,	-1.0f,

			-1.0f,	0.0f,	1.0f,
			1.0f,	0.0f,	1.0f,
			1.0f,	0.0f,	-1.0f
	};
	
	private float[] colors = {
			0.0f,	1.0f,	0.0f,	0.0f,
			0.0f,	1.0f,	0.0f,	0.0f,
			0.0f,	1.0f,	0.0f,	0.0f,

			0.0f,	1.0f,	0.0f,	0.0f,
			0.0f,	1.0f,	0.0f,	0.0f,
			0.0f,	1.0f,	0.0f,	0.0f
	};
	
	private float[] normals = {
			0.0f,	1.0f,	0.0f,
			0.0f,	1.0f,	0.0f,
			0.0f,	1.0f,	0.0f,

			0.0f,	1.0f,	0.0f,
			0.0f,	1.0f,	0.0f,
			0.0f,	1.0f,	0.0f,
	};
	
	private float[] textures = {
			0.0f,	0.0f,
			0.0f,	1.0f,
			1.0f,	0.0f,
			
			0.0f,	1.0f,
			1.0f,	1.0f,
			1.0f,	0.0f
	};

	public float[] getColors() {
		return colors;
	}

	public void setColors(float[] colors) {
		this.colors = colors;
	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public float[] getNormals() {
		return normals;
	}

	public void setNormals(float[] normals) {
		this.normals = normals;
	}

	public float[] getTextures() {
		return textures;
	}

	public void setTextures(float[] textures) {
		this.textures = textures;
	}
}
