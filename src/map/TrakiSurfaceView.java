package map;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class TrakiSurfaceView extends GLSurfaceView {

	private TrakiMapRenderer mRenderer;

	public TrakiSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TrakiSurfaceView(Context context) {
		super(context);
		//setRenderMode(RENDERMODE_WHEN_DIRTY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = 0, y = 0;
		
		if (event != null) {		
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				x = event.getX();
				y = event.getY();
				Log.d("press", Float.toString(x) + " " + Float.toString(y));
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				final float deltax, deltay;
				
				deltax = event.getX() - x;
				deltay = event.getY() - y;
				
				x = event.getX();
				y = event.getY();
				
				queueEvent(new Runnable() {
					
					@Override
					public void run() {
						mRenderer.handledrag(deltax, deltay);
					}
				});
				
				Log.d("move", Float.toString(x) + " " + Float.toString(y));
			}
			return true;
		} else {
			return super.onTouchEvent(event);
		}
	}

	public void setRenderer(TrakiMapRenderer renderer) {
		mRenderer = renderer;
		super.setRenderer(renderer);
	}

	public void cam() {
		mRenderer.incangle(false);
	}
}
