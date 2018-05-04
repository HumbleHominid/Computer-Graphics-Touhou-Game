import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;

import java.util.HashSet;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Player {
    // the players model
    private Model _model;
    // coords
    double _x, _y;
    // velocities
    double _xVel, _yVel;
    // Maybe implement physics like this? Ends up being a component pattern
    // private Physics _physics
    // then call like _physics.doPhysics(this)
    private ProcessInputable _input;

    private Particle[] _particlePool = new Particle[200];
    private ParticleSpawner _particleSpawner;

    private Orbital[] _orbitals = new Orbital[6];

    public Player() {
        // set the initial position
        _x = 500.0;
        _y = 500.0;

        // set the initial velocity
        _xVel = 0.25f;
        _yVel = 0.25f;

        // set the input component
        _input = new PlayerInputComponent();

        Model playerModel = new Model("assets/images/player_particle.png", 12.0f);

        _particleSpawner = new ParticleSpawner(new PlayerParticle(playerModel));

        for (int i = 0; i < _particlePool.length; i++) {
            _particlePool[i] = _particleSpawner.spawnParticle();
        }

        for (int i = 0; i < _orbitals.length; i++) {
            Model orbitalModel = new Model("assets/images/orbital.png", 100.0f);

            _orbitals[i] = new Orbital(orbitalModel);
        }
    }

    public void setModel(Model model) {
        _model = model;
    }

    public void render(GLAutoDrawable glAD, double elapsed, Matrix3D pMat) {
        // draw the particles
        for (Particle p : _particlePool) {
            p.render(glAD, elapsed, pMat);
        }

        // Make the model matrix
        Matrix3D mMat = new Matrix3D();
        Matrix3D hMat; // Matrix to pass for hierarchical Modeling

        // translate
        mMat.translate(_x, _y, 0.0f);
        hMat = mMat;

        // scale
        mMat.scale(_model.getScale(), _model.getScale(), 0.0f);

        Renderer.render(glAD, pMat, mMat, _model);

        // Draw the orbitals
        for (Orbital o : _orbitals) {
            o.render(glAD, elapsed, pMat, (Matrix3D) hMat.clone());
        }
    }

    public void update() {
        for (Particle p : _particlePool) {
            p.update();
        }

        for (Orbital o : _orbitals) {
            o.update();
        }
    }

    public void processInput(HashSet<Integer> pressed) {
        _input.processInput(this, pressed);

        //create a new particle
        for (Particle p : _particlePool) {
            if (!p.isInUse()) {
                ((PlayerParticle) p).init(_x, _y,
                        new Random(System.nanoTime()).nextInt() % 300);
                return;
            }
        }
    }
}
