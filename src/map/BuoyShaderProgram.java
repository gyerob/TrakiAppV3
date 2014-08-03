package map;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import hu.gyerob.trakiappdev.R;
import android.content.Context;

public class BuoyShaderProgram extends ShaderProgram {
	private final int uMatrixLocation;
	private final int uTextureUnitLocation;

	private final int aPositionLocation;
	private final int aTextureLocation;

	public BuoyShaderProgram(Context context) {
		super(context, R.raw.per_pixel_vertex_shader,
				R.raw.per_pixel_fragment_shader);

		uMatrixLocation = glGetUniformLocation(program, "u_MVPMatrix");
		uTextureUnitLocation = glGetUniformLocation(program, "u_Texture");

		aTextureLocation = glGetAttribLocation(program, "a_TexCoordinate");
		aPositionLocation = glGetAttribLocation(program, "a_Position");
	}

	public void setUniforms(float[] matrix, int textureId) {
		glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, textureId);
		glUniform1i(uTextureUnitLocation, 0);
	}

	public int getPositionAttributeLocation() {
		return aPositionLocation;
	}

	public int getTexAttributeLocation() {
		return aTextureLocation;
	}
}
