import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;

import java.util.Random;

public class PlayerParticle implements Particle {
    private Model _model;
    private double _x, _y;

    private double _xVel, _yVel;

    private int _lifetime;

    public PlayerParticle(Model model) {
        _model = model;

        _lifetime = 0;
    }

    public void init(double x, double y, int lifetime) {
        // position
        _x = x;
        _y = y;

        _lifetime = lifetime;

        // set the _xVel, _yVel dependent on the time created
        int angle = (int) (System.nanoTime() % 360);
        double scale = new Random(System.nanoTime()).nextDouble();

        _xVel = Math.cos(angle) * 1.5;
        _yVel = Math.sin(angle) * 1.5;
    }

    public PlayerParticle cloneParticle() {
        return new PlayerParticle(_model);
    }

    public void render(GLAutoDrawable glAD, double elapsed, Matrix3D pMat) {
        if (!isInUse()) {
            return;
        }

        // Make the model matrix
        Matrix3D mMat = new Matrix3D();

        // translate
        mMat.translate(_x + (_xVel * elapsed), _y + (_yVel * elapsed), 0.0f);

        // scale
        mMat.scale(_model.getScale(), _model.getScale(), 0.0f);

        Renderer.render(glAD, pMat, mMat, _model);
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
