package map;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static android.opengl.Matrix.transposeM;
import hu.gyerob.trakiapp.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import map.utils.MatrixHelper;
import map.utils.TextureHelper;
import android.content.Context;
import android.opengl.GLSurfaceView;

public class TrakiMapRenderer2 implements GLSurfaceView.Renderer {

	private final float[] modelMatrix = new float[16];
	private final float[] viewMatrix = new float[16];
	//private final float[] viewMatrixForSkybox = new float[16];
	private final float[] projectionMatrix = new float[16];

	private final float[] tempMatrix = new float[16];
	private final float[] modelViewMatrix = new float[16];
	private final float[] it_modelViewMatrix = new float[16];
	private final float[] modelViewProjectionMatrix = new float[16];

	private final Context context;

	private BaleShaderProgram baleprogram;
	private Bale bale;
	private int baletexture;

	private BuoyShaderProgram buoyprogram;
	private Buoy buoy;
	private int buoytexture;

	private float xRotation, yRotation;

	public TrakiMapRenderer2(Context context) {
		this.context = context;
	}

	private void updateview() {
		setIdentityM(viewMatrix, 0);
		// rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
		rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f);

		translateM(viewMatrix, 0, 0, -1.5f, -5f);
	}

	public void handledrag(float deltax, float deltay) {
		xRotation += deltax / 16f;
		yRotation += deltay / 16f;

		if (yRotation < -180) {
			yRotation = -180;
		} else if (yRotation > 180) {
			yRotation = 180;
		}

		updateview();
	}

	private void updateMvpMatrix() {
		multiplyMM(modelViewMatrix, 0, viewMatrix, 0, modelMatrix, 0);
		invertM(tempMatrix, 0, modelViewMatrix, 0);
		transposeM(it_modelViewMatrix, 0, tempMatrix, 0);
		multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0,
				modelViewMatrix, 0);
	}
/*
	private void updateMvpMatrixForSkybox() {
		multiplyMM(tempMatrix, 0, viewMatrixForSkybox, 0, modelMatrix, 0);
		multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0,
				tempMatrix, 0);
	}
*/
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);

		MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
				/ (float) height, 1f, 100f);

		updateview();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		baleprogram = new BaleShaderProgram(context);
		bale = new Bale();

		buoyprogram = new BuoyShaderProgram(context);
		buoy = new Buoy();

		baletexture = TextureHelper.loadTexture(context, R.drawable.balaside);
		buoytexture = TextureHelper.loadTexture(context, R.drawable.buoy);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		drawBale(0f, 0f);
		drawBuoy(0f, 0f);
	}

	private void drawBale(float x, float z) {
		setIdentityM(modelMatrix, 0);
		// scaleM(modelMatrix, 0, 0.2f, 0.2f, 0.2f);
		translateM(modelMatrix, 0, x, 1f, z);
		updateMvpMatrix();

		baleprogram.useProgram();
		baleprogram.setUniforms(modelViewProjectionMatrix, baletexture);
		bale.bindData(baleprogram);
		bale.draw();
	}

	private void drawBuoy(float x, float z) {
		setIdentityM(modelMatrix, 0);
		// scaleM(modelMatrix, 0, 0.2f, 0.2f, 0.2f);
		translateM(modelMatrix, 0, x, 1f, z);
		updateMvpMatrix();

		buoyprogram.useProgram();
		buoyprogram.setUniforms(modelViewProjectionMatrix, buoytexture);
		buoy.bindData(buoyprogram);
		buoy.draw();
	}
}
