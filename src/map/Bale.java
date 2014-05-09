package map;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.glDrawElements;

import java.nio.ByteBuffer;

import map.utils.VertexArray;

public class Bale {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COMPONENT_COUNT) 
        * BYTES_PER_FLOAT;

    private final VertexArray vertexArray;
    private final ByteBuffer sideindexArray;
    private final ByteBuffer topindexArray;

    public Bale() {
                    
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
             *   1____2
             *   |   /|
             *   | 	/ |
             *   | /  |
             *   |/___|
             *   3	  4
             *   1,3,2  
             *   3,4,2
             */
            
        		
        	//X			Y			Z			TX		TY
            //1. lap
            -0.309f,	1.0f,		0.951f,		0.0f,	0.0f,	//0
            0.309f,		1.0f,		0.951f,		1.0f,	0.0f,	//1
        	-0.309f,	-1.0f,		0.951f,		0.0f,	1.0f,	//2
            0.309f,		-1.0f,		0.951f,		1.0f,	1.0f,	//3
            
            //2. lap
            0.309f,		1.0f,		0.951f,		0.0f,	0.0f,	//4
            0.809f,		1.0f,		0.588f,		1.0f,	0.0f,	//5
        	0.309f,		-1.0f,		0.951f,		0.0f,	1.0f,	//6
            0.809f,		-1.0f,		0.588f,		1.0f,	1.0f,	//7

            //3. lap
            0.809f,		1.0f,		0.588f,		0.0f,	0.0f,	//8
            1.0f,		1.0f,		0.0f,		1.0f,	0.0f,	//9
        	0.809f,		-1.0f,		0.588f,		0.0f,	1.0f,	//10
            1.0f,		-1.0f,		0.0f,		1.0f,	1.0f,	//11

            //4. lap
            1.0f,		1.0f,		0.0f,		0.0f,	0.0f,	//12
            0.809f,		1.0f,		-0.588f,	1.0f,	0.0f,	//13
        	1.0f,		-1.0f,		0.0f,		0.0f,	1.0f,	//14
            0.809f,		-1.0f,		-0.588f,	1.0f,	1.0f,	//15

            //5. lap
            0.809f,		1.0f,		-0.588f,	0.0f,	0.0f,	//16
            0.309f,		1.0f,		-0.951f,	1.0f,	0.0f,	//17
        	0.809f,		-1.0f,		-0.588f,	0.0f,	1.0f,	//18
            0.309f,		-1.0f,		-0.951f,	1.0f,	1.0f,	//19

            //6. lap
            0.309f,		1.0f,		-0.951f,	0.0f,	0.0f,	//20
            -0.309f,	1.0f,		-0.951f,	1.0f,	0.0f,	//21
        	0.309f,		-1.0f,		-0.951f,	0.0f,	1.0f,	//22
            -0.309f,	-1.0f,		-0.951f,	1.0f,	1.0f,	//23

            //7. lap
            -0.309f,	1.0f,		-0.951f,	0.0f,	0.0f,	//24
            -0.809f,	1.0f,		-0.588f,	1.0f,	0.0f,	//25
        	-0.309f,	-1.0f,		-0.951f,	0.0f,	1.0f,	//26
            -0.809f,	-1.0f,		-0.588f,	1.0f,	1.0f,	//27

            //8. lap
            -0.809f,	1.0f,		-0.588f,	0.0f,	0.0f,	//28
            -1.0f,		1.0f,		0.0f,		1.0f,	0.0f,	//29
        	-0.809f,	-1.0f,		-0.588f,	0.0f,	1.0f,	//30
            -1.0f,		-1.0f,		0.0f,		1.0f,	1.0f,	//31

            //9. lap
            -1.0f,		1.0f,		0.0f,		0.0f,	0.0f,	//32
            -0.809f,	1.0f,		0.588f,		1.0f,	0.0f,	//33
        	-1.0f,		-1.0f,		0.0f,		0.0f,	1.0f,	//34
            -0.809f,	-1.0f,		0.588f,		1.0f,	1.0f,	//35

            //10. lap
            -0.809f,	1.0f,		0.588f,		0.0f,	0.0f,	//36
            -0.309f,	1.0f,		0.951f,     1.0f,	0.0f,	//37
        	-0.809f,	-1.0f,		0.588f,     0.0f,	1.0f,	//38
            -0.309f,	-1.0f,		0.951f,     1.0f,	1.0f,	//39
            
                 
            //bottom
            0.309f,		-1.0f,		0.951f,		0.345f,		0.975f,	//40
            -0.309f,	-1.0f,		0.951f,		0.655f,		0.975f,	//41
            -0.809f,	-1.0f,		0.588f,		0.904f,		0.794f,	//42
            -1.0f,		-1.0f,		0.0f,		1.0f,		0.5f,	//43
            -0.809f,	-1.0f,		-0.588f,	0.904f,		0.206f,	//44
            -0.309f,	-1.0f,		-0.951f,	0.655f,		0.025f,	//45
            0.309f,		-1.0f,		-0.951f,	0.345f,		0.025f,	//46
            0.809f,		-1.0f,		-0.588f,	0.096f,		0.206f,	//47
            1.0f,		-1.0f,		0.0f,		0.0f,		0.5f,	//48
            0.809f,		-1.0f,		0.588f,		0.096f,		0.794f,	//49
            
          //top
            -0.309f,	1.0f,		0.951f,		0.345f,		0.975f,	//50
            0.309f,		1.0f,		0.951f,		0.655f,		0.975f,	//51
            0.809f,		1.0f,		0.588f,		0.904f,		0.794f,	//52
            1.0f,		1.0f,		0.0f,		1.0f,		0.5f,	//53
            0.809f,		1.0f,		-0.588f,	0.904f,		0.206f,	//54
            0.309f,		1.0f,		-0.951f,	0.655f,		0.025f,	//55
            -0.309f,	1.0f,		-0.951f,	0.345f,		0.025f,	//56
            -0.809f,	1.0f,		-0.588f,	0.096f,		0.206f,	//57
            -1.0f,		1.0f,		0.0f,		0.0f,		0.5f,	//58
            -0.809f,	1.0f,		0.588f,		0.096f,		0.794f,	//59
            
            //middle
            0.0f,		-1.0f,		0.0f,		0.5f,	0.5f,	//60
            0.0f,		1.0f,		0.0f,		0.5f,	0.5f	//61
        });
        sideindexArray = ByteBuffer.allocateDirect(10 * 6).put(new byte[] {
            // 1            
            0,	2,	1, 
            2,	3,	1,

            // 2
            4,	6,	5, 
            6,	7,	5,

            // 3
            8,	10,	9, 
            10,	11,	9,

            // 4
            12,	14,	13, 
            14,	15,	13,

            // 5
            16,	18,	17, 
            18,	19,	17,

            // 6
            20,	22,	21, 
            22,	23,	21,
            
            // 7
            24,	26,	25, 
            26,	27,	25,
            
            // 8
            28,	30,	29, 
            30,	31,	29, 
            
            // 9
            32,	34,	33, 
            34,	35,	33, 
            
            // 10
            36,	38,	37, 
            38,	39,	37, 
        });
        sideindexArray.position(0);
        
        topindexArray = ByteBuffer.allocateDirect(12).put(new byte[] {
            61, 50, 51,
            52, 53, 54,
            55, 56, 57,
            58, 59, 50
        });
        topindexArray.position(0);
        
    }

    public void bindData(BaleShaderProgram baleProgram) {
        vertexArray.setVertexAttribPointer(0,
            baleProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT, STRIDE);
        
        vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,
            baleProgram.getTexAttributeLocation(),
            TEXTURE_COMPONENT_COUNT, STRIDE);
    }

    public void draw() {
        glDrawElements(GL_TRIANGLES, 60, GL_UNSIGNED_BYTE, sideindexArray);
        glDrawElements(GL_TRIANGLE_FAN, 12, GL_UNSIGNED_BYTE, topindexArray);
    }
}
