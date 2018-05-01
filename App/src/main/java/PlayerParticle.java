import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;

import java.util.Random;

public class PlayerParticle implements Particle {
    private Model _model;
    private double _x, _y;

    private double _xVel = 0, _yVel = 0;

    private int _lifetime;

    public PlayerParticle(double x, double y, int lifetime, Model model) {
        // position
        _x = x;
        _y = y;

        _lifetime = lifetime;

        // model
        _model = model;

        // set the _xVel, _yVel dependent on the time created
        int angle = (int) (System.nanoTime() % 360);
        double scale = new Random(System.nanoTime()).nextDouble();

        _xVel = Math.cos(angle) * scale;
        _yVel = Math.sin(angle) * scale;
    }

    public PlayerParticle cloneParticle() {
        return new PlayerParticle(_x, _y, _lifetime, _model);
    }

    public void render(GLAutoDrawable glAD, double elapsed, Matrix3D pMat) {
        if (!isInUse()) {
            return;
        }

        GL4 gl = (GL4) glAD.getGL();

        // Use the rendering program associated with the model
        gl.glUseProgram(_model.getRenderingProgram());

        // Make the model matrix
        Matrix3D mMat = new Matrix3D();

        // translate
        mMat.translate(_x + (_xVel * elapsed), _y + (_yVel * elapsed), 0.0f);

        // scale
        mMat.scale(_model.getScale(), _model.getScale(), 0.0f);

        // Get pointers to the matrix locations
        int m_loc = gl.glGetUniformLocation(_model.getRenderingProgram(),
                "mv_matrix");
        int p_loc = gl.glGetUniformLocation(_model.getRenderingProgram(),
                "p_matrix");

        // Bind matricies
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

    public boolean isInUse() {
        return _lifetime > 0;
    }

    public boolean update() {
        // Return if update was called on a danmakufu that is not in use
        if (!isInUse()) {
            return false;
        }

        // Update the position
        _x = _x + _xVel;
        _y = _y + _yVel;

        // Update the lifetime
        _lifetime--;

        return _lifetime < 1;
    }
}
