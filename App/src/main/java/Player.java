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
    }

    public void setModel(Model model) {
        _model = model;
    }

    public void render(GLAutoDrawable glAD, double elapsed, Matrix3D pMat) {
        GL4 gl = (GL4) glAD.getGL();

        // draw the particles
        for (Particle p : _particlePool) {
            p.render(glAD, elapsed, pMat);
        }

        // Use the rendering program associated with the model
        gl.glUseProgram(_model.getRenderingProgram());

        // Make the model matrix
        Matrix3D mMat = new Matrix3D();

        // translate
        mMat.translate(_x, _y, 0.0f);

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

        // activate the textures
        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, _model.getTexture().getTextureObject());

        // Draw the thing
        gl.glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    public void update() {
        for (Particle p : _particlePool) {
            p.update();
        }
    }

    public void processInput(HashSet<Integer> pressed) {
        _input.processInput(this, pressed);

        //create a new particle
        for (Particle p : _particlePool) {
            if (!p.isInUse()) {
                ((PlayerParticle) p).init(_x, _y, new Random(System.nanoTime()).nextInt() % 300);
                return;
            }
        }
    }
}
