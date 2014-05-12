package map;

import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawElements;

import java.nio.ByteBuffer;

import map.utils.VertexArray;

public class Plane {
	private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 4;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) 
        * BYTES_PER_FLOAT;

    private final VertexArray vertexArray;
    private final ByteBuffer sideindexArray;

    public Plane() {
                    
        vertexArray = new VertexArray(new float[] {
        		
        	//X			Y			Z			R		G		B		A
            //1. lap
            -1.0f,		0.0f,		1.0f,		0.0f,	1.0f,	0.0f,	0.0f,	//0
        	1.0f,		0.0f,		1.0f,		0.0f,	1.0f,	0.0f,	0.0f,	//1
        	-1.0f,		0.0f,		-1.0f,		0.0f,	1.0f,	0.0f,	0.0f,	//2
        	1.0f,		0.0f,		-1.0f,		0.0f,	1.0f,	0.0f,	0.0f	//3
        });
        sideindexArray = ByteBuffer.allocateDirect(28 * 4).put(new byte[] {
            // 1            
            0,	1,	2,
            2,	1,	3
        });
        sideindexArray.position(0);
    }

    public void bindData(PlaneShaderProgram planeProgram) {
        vertexArray.setVertexAttribPointer(0,
            planeProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT, STRIDE);
        /*
        vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,
            lineProgram.getTexAttributeLocation(),
            COLOR_COMPONENT_COUNT, STRIDE);
  */  }

    public void draw() {
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, sideindexArray);
    }
}
