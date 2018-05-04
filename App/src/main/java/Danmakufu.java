import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;

public class Danmakufu {
    // positions
    private double _x, _y;
    // velocities
    private double _xVel, _yVel;
    // lifetime in update cycles
    private int _lifetime;
    // The next danmakufu in the list
    private Danmakufu _next;
    // The danmakufu model that should be used for this danmakufu
    private Model _model;

    public void init(double x, double y, double xVel, double yVel,
            int lifetime, Model model) {
        // Positions
        _x = x;
        _y = y;

        // Velocities
        _xVel = xVel;
        _yVel = yVel;

        // Lifetime
        _lifetime = lifetime;

        // Model
        _model = model;
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

    public void render(GLAutoDrawable glAD, double elapsed, Matrix3D pMat) {
        if (!isInUse()) {
            return;
        }

        // Make the model matrix
        Matrix3D mMat = new Matrix3D();

        // translate; This is really physics. Should use a pysics componen
        //  probably
        mMat.translate(_x + _xVel * elapsed, _y + _yVel * elapsed, 0.0f);

        // scale
        mMat.scale(_model.getScale(), _model.getScale(), 0.0f);

        // rotate
        double rotAmt = 0.0f;

        if (_xVel == 0.0f) {
            rotAmt = _yVel < 0 ? 180.0f : 0.0f;
        }
        else if (_yVel == 0.0f) {
            rotAmt = _xVel < 0 ? 90.0f : 270.0f;
        }
        else {
            // Adjust 0 degree north orientation (java) to right orientation (jogl)
            rotAmt = (Math.toDegrees(Math.atan2(_yVel, _xVel)) - 90.0f);
        }

        // Rotation
        mMat.rotateZ(rotAmt);

        Renderer.render(glAD, pMat, mMat, _model);
    }

    public boolean isInUse() {
        return _lifetime > 0;
    }

    public final Danmakufu getNext() {
        return _next;
    }

    public void setNext(Danmakufu next) {
        _next = next;
    }
}
