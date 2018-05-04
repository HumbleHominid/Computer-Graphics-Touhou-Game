import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;

public class Renderer {
    public static void render(GLAutoDrawable glAD, Matrix3D pMat, Matrix3D mMat,
            Model model) {
        GL4 gl = (GL4) glAD.getGL();

        gl.glUseProgram(model.getRenderingProgram());

        // Get "pointers" to the matrix locations
        int m_loc = gl.glGetUniformLocation(model.getRenderingProgram(),
                "mv_matrix");
        int p_loc = gl.glGetUniformLocation(model.getRenderingProgram(),
                "p_matrix");

        // Bind matricies
        gl.glUniformMatrix4fv(m_loc, 1, false, mMat.getFloatValues(), 0);
        gl.glUniformMatrix4fv(p_loc, 1, false, pMat.getFloatValues(), 0);

        // Set up arrays to draw
        gl.glBindBuffer(GL_ARRAY_BUFFER, model.getVBO()[0]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, model.getVBO()[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        // Blend the pixels
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl.glBlendEquation(GL_FUNC_ADD);

        // activate the textures
        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, model.getTexture().getTextureObject());

        // Draw the thing
        gl.glDrawArrays(GL_TRIANGLES, 0, 6);
    }
}
