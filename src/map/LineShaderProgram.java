package map;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import hu.gyerob.trakiapp.R;
import android.content.Context;

public class LineShaderProgram extends ShaderProgram {
	private final int uMatrixLocation;
	private final int uColor;

	private final int aPositionLocation;

	public LineShaderProgram(Context context) {
		super(context, R.raw.line_vertex_shader,
				R.raw.line_fragment_shader);

		uMatrixLocation = glGetUniformLocation(program, "u_MVPMatrix");
		uColor = glGetUniformLocation(program, "v_Color");
		
		aPositionLocation = glGetAttribLocation(program, "a_Position");
	}

	public void setUniforms(float[] matrix, int textureId) {
		glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		
		//glUniformMatrix4fv(uColor, 1, false, matrix, 0);
		
		glUniform1i(uColor, 0);
	}

	public int getPositionAttributeLocation() {
		return aPositionLocation;
	}
}
