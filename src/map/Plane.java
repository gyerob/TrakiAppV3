package map;

import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawElements;

import java.nio.ByteBuffer;

import map.utils.VertexArray;

public class Plane {
	private static final int POSITION_COMPONENT_COUNT = 3;
	private static final int TEXTURE_COMPONENT_COUNT = 2;
	private static final int BYTES_PER_FLOAT = 4;
	private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COMPONENT_COUNT)
			* BYTES_PER_FLOAT;

	private final VertexArray vertexArray;
	private final ByteBuffer topindexArray;

	public Plane() {

		vertexArray = new VertexArray(new float[] {

				//X		Y		Z		TX		TY
				//1. lap
				-1.0f,	0.0f,	1.0f,	0.0f,	1.0f,	// 0
				1.0f,	0.0f,	1.0f,	1.0f,	1.0f,	// 1
				-1.0f,	0.0f,	-1.0f,	0.0f,	0.0f,	// 2
				1.0f,	0.0f,	-1.0f,	1.0f,	0.0f	// 3
				});
		topindexArray = ByteBuffer.allocateDirect(30).put(new byte[] {
				// 1
				0, 1, 3, 
				3, 2, 0 });
		topindexArray.position(0);
	}

	public void bindData(PlaneShaderProgram planeProgram) {
		vertexArray.setVertexAttribPointer(0,
				planeProgram.getPositionAttributeLocation(),
				POSITION_COMPONENT_COUNT, STRIDE);

		vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,
				planeProgram.getTexAttributeLocation(),
				TEXTURE_COMPONENT_COUNT, STRIDE);
	}

	public void draw() {
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, topindexArray);
	}
}
