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

    public void render(GLAutoDrawable glAD, double elapsed) {
        if (!isInUse()) {
            return;
        }

        GL4 gl = (GL4) glAD.getGL();

        gl.glUseProgram(_model.getRenderingProgram());

        // Make the translation matrix
        Matrix3D mMat = new Matrix3D();
        mMat.translate(_x + _xVel * elapsed, _y + _yVel * elapsed, 0.0);
        mMat.scale(0.007, 0.02, 0.0);

        int mv_loc = gl.glGetUniformLocation(_model.getRenderingProgram(),
                "mv_matrix");

        // Predict where the object should be between update calls
        gl.glUniformMatrix4fv(mv_loc, 1, false, mMat.getFloatValues(), 0);

        // set up arrays to draw
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
