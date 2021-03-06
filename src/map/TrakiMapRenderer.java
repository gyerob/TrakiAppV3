package map;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glLineWidth;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.scaleM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static android.opengl.Matrix.transposeM;
import hu.gyerob.trakiappdev.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import map.utils.MatrixHelper;
import map.utils.TextureHelper;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;

public class TrakiMapRenderer implements GLSurfaceView.Renderer {

	private final float[] modelMatrix = new float[16];
	private final float[] viewMatrix = new float[16];
	// private final float[] viewMatrixForSkybox = new float[16];
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

	private NewLine line;

	private Tractor tractorA;
	private Tractor tractorB;

	private PlaneShaderProgram planeprogram;
	private Plane plane;
	private int planetexture;

	private HeightmapShaderProgram heightmapProgram;
	private Heightmap heightmap;

	private float xRotation;
	private float moveX, moveY;

	private float angle;
	private float xpos, zpos;

	private float zoom = 50f;

	private float trakix, trakiz;
	private float trakiangle;

	private boolean birdview;
	private boolean trakiA = false;
	private boolean trakiB = false;

	private Thread t;
	private boolean running = false;

	private float[] obstacles = {
			// b�l�k
			8.0f, 5.0f, 32.0f, 8.0f, 65.0f, 8.0f,

			8.0f,
			-5.0f,
			32.0f,
			-8.0f,
			65.0f,
			-8.0f,

			// b�ly�k
			16.0f, 5.0f, 24.0f, 8.0f, 40.0f, 8.0f, 42.75f, 8.0f, 45.5f, 8.0f,
			48.0f, 8.0f, 48.0f, 4.0f, 48.0f, 2.0f, 48.0f, 12.0f, 48.0f, 16.0f,
			48.0f, 20.0f, 48.0f, 24.0f, 60.0f, 16.0f, 60.0f, 21.0f, 69.0f,
			8.0f,

			64.0f, 0.0f,

			16.0f, -5.0f, 24.0f, -8.0f, 40.0f, -8.0f, 42.75f, -8.0f, 45.5f,
			-8.0f, 48.0f, -8.0f, 48.0f, -4.0f, 48.0f, -2.0f, 48.0f, -12.0f,
			48.0f, -16.0f, 48.0f, -20.0f, 48.0f, -24.0f, 60.0f, -16.0f, 60.0f,
			-21.0f, 69.0f, -8.0f };

	private float[] track = { 0.0f, 2.5f, 8.0f, 2.5f, 16.0f, 7.5f, 24.0f, 5.5f,
			32.0f, 10.5f, 40.0f, 5.5f, 48.0f, 5.5f, 55.0f, 8.0f, 42.0f, 12.0f,
			55.0f, 16.0f, 42.0f, 20.0f, 48.0f, 22.0f, 56.0f, 19.0f, 64.0f,
			18.0f, 66.0f, 12.0f, 66.0f, 6.5f, 64.0f, 3.0f, 48.0f, 5.5f, 40.0f,
			5.5f, 32.0f, 10.5f, 24.0f, 5.5f, 16.0f, 7.5f, 8.0f, 2.5f, 0.0f,
			2.5f, };

	public TrakiMapRenderer(Context context) {
		this.context = context;
	}

	public void keyHandle(int irany) {
		// 0:el�re
		// 1:h�tra
		// 2:balra
		// 3:jobbra
		if (irany == 0 || irany == 1) {
			if (irany == 0) {
				xpos -= 5 * moveX;
				zpos += 5 * moveY;
			} else {
				xpos += 5 * moveX;
				zpos -= 5 * moveY;
			}
		} else if (irany == 2 || irany == 3) {
			if (irany == 2)
				angle -= 45f;
			if (irany == 3)
				angle += 45f;

			if (angle > 359f || angle < -359f)
				angle = 0f;

			moveX = (float) Math.sin(angle / 180 * Math.PI);
			moveY = (float) Math.cos(angle / 180 * Math.PI);
			if (Math.abs(moveX) < 0.00001)
				moveX = 0f;
			if (Math.abs(moveY) < 0.00001)
				moveY = 0f;
		}

		updateview();
	}

	public void settrakiview(boolean a, boolean b) {
		if (!a && !b) {
			if (trakiA) {
				xpos = -trakix;
				zpos = -trakiz;
			} else if (trakiB) {
				xpos = -trakix;
				zpos = trakiz;
			}
		}
		trakiA = a;
		trakiB = b;
		updateview();
	}

	public void start() {
		Log.d("isalaive", Boolean.toString(t.isAlive()));
		if (!t.isAlive()) {
			if (!running) {
				initThread();
				t.start();
			}
		}
	}

	private void updateview() {
		setIdentityM(viewMatrix, 0);

		if (birdview) {
			rotateM(viewMatrix, 0, 90, 1f, 0f, 0f);
			if (!trakiA && !trakiB)
				translateM(viewMatrix, 0, xpos, -zoom, zpos);
			else if (trakiA)
				translateM(viewMatrix, 0, -trakix, -zoom, -trakiz);
			else if (trakiB)
				translateM(viewMatrix, 0, -trakix, -zoom, trakiz);
		} else {
			rotateM(viewMatrix, 0, angle, 0f, 1f, 0f);
			translateM(viewMatrix, 0, xpos, -3.5f, zpos);
		}
	}

	public void setZoom(float scale) {
		zoom = 50f * scale;
	}

