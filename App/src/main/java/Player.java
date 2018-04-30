import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import java.util.HashSet;
import java.awt.event.KeyEvent;

public class Player {
    // the players model
    private Model _model;
    // coords
    private double _x, _y;
    // velocities
    private double _xVel, _yVel;

    public Player() {
        // set the initial position
        _x = 500.0;
        _y = 500.0;

        // set the initial velocity
        _xVel = 0.25f;
        _yVel = 0.25f;
    }

    public void setModel(Model model) {
        _model = model;
    }

    public void render(GLAutoDrawable glAD, Matrix3D pMat) {
        GL4 gl = (GL4) glAD.getGL();

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

    public void processInput(HashSet<Integer> pressed) {
        // Scale the velocity down if alt is held
        float velScale = pressed.contains(KeyEvent.VK_ALT) ? 0.25f : 1.0f;

        for (Integer c : pressed) {
            switch (c) {
                case KeyEvent.VK_W:
                    _y = _y + (_yVel * velScale);
                    break;
                case KeyEvent.VK_A:
                    _x = _x - (_xVel * velScale);
                    break;
                case KeyEvent.VK_S:
                    _y = _y - (_yVel * velScale);
                    break;
                case KeyEvent.VK_D:
                    _x = _x + (_xVel * velScale);
                    break;
            }
        }
    }
}
