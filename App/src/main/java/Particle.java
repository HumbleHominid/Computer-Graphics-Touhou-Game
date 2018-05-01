import graphicslib3D.*;

import com.jogamp.opengl.*;

public interface Particle {
    Particle cloneParticle();
    void render(GLAutoDrawable glAD, double elapsed, Matrix3D pMat);
    boolean isInUse();
    boolean update();
}
