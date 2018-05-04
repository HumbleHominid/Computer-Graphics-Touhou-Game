import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;

import java.util.Random;

public class Orbital {
    private Model _model;

    private double _radius;
    private double _angle; // in degrees
    private double _rate; // degrees moved per update

    public Orbital(Model model) {
        _model = model;

        Random rand = new Random(System.nanoTime());

        _radius = 1.0f + rand.nextDouble();
        _angle = rand.nextInt(360);
        _rate = rand.nextDouble() / 20;
    }

    public void render(GLAutoDrawable glAD, double elapsed, Matrix3D pMat,
            Matrix3D mMat) {
        double newAngle = _angle + (_rate * elapsed);

        mMat.translate(_radius * Math.sin(newAngle), _radius * Math.cos(newAngle), 0.0f);

        Renderer.render(glAD, pMat, mMat, _model);
    }

    public void update() {
        _angle = (_angle + _rate) % 360;
    }
}
