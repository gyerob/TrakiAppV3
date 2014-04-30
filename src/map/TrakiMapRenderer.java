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
import static android.opengl.Matrix.scaleM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;
import hu.gyerob.trakiapp.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import map.utils.MatrixHelper;
import map.utils.ShaderHelper;
import map.utils.TextResourceReader;
import map.utils.TextureHelper;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

public class TrakiMapRenderer implements GLSurfaceView.Renderer {

	private static final int POSITION_COMPONENT_COUNT = 3;
	private static final int TEXTURE_COMPONENT_COUNT = 2;
	private static final int BYTES_PER_FLOAT = 4;

	private final FloatBuffer balevertexData;
	private final FloatBuffer baletextureData;
	private final FloatBuffer balenormalData;
	
	private final FloatBuffer buoyvertexData;
	private final FloatBuffer buoytextureData;
	private final FloatBuffer buoynormalData;
	
	private final FloatBuffer planevertexData;
	private final FloatBuffer planetextureData;
	private final FloatBuffer planenormalData;

	private int mMVPMatrixHandle;
	private int mMVMatrixHandle;
	private int mPositionHandle;
	private int mTextureUniformHandle;
	private int mTextureCoordHandle;

	private int baleTextureDataHandle;
	private int buoyTextureDataHandle;
	private int planeTextureDataHandle;

