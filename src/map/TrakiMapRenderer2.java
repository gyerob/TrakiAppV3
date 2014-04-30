package map;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;
import hu.gyerob.trakiapp.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import map.utils.copy.MatrixHelper;
import map.utils.copy.ShaderHelper;
import map.utils.copy.TextResourceReader;
import map.utils.copy.TextureHelper;
import android.content.Context;
import android.opengl.GLSurfaceView;

public class TrakiMapRenderer2 implements GLSurfaceView.Renderer {

	private static final int POSITION_COMPONENT_COUNT = 3;
	private static final int TEXTURE_COMPONENT_COUNT = 2;
	private static final int BYTES_PER_FLOAT = 4;

	private final FloatBuffer balevertexData;
	private final FloatBuffer baletextureData;
	private final FloatBuffer balenormalData;

	private int mMVPMatrixHandle;
	private int mMVMatrixHandle;
	private int mPositionHandle;
	private int mTextureUniformHandle;
	private int mTextureCoordHandle;

	private int baleTextureDataHandle;

	private float[] mModelMatrix = new float[16];
	private float[] mViewMatrix = new float[16];
	private float[] mProjectionMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];

	private final Context context;

	private int program;

	private float xRotation, yRotation;

	private Cylinder bale = new Cylinder();;
	private float[] balevertices;
	private float[] baletextures;
	private float[] balenormals;

	public TrakiMapRenderer2(Context context) {
		this.context = context;

		balevertices = bale.getVertices();
		baletextures = bale.getTextures();
		balenormals = bale.getNormal();

		// pozíciók
		balevertexData = ByteBuffer
				.allocateDirect(balevertices.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		balevertexData.put(balevertices).position(0);

		// normálok
		balenormalData = ByteBuffer
				.allocateDirect(balenormals.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		balenormalData.put(balenormals).position(0);

		// textúrák
		baletextureData = ByteBuffer
				.allocateDirect(baletextures.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		baletextureData.put(baletextures).position(0);

		// színek
	}

	private void updateview() {
		setIdentityM(mViewMatrix, 0);
		rotateM(mViewMatrix, 0, -yRotation, 1f, 0f, 0f);
		rotateM(mViewMatrix, 0, -xRotation, 0f, 1f, 0f);

		translateM(mViewMatrix, 0, 0, -1.5f, -5f);
	}

	public void handledrag(float deltax, float deltay) {
		xRotation += deltax / 16f;
		yRotation += deltay / 16f;

		if (yRotation < -90) {
			yRotation = -90;
		} else if (yRotation > 90) {
			yRotation = 90;
		}

		// updateview();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);

		MatrixHelper.perspectiveM(mProjectionMatrix, 45, (float) width
				/ (float) height, 1f, 100f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glEnable(GL_CULL_FACE);

		glEnable(GL_DEPTH_TEST);
		setLookAtM(mViewMatrix, 0, 0, 0, 5, 0, 0, -2, 0, 1, 0);
		String vertexShaderSource = TextResourceReader
				.readTextFileFromResource(context,
						R.raw.particle_vertex_shader);
		String fragmentShaderSource = TextResourceReader
				.readTextFileFromResource(context,
						R.raw.per_pixel_fragment_shader);

		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
		int fragmentShader = ShaderHelper
				.compileFragmentShader(fragmentShaderSource);

		program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

		baleTextureDataHandle = TextureHelper.loadTexture(context,
				R.drawable.balaside);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glUseProgram(program);

		mMVPMatrixHandle = glGetUniformLocation(program, "u_MVPMatrix");
		mMVMatrixHandle = glGetUniformLocation(program, "u_MVMatrix");
		mTextureUniformHandle = glGetUniformLocation(program, "u_Texture");
		mPositionHandle = glGetAttribLocation(program, "a_Position");
		mTextureCoordHandle = glGetAttribLocation(program, "a_TexCoordinate");

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, baleTextureDataHandle);
		glUniform1i(mTextureUniformHandle, 0);

		setIdentityM(mModelMatrix, 0);
		translateM(mModelMatrix, 0, 0.0f, 0.0f, -5.0f);
		drawBale();
	}

	private void drawBale() {
		balevertexData.position(0);
		glVertexAttribPointer(mPositionHandle, POSITION_COMPONENT_COUNT,
				GL_FLOAT, false, 0, balevertexData);

		glEnableVertexAttribArray(mPositionHandle);

		baletextureData.position(0);
		glVertexAttribPointer(mTextureCoordHandle, TEXTURE_COMPONENT_COUNT,
				GL_FLOAT, false, 0, baletextureData);

		glEnableVertexAttribArray(mTextureCoordHandle);

		multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

		glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVPMatrix, 0);

		multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

		glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

		glDrawArrays(GL_TRIANGLES, 0, 60);
		glDrawArrays(GL_TRIANGLE_FAN, 60, 12);
		glDrawArrays(GL_TRIANGLE_FAN, 72, 12);
	}
}
