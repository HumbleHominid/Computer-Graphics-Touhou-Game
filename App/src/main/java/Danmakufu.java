import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.GLContext;
import com.jogamp.common.nio.Buffers;

import java.io.*;

public class Danmakufu {
    // positions
    private double _x, _y;

    // velocities
    private double _xVel, _yVel;

    // lifetime in update cycles
    private int _lifetime = 0;

    // the next Danmakufu in the list
    private Danmakufu _next;

    // Pointer do the rendering program being used
    private int _renderingProgram;

    public Danmakufu() {
        _renderingProgram = createShaderProgram();
    }

    public void init(double x, double y, double xVel, double yVel, int lifetime) {
        // Positions
        _x = x;
        _y = y;

        // Velocities
        _xVel = xVel;
        _yVel = yVel;

        // Lifetime
        _lifetime = lifetime;
    }

    // Clear references to other object when marking not in use if necessary
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

        gl.glUseProgram(_renderingProgram);

        int x = gl.glGetUniformLocation(_renderingProgram, "x");
        int y = gl.glGetUniformLocation(_renderingProgram, "y");

        // Predict where the object should be between update calls
        gl.glProgramUniform1f(_renderingProgram, x, (float) (_x + _xVel * elapsed));
        gl.glProgramUniform1f(_renderingProgram, y, (float) (_y + _yVel * elapsed));

        gl.glDrawArrays(GL_TRIANGLES, 0, 3);
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

    // These need to be pulled out later
    private int createShaderProgram() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        String vshaderSource[] = readShaderSource("assets/GLSL/vertex/tri.shader");
        String fshaderSource[] = readShaderSource("assets/GLSL/fragment/tri.shader");

        int lengths[];

        int vShader = gl.glCreateShader(GL_VERTEX_SHADER);
        int fShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

        gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);
        gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0);

        gl.glCompileShader(vShader);
        gl.glCompileShader(fShader);

        int vfprogram = gl.glCreateProgram();

        gl.glAttachShader(vfprogram, vShader);
        gl.glAttachShader(vfprogram, fShader);
        gl.glLinkProgram(vfprogram);

        return vfprogram;
    }

    // These need to be pulled out later
    private String[] readShaderSource(String filename) {
        String lines[];

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            // Lambdas are the best
            lines = br.lines().toArray(String[]::new);

            for (int i = 0; i < lines.length; i++) {
                lines[i] = lines[i] + "\n";
            }

            br.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Failed to find fragment shader file.");

            return null;
        }
        catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        return lines;
    }
}
