import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;

public class Orbital {
    private Model _model;

    private double _radius;
    private double _angle; // in degrees
    private double _rate = 0.03; // degrees moved per update

    public Orbital(Model model) {
        _model = model;

        _radius = 2.0f;
        _angle = System.currentTimeMillis() % 360;
    }

    public void render(GLAutoDrawable glAD, double elapsed, Matrix3D pMat,
            Matrix3D hMat) {
        GL4 gl = (GL4) glAD.getGL();

        Matrix3D mMat = hMat;

        double newAngle = _angle + (_rate * elapsed);

        mMat.translate(_radius * Math.sin(newAngle), _radius * Math.cos(newAngle), 0.0f);

        // Get pointers to the matrix locations
        int m_loc = gl.glGetUniformLocation(_model.getRenderingProgram(),
                "mv_matrix");
        int p_loc = gl.glGetUniformLocation(_model.getRenderingProgram(),
                "p_matrix");

        // Bind matrices
        gl.glUniformMatrix4fv(m_loc, 1, false, mMat.getFloatValues(), 0);
        gl.glUniformMatrix4fv(p_loc, 1, false, pMat.getFloatValues(), 0);

        // Set up arrays to draw
        gl.glBindBuffer(GL_ARRAY_BUFFER, _model.getVBO()[0]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, _model.getVBO()[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        // Blend the pixels
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl.glBlendEquation(GL_FUNC_ADD);

        // activate the textures
        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, _model.getTexture().getTextureObject());

        // Draw the thing
        gl.glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    public void update() {
        _angle = (_angle + _rate) % 360;
    }
}