	private float[] mModelMatrix = new float[16];
	private float[] mViewMatrix = new float[16];
	private float[] mProjectionMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];

	private final Context context;

	private int program;
	
	private float eyeX = 96.0f;
	private float eyeY = 2.0f;
	private float eyeZ = 8.0f;

	private float lookX = 0.0f;
	private float lookY = 2.0f;
	private float lookZ = 4.0f;

	private float upX = 0.0f;
	private float upY = 1.0f;
	private float upZ = 0.0f;
	
	private float camx,camz;
	
	private float xRotation, yRotation;
	
	private float angleInDegrees = 270.0f;
	
	private Cylinder bale = new Cylinder();;
	private float[] balevertices;
	private float[] baletextures;
	private float[] balenormals;
	
	private Buoy buoy = new Buoy();
	private float[] buoyvertices;
	private float[] buoytextures;
	private float[] buoynormals;
	
	private Plane plane = new Plane();
	private float[] planevertices;
	private float[] planetextures;
	private float[] planenormals;
	
	private float[] obstacles = {
			//bálák
			16.0f,		10.0f,
			64.0f,		16.0f,
			130.0f,		10.0f,
			
			16.0f,		-10.0f,
			64.0f,		-16.0f,
			130.0f,		-10.0f,
			
			//bólyák
			32.0f,		10.0f,
			48.0f,		16.0f,
			80.0f,		16.0f,
			85.5f,		16.f,
			91.0f,		16.0f,
			96.0f,		16.0f,
			96.0f,		4.0f,
			96.0f,		8.0f,
			96.0f,		24.0f,
			96.0f,		32.0f,
			96.0f,		40.0f,
			96.0f,		48.0f,
			120.0f,		32.0f,
			120.0f,		42.0f,
			138.0f,		16.0f,
			
			128.0f,		0.0f,
			
			
			32.0f,		-10.0f,
			48.0f,		-16.0f,
			80.0f,		-16.0f,
			85.5f,		-16.f,
			91.0f,		-16.0f,
			96.0f,		-16.0f,
			96.0f,		-4.0f,
			96.0f,		-8.0f,
			96.0f,		-24.0f,
			96.0f,		-32.0f,
			96.0f,		-40.0f,
			96.0f,		-48.0f,
			120.0f,		-32.0f,
			120.0f,		-42.0f,
			138.0f,		-16.0f
	};
	private long frameStartTimeMs;

	public TrakiMapRenderer(Context context) {
		this.context = context;
		
		balevertices = bale.getVertices();
		baletextures = bale.getTextures();
		balenormals = bale.getNormal();
		
		buoyvertices = buoy.getVertices();
		buoytextures = buoy.getTextures();
		buoynormals = buoy.getNormal();
		
		planevertices = plane.getVertices();
		planetextures = plane.getTextures();
		planenormals = plane.getNormals();
		
		//pozíciók
		balevertexData = ByteBuffer
				.allocateDirect(balevertices.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		balevertexData.put(balevertices).position(0);
		
		buoyvertexData = ByteBuffer
				.allocateDirect(buoyvertices.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		buoyvertexData.put(buoyvertices).position(0);
		
		planevertexData = ByteBuffer
				.allocateDirect(planevertices.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		planevertexData.put(planevertices).position(0);
		
		//normálok
		balenormalData = ByteBuffer.allocateDirect(balenormals.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		balenormalData.put(balenormals).position(0);
		
		buoynormalData = ByteBuffer.allocateDirect(buoynormals.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		buoynormalData.put(buoynormals).position(0);
		
		planenormalData = ByteBuffer.allocateDirect(planenormals.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		planenormalData.put(planenormals).position(0);
		
		
		//textúrák
		baletextureData = ByteBuffer.allocateDirect(baletextures.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		baletextureData.put(baletextures).position(0);
		
		buoytextureData = ByteBuffer.allocateDirect(buoytextures.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		buoytextureData.put(buoytextures).position(0);
		
		planetextureData = ByteBuffer.allocateDirect(planetextures.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		planetextureData.put(planetextures).position(0);
		
		//színek
	}
	
	private void limitFrameRate(int framesPerSecond) {
        long elapsedFrameTimeMs = SystemClock.elapsedRealtime() - frameStartTimeMs;
        long expectedFrameTimeMs = 1000 / framesPerSecond;
        long timeToSleepMs = expectedFrameTimeMs - elapsedFrameTimeMs;
        
        if (timeToSleepMs > 0) {
            SystemClock.sleep(timeToSleepMs);
        }                
        frameStartTimeMs = SystemClock.elapsedRealtime();
    }
	
	private void updateview() {
		//setIdentityM(mViewMatrix, 0);
        //rotateM(mViewMatrix, 0, -yRotation, 1f, 0f, 0f);
        rotateM(mViewMatrix, 0, -xRotation, 0f, 1f, 0f);
        
        //translateM(mViewMatrix, 0, 0, -1.5f, -5f);
	}
	
	public void handledrag(float deltax, float deltay) {
		xRotation += deltax / 64f;
        yRotation += deltay / 64f;
        
        if (yRotation < -90) {
            yRotation = -90;
        } else if (yRotation > 90) {
            yRotation = 90;
        } 
        
		//updateview();
	}
	
	public void incangle(boolean create) {
		if (!create) angleInDegrees += 45.0f;
		if (angleInDegrees == 360.0f) angleInDegrees = 0.0f;
		float temp = angleInDegrees * (float)Math.PI / 180.0f;
		
		camx = (float) Math.sin(temp);
        camz = (float) Math.cos(temp);
        
        Log.d("campos", Float.toString(lookX + 8*camx) + " " + Float.toString(lookZ + 8*camz) + " szög: " + Float.toString(angleInDegrees));
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);

		MatrixHelper.perspectiveM(mProjectionMatrix, 45, (float) width / (float) height, 1f, 100f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.0f, 0.6f, 1.0f, 0.0f);

		glEnable(GL_CULL_FACE);

		glEnable(GL_DEPTH_TEST);
		
		incangle(true);
		
		setLookAtM(mViewMatrix, 0, lookX + 8*camx, eyeY, lookZ + 8*camz, lookX, lookY,
				lookZ, upX, upY, upZ);		

		String vertexShaderSource = TextResourceReader
				.readTextFileFromResource(context, R.raw.per_pixel_vertex_shader);
		String fragmentShaderSource = TextResourceReader
				.readTextFileFromResource(context, R.raw.per_pixel_fragment_shader);

		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
		int fragmentShader = ShaderHelper
				.compileFragmentShader(fragmentShaderSource);

		program = ShaderHelper.createandlinkProgram(vertexShader,
				fragmentShader, new String[] { "a_Position", "a_Color", "a_TexCoordinate" });
		
		baleTextureDataHandle = TextureHelper.loadTexture(context, R.drawable.balaside);
		buoyTextureDataHandle = TextureHelper.loadTexture(context, R.drawable.buoy);
		planeTextureDataHandle = TextureHelper.loadTexture(context, R.drawable.noisy_grass_public_domain);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		limitFrameRate(24);
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glUseProgram(program);
		

    	//setIdentityM(mViewMatrix, 0);

		//setLookAtM(mViewMatrix, 0, lookX + 8*camx, eyeY, lookZ + 8*camz, lookX, lookY,
		//		lookZ, upX, upY, upZ);
		
		mMVPMatrixHandle = glGetUniformLocation(program, "u_MVPMatrix");
		mMVMatrixHandle = glGetUniformLocation(program, "u_MVMatrix");
		mTextureUniformHandle = glGetUniformLocation(program, "u_Texture");
		mPositionHandle = glGetAttribLocation(program, "a_Position");
		mTextureCoordHandle = glGetAttribLocation(program, "a_TexCoordinate");
		
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, baleTextureDataHandle);
        glUniform1i(mTextureUniformHandle, 0);
        
        int j = 0;
        float x, z;
        
        for (int i = 0; i < 6; i++) {
        	x = obstacles[j++];
        	z = obstacles[j++];
	        setIdentityM(mModelMatrix, 0);
	        translateM(mModelMatrix, 0, x, 0.0f, z);
	        scaleM(mModelMatrix, 0, 1.5f, 2.0f, 1.5f);
	    	drawBale();
        }
        
        setIdentityM(mModelMatrix, 0);
        translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
        drawBale();
        
        glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, buoyTextureDataHandle); 
        glUniform1i(mTextureUniformHandle, 0);
        
        for (int i = 0; i< 31; i++) {
        	x = obstacles[j++];
        	z = obstacles[j++];
        	setIdentityM(mModelMatrix, 0);
            translateM(mModelMatrix, 0, x, 0.0f, z);
            scaleM(mModelMatrix, 0, 0.1f, 0.25f, 0.1f);
    		drawBuoy();
        }
        
        glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, planeTextureDataHandle); 
        glUniform1i(mTextureUniformHandle, 0);
        
        setIdentityM(mModelMatrix, 0);
        translateM(mModelMatrix, 0, 96.0f, -1.0f, 16.0f);
        scaleM(mModelMatrix, 0, 200.0f, 200.0f, 200.0f);
        drawPlane();
	}
	
	private void drawBale() {
		balevertexData.position(0);
		glVertexAttribPointer(mPositionHandle, POSITION_COMPONENT_COUNT,
				GL_FLOAT, false, 0, balevertexData);

		glEnableVertexAttribArray(mPositionHandle);
		
        baletextureData.position(0);
        glVertexAttribPointer(mTextureCoordHandle, TEXTURE_COMPONENT_COUNT, GL_FLOAT, false, 
        		0, baletextureData);
        
        glEnableVertexAttribArray(mTextureCoordHandle);

		multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

		glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVPMatrix, 0);

		multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

		glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

		glDrawArrays(GL_TRIANGLES, 0, 60);
		glDrawArrays(GL_TRIANGLE_FAN, 60, 12);
		glDrawArrays(GL_TRIANGLE_FAN, 72, 12);
	}

	private void drawBuoy() {
		buoyvertexData.position(0);
		glVertexAttribPointer(mPositionHandle, POSITION_COMPONENT_COUNT,
				GL_FLOAT, false, 0, buoyvertexData);

		glEnableVertexAttribArray(mPositionHandle);

        buoytextureData.position(0);
        glVertexAttribPointer(mTextureCoordHandle, TEXTURE_COMPONENT_COUNT, GL_FLOAT, false, 
        		0, buoytextureData);
        
        glEnableVertexAttribArray(mTextureCoordHandle);

		multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

		glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVPMatrix, 0);

		multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

		glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

		glDrawArrays(GL_TRIANGLE_FAN, 0, 12);
		glDrawArrays(GL_TRIANGLE_FAN, 12, 12);
	}
	
	private void drawPlane() {
		planevertexData.position(0);
		glVertexAttribPointer(mPositionHandle, POSITION_COMPONENT_COUNT,
				GL_FLOAT, false, 0, planevertexData);

		glEnableVertexAttribArray(mPositionHandle);
		
		planetextureData.position(0);
        glVertexAttribPointer(mTextureCoordHandle, TEXTURE_COMPONENT_COUNT, GL_FLOAT, false, 
        		0, planetextureData);
        
        glEnableVertexAttribArray(mTextureCoordHandle);

		multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

		glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVPMatrix, 0);

		multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

		glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

		glDrawArrays(GL_TRIANGLES, 0, 6);
	}
}
