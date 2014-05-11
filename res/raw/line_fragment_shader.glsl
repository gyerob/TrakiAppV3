precision mediump float;       	// Set the default precision to medium. We don't need as high of a 
								// precision in the fragment shader.

varying vec4 v_Color;          	// This is the color from the vertex shader interpolated across the 
  								// triangle per fragment.
  
// The entry point for our fragment shader.
void main()                    		
{                           

	// Multiply the color by the diffuse illumination level and texture value to get final output color.
    gl_FragColor = v_Color;                                  		
}                                                                     	

