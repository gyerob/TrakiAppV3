package map;

import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.glDrawElements;

import java.nio.ByteBuffer;

import map.utils.VertexArray;

public class Line {
	private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 4;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) 
        * BYTES_PER_FLOAT;

    private final VertexArray vertexArray;
    private final ByteBuffer sideindexArray;

    public Line() {
                    
        vertexArray = new VertexArray(new float[] {
        		
        	//X			Y			Z			R		G		B		A
            //1. lap
            0.0f,		1.0f,		0.0f,		1.0f,	0.0f,	0.0f,	0.0f,	//0
        	128.0f,		1.0f,		0.0f,		1.0f,	0.0f,	0.0f,	0.0f	//1
        });
        sideindexArray = ByteBuffer.allocateDirect(2 * 1).put(new byte[] {
            // 1            
            0,	1
        });
        sideindexArray.position(0);
    }

    public void bindData(LineShaderProgram lineProgram) {
        vertexArray.setVertexAttribPointer(0,
            lineProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT, STRIDE);
        /*
        vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,
            lineProgram.getTexAttributeLocation(),
            COLOR_COMPONENT_COUNT, STRIDE);
  */  }

    public void draw() {
        glDrawElements(GL_LINES, 2, GL_UNSIGNED_BYTE, sideindexArray);
    }
}