	public void handledrag(float deltax, float deltay) {
		if (!birdview) {
			xRotation += deltax / 16f;

			angle = -xRotation;

			if (angle > 359f || angle < -359f) {
				angle = 0f;
				xRotation = 0f;
			}

			moveX = (float) Math.sin(angle / 180 * Math.PI);
			moveY = (float) Math.cos(angle / 180 * Math.PI);
			if (Math.abs(moveX) < 0.00001)
				moveX = 0f;
			if (Math.abs(moveY) < 0.00001)
				moveY = 0f;

			xpos += ((-deltay / 16f) * moveX);
			zpos += ((deltay / 16f) * moveY);
		} else {
			if ((xpos+deltax / 16.0f) < 10 && (xpos+deltax / 16.0f) > -80)
				xpos += deltax / 16.0f;
			if ((zpos+deltay / 16.0f) < 30 && (zpos+deltay / 16.0f) > -30)
				zpos += deltay / 16.0f;
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

	private void initThread() {
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				running = true;
				float x, y, deltax = 0, deltay = 0, d;
				int i, k;
				int j;
				j = 0;
				i = k = 0;

				while (i < (track.length / 2) - 1) {
					if (j == 0) {
						x = track[k + 2] - track[k];
						y = track[k + 3] - track[k + 1];

						deltax = x / 100f;
						if (deltax >= 0f)
							deltay = y / 100f;
						else
							deltay = y / (-100f);

						trakiangle = (float) Math.atan(y / x);
						trakiangle *= 180 / Math.PI;

						if (i > 14)
							trakiangle = -180 + trakiangle;
					} else if (j == 100) {
						j = -1;
						k += 2;
						i++;
					}
					if (deltax >= 0f)
						trakiz += deltay;
					else
						trakiz -= deltay;
					trakix += deltax;

					d = Math.round(trakix * 100);
					d = d / 100;
					trakix = d;

					j++;

					updateview();
					SystemClock.sleep(10);
				}
				running = false;
				trakix = track[0];
				trakiz = track[1];
			}
		});
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);

		MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
				/ (float) height, 1f, 300f);

		updateview();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.753f, 0.878f, 0.643f, 0.0f);

		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glLineWidth(5.0f);

		xRotation = -90f;
		angle = 90f;
		moveX = 0f;
		moveY = 1f;
		trakix = track[0];
		trakiz = track[1];
		trakiangle = 0.0f;

		birdview = true;

		baleprogram = new BaleShaderProgram(context);
		bale = new Bale();

		buoyprogram = new BuoyShaderProgram(context);
		buoy = new Buoy();

		line = new NewLine();
		tractorA = new Tractor();
		tractorB = new Tractor();
		float szin[] = { 0.0f, 0.0f, 1.0f, 0.0f };
		tractorB.setColor(szin);

		planeprogram = new PlaneShaderProgram(context);
		plane = new Plane();

		heightmapProgram = new HeightmapShaderProgram(context);
		heightmap = new Heightmap(((BitmapDrawable) context.getResources()
				.getDrawable(R.drawable.palya2)).getBitmap());

		baletexture = TextureHelper.loadTexture(context, R.drawable.balaside);
		buoytexture = TextureHelper.loadTexture(context, R.drawable.buoy);
		planetexture = TextureHelper.loadTexture(context, R.drawable.palya);

		initThread();

		updateview();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		drawPlane();
		drawTractor();

		int j = 0;
		for (int i = 0; i < obstacles.length / 2; i++) {
			if (i < 6)
				drawBale(obstacles[j++], obstacles[j++]);
			else
				drawBuoy(obstacles[j++], obstacles[j++]);
		}
	}

	@SuppressWarnings("unused")
	private void drawHeightmap() {
		setIdentityM(modelMatrix, 0);
		scaleM(modelMatrix, 0, 148f, 0.3f, 148f);
		updateMvpMatrix();

		heightmapProgram.useProgram();
		heightmapProgram.setUniforms(modelViewMatrix, it_modelViewMatrix,
				modelViewProjectionMatrix);
		heightmap.bindData(heightmapProgram);
		heightmap.draw();
	}

	private void drawPlane() {
		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, 27f, 0f, 0f);
		scaleM(modelMatrix, 0, 45, 1, 45);
		updateMvpMatrix();

		planeprogram.useProgram();
		planeprogram.setUniforms(modelViewProjectionMatrix, planetexture);
		plane.bindData(planeprogram);
		plane.draw();
	}

	private void drawTractor() {
		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, (float) trakix, 0f, (float) trakiz);
		rotateM(modelMatrix, 0, -trakiangle, 0f, 1f, 0f);
		updateMvpMatrix();

		tractorA.draw(modelViewProjectionMatrix);

		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, (float) trakix, 0f, (float) -trakiz);
		rotateM(modelMatrix, 0, trakiangle, 0f, 1f, 0f);
		updateMvpMatrix();

		tractorB.draw(modelViewProjectionMatrix);
	}

	@SuppressWarnings("unused")
	private void drawLine() {
		setIdentityM(modelMatrix, 0);
		updateMvpMatrix();

		line.draw(modelViewProjectionMatrix);
	}

	private void drawBale(float x, float z) {
		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, x, 0.8f, z);
		scaleM(modelMatrix, 0, 0.75f, 0.75f, 0.75f);
		updateMvpMatrix();

		baleprogram.useProgram();
		baleprogram.setUniforms(modelViewProjectionMatrix, baletexture);
		bale.bindData(baleprogram);
		bale.draw();
	}

	private void drawBuoy(float x, float z) {
		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, x, 0.3f, z);
		scaleM(modelMatrix, 0, 0.6f, 0.2f, 0.6f);
		updateMvpMatrix();

		buoyprogram.useProgram();
		buoyprogram.setUniforms(modelViewProjectionMatrix, buoytexture);
		buoy.bindData(buoyprogram);
		buoy.draw();
	}

	public static int loadShader(int type, String shaderCode) {
		int shader = GLES20.glCreateShader(type);

		// add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}
}
