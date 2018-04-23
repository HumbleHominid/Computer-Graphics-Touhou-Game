import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.*;

public class Danmakufu {
    // positions
    private double _x, _y;
    // velocities
    private double _xVel, _yVel;
    // lifetime in update cycles
    private int _lifetime;
    // The next danmakufu in the list
    private Danmakufu _next;
    // The Danmakufu model that should be used for this Danmakufu
    private DanmakufuModel _model;

    public void init(double x, double y, double xVel, double yVel,
            int lifetime, DanmakufuModel dm) {
        // Positions
        _x = x;
        _y = y;

        // Velocities
        _xVel = xVel;
        _yVel = yVel;

        // Lifetime
        _lifetime = lifetime;

        // Model
        _model = dm;
    }

    public boolean update() {
        // Return if update was called on a Danmakufu that is not in use
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

    public void render(GLAutoDrawable glAD, double elapsed, Matrix3D pMat) {
        if (!isInUse()) {
            return;
        }

        GL4 gl = (GL4) glAD.getGL();

        gl.glUseProgram(_model.getRenderingProgram());

        // Make the model matrix
        Matrix3D mMat = new Matrix3D();

        // rotate
        double rotAmt = 0.0f;

        if (_xVel == 0.0) {
            rotAmt = _yVel < 0 ? 90.0f : 0.0f;
        }
        else if (_yVel == 0.0) {
            rotAmt = _xVel < 0 ? 45.0f : 135.0f;
        }
        else {
            // This is stupid. Have to change [ -180, 180 ] to [ -90, 90 ] and
            //  north orientation to right orientation
            rotAmt = (Math.toDegrees(Math.atan2(_yVel, _xVel)) -90.0f) / 2.0f;
        }
System.out.printf("%f%n", rotAmt);
        mMat.rotateZ(rotAmt);

        // translate
        mMat.translate(_x + _xVel * elapsed, _y + _yVel * elapsed, 0.0);
        // scale
        mMat.scale(0.5, 0.5, 0.0);

        int m_loc = gl.glGetUniformLocation(_model.getRenderingProgram(),
                "mv_matrix");
        int p_loc = gl.glGetUniformLocation(_model.getRenderingProgram(),
                "p_matrix");

        // Bind matricies
        gl.glUniformMatrix4fv(m_loc, 1, false, mMat.getFloatValues(), 0);
        gl.glUniformMatrix4fv(p_loc, 1, false, mMat.getFloatValues(), 0);

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

        gl.glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    public boolean isInUse() {
        return _lifetime > 1;
    }

    public final Danmakufu getNext() {
        return _next;
    }

    public void setNext(Danmakufu next) {
        _next = next;
    }
}
