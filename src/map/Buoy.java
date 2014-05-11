package map;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.glDrawElements;

import java.nio.ByteBuffer;

import map.utils.VertexArray;

public class Buoy {
	private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COMPONENT_COUNT) 
        * BYTES_PER_FLOAT;

    private final VertexArray vertexArray;
    private final ByteBuffer sideindexArray;

    public Buoy() {
                    
        vertexArray = new VertexArray(new float[] {
            /*
             * 
             *   7_6
             *  8/ \ 
             * 9/   \ 5
             *10\   / 4
             *   \_/ 3
             *   1 2
             *   
             *   	 1
             *      /\
             *     /  \
             *    /    \
             *   /______\
             *   2	    3
             *   1,2,3  
             */
            
        		
        	//X			Y			Z			TX		TY
            //1. lap
            0.0f,		1.0f,		0.0f,		0.5f,	0.0f,	//0
        	-0.309f,	-1.0f,		0.951f,		0.0f,	1.0f,	//1
            0.309f,		-1.0f,		0.951f,		1.0f,	1.0f,	//2
            
            //2. lap
            0.0f,		1.0f,		0.0f,		0.5f,	0.0f,	//3
        	0.309f,		-1.0f,		0.951f,		0.0f,	1.0f,	//4
            0.809f,		-1.0f,		0.588f,		1.0f,	1.0f,	//5

            //3. lap
            0.0f,		1.0f,		0.0f,		0.5f,	0.0f,	//6
        	0.809f,		-1.0f,		0.588f,		0.0f,	1.0f,	//7
            1.0f,		-1.0f,		0.0f,		1.0f,	1.0f,	//8

            //4. lap
            0.0f,		1.0f,		0.0f,		0.5f,	0.0f,	//9
        	1.0f,		-1.0f,		0.0f,		0.0f,	1.0f,	//10
            0.809f,		-1.0f,		-0.588f,	1.0f,	1.0f,	//11
            
            //5. lap
            0.0f,		1.0f,		0.0f,		0.5f,	0.0f,	//12
        	0.809f,		-1.0f,		-0.588f,	0.0f,	1.0f,	//13
            0.309f,		-1.0f,		-0.951f,	1.0f,	1.0f,	//14

            //6. lap
            0.0f,		1.0f,		0.0f,		0.5f,	0.0f,	//15
        	0.309f,		-1.0f,		-0.951f,	0.0f,	1.0f,	//16
            -0.309f,	-1.0f,		-0.951f,	1.0f,	1.0f,	//17

            //7. lap
            0.0f,		1.0f,		0.0f,		0.5f,	0.0f,	//18
        	-0.309f,	-1.0f,		-0.951f,	0.0f,	1.0f,	//19
            -0.809f,	-1.0f,		-0.588f,	1.0f,	1.0f,	//20

            //8. lap
            0.0f,		1.0f,		0.0f,		0.5f,	0.0f,	//21
        	-0.809f,	-1.0f,		-0.588f,	0.0f,	1.0f,	//22
            -1.0f,		-1.0f,		0.0f,		1.0f,	1.0f,	//23

            //9. lap
            0.0f,		1.0f,		0.0f,		0.5f,	0.0f,	//24
        	-1.0f,		-1.0f,		0.0f,		0.0f,	1.0f,	//25
            -0.809f,	-1.0f,		0.588f,		1.0f,	1.0f,	//26

            //10. lap
            0.0f,		1.0f,		0.0f,		0.5f,	0.0f,	//27
        	-0.809f,	-1.0f,		0.588f,     0.0f,	1.0f,	//28
            -0.309f,	-1.0f,		0.951f,     1.0f,	1.0f,	//29
        });
        sideindexArray = ByteBuffer.allocateDirect(10 * 6).put(new byte[] {
            // 1            
            0,	1,	2, 

            // 2
            3,	4,	5, 

            // 3
            6,	7,	8, 

            // 4
            9,	10,	11, 

            // 5
            12,	13,	14, 

            // 6
            15,	16,	17, 
            
            // 7
            18,	19,	20, 
            
            // 8
            21,	22,	23, 
            
            // 9
            24,	25,	26, 
            
            // 10
            27,	28,	29, 
        });
        sideindexArray.position(0);
    }

    public void bindData(BuoyShaderProgram skyboxProgram) {
        vertexArray.setVertexAttribPointer(0,
            skyboxProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT, STRIDE);
        
        vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,
            skyboxProgram.getTexAttributeLocation(),
            TEXTURE_COMPONENT_COUNT, STRIDE);
    }

    public void draw() {
        glDrawElements(GL_TRIANGLES, 60, GL_UNSIGNED_BYTE, sideindexArray);
    }
}
