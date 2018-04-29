import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.GLContext;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.texture.*;

import java.io.*;
import java.nio.FloatBuffer;

public class DanmakufuModel {
    // Reference to the rendering program being used
    private int _renderingProgram;
    // VAO
    private int[] _vao = new int[1];
    // VAB
    private int[] _vbo = new int[2];
    // Reference to texture
    private Texture _texture;
    private float _scale;

    public DanmakufuModel(String vertShaderPath, String fragShaderPath,
            String texturePath, float scale) {
        _scale = scale;
        _renderingProgram = createShaderProgram(vertShaderPath, fragShaderPath);
        _texture = loadTexture(texturePath);
        setupVertices();
    }

    public final int getRenderingProgram() {
        return _renderingProgram;
    }

    public final int[] getVAO() {
        return _vao;
    }

    public final int[] getVBO() {
        return _vbo;
    }

    public final Texture getTexture() {
        return _texture;
    }

    public final float getScale() {
        return _scale;
    }

    private void setupVertices() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Aspect ratio of the texture
        float texAr = (_texture == null) ? 1.0f :
                (float) _texture.getHeight() / (float) _texture.getWidth();

        // Build the vertex positions based on the aspect ration of the texture
        float[] vertex_positions = {
            // Top Triangle
            -1.0f, texAr, 1.0f, // Top Left
            1.0f, -texAr, 1.0f, // Bottom Right
            1.0f, texAr, 1.0f,  // Top Right
            // Bottom Triangle
            -1.0f, texAr, 1.0f, // Top Left
            1.0f, -texAr, 1.0f, // Bottom Right
            -1.0f, -texAr, 1.0f // Bottom Left
        };

        float[] texture_coordinates = {
            // Top Triangle
            0.0f, 1.0f, // Top Left
            1.0f, 0.0f, // Bottom Right
            1.0f, 1.0f, // Top Right
            // Bottom Triangle
            0.0f, 1.0f, // Top Left
            1.0f, 0.0f, // Bottom Right
            0.0f, 0.0f  // Bottom Left
        };

        gl.glGenVertexArrays(_vao.length, _vao, 0);
        gl.glBindVertexArray(_vao[0]);
        gl.glGenBuffers(_vbo.length, _vbo, 0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, _vbo[0]);
        FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(vertex_positions);
        gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit() * 4, vertBuf,
                GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, _vbo[1]);
        FloatBuffer texBuff = Buffers.newDirectFloatBuffer(texture_coordinates);
        gl.glBufferData(GL_ARRAY_BUFFER, texBuff.limit() * 4, texBuff,
                GL_STATIC_DRAW);
    }

    // Need to be pulled out probably
    private int createShaderProgram(String vertShaderPath, String fragShaderPath) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        String vshaderSource[] = readShaderSource(vertShaderPath);
        String fshaderSource[] = readShaderSource(fragShaderPath);

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

    // Need to be pulled out probably
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
            fr.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();

            return null;
        }
        catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        return lines;
    }

    private Texture loadTexture(String texturePath) {
        Texture tex = null;

        try {
            tex = TextureIO.newTexture(new File(texturePath), true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return tex;
    }
}
