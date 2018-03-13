import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;

public class App extends JFrame implements GLEventListener {
    // Game tick set to 60 ticks per second
    private final double MS_PER_UPDATE = 1000 / 60;
    private GLCanvas _myCanvas;
    // Frame tracking stuff
    // list of the past N render times. for getting fps values
    private ArrayList<Double> _renderTimes = new ArrayList<Double>();
    // the amount of the next frame we covered in our update method
    private double _frameCovered = 0.0;

    public App() {
        // Set the window properties
        setTitle("Crappy Touhou Clone dot EXE");
        setSize(800, 600);

        // Create new canvas
        _myCanvas = new GLCanvas();

        // Add canvas listeners
        _myCanvas.addGLEventListener(this);

        // Add my canvas to the pane and set it to visible
        getContentPane().add(_myCanvas);
        setVisible(true);
    }

    /**
     * Main game loop function. updates the game according to the tickrate set
     * by MS_PER_UPDATE. Will render the game as fast as it can.
     */
    public void gameLoop() {
        double current;
        double elapsed;
        double lag = 0.0;
        double previous = System.currentTimeMillis();

        while (true) {
            current = System.currentTimeMillis();
            elapsed = current - previous;

            lag = lag + elapsed;
            previous = current;

            processInput();

            // keep updating the game while we are running at slow fps
            while (lag >= MS_PER_UPDATE) {
                update();

                lag = lag - MS_PER_UPDATE;
            }

            // Track the elapsed time for displaying fps purposes
            _renderTimes.add(elapsed);

            if (_renderTimes.size() > 10) {
                _renderTimes.remove(0);
            }

            // set frameCovered. this is for extrapolating rendering
            _frameCovered = lag / MS_PER_UPDATE;

            _myCanvas.display();
        }
    }

    public void processInput() {
        // TODO
    }

    public void update() {
        // TODO
    }

    /* ************************
     * GLEventListener Handlers
     * ***********************/
    /*
     * (non-Javadoc)
     * @see com.jogamp.opengl.GLEventListener#display(com.jogamp.opengl.GLAutoDrawable)
     */
    @Override
    public void display(GLAutoDrawable glAD) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Clearing the canvas is important
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // print some text
        TextRenderer textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 18));

        textRenderer.beginRendering(glAD.getSurfaceWidth(), glAD.getSurfaceHeight());
        textRenderer.setColor(Color.YELLOW);
        textRenderer.setSmoothing(true);

        // Get the sum of the render times in the list
        double totalTime = 0;

        for (Double d : _renderTimes) {
            totalTime = totalTime + d;
        }

        // Display the average of the render times
        textRenderer.draw(String.format("%.01f ms", totalTime / _renderTimes.size()), 0, 0);
        textRenderer.endRendering();
    }

    /*
     * (non-Javadoc)
     * @see com.jogamp.opengl.GLEventListener#dispose(com.jogamp.opengl.GLAutoDrawable)
     */
    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO
    }

    /*
     * (non-Javadoc)
     * @see com.jogamp.opengl.GLEventListener#init(com.jogamp.opengl.GLAutoDrawable)
     */
    @Override
    public void init(GLAutoDrawable arg0) {
        // TODO
    }

    /*
     * (non-Javadoc)
     * @see com.jogamp.opengl.GLEventListener#reshape(com.jogamp.opengl.GLAutoDrawable, int, int, int, int)
     */
    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
        // TODO
    }

    /*
     * main
     */
    public static void main(String[] args) {
        App app = new App();
        app.gameLoop();
    }
}
