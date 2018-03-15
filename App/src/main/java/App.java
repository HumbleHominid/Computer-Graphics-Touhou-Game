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
    private ArrayList<Long> _renderTimes = new ArrayList<Long>();
    // the amount of the next frame we covered in our update method
    private double _frameCovered = 0.0;

    public App() {
        // set the window title
        setTitle("Crappy Touhou Clone dot EXE");
        // set the window size
        setSize(1600, 900);
        // set window to fullscreen
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
        // remove bar on top of window
        // setUndecorated(true);
        // disable window resize
        setResizable(false);
        // set window 'x' to close
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        long current;
        long elapsed;
        double lag = 0.0;
        long previous = System.nanoTime();

        while (true) {
            current = System.nanoTime();
            elapsed = current - previous;
            lag = lag + elapsed;
            previous = current;

            // process the input
            processInput();

            // keep updating the game while we are running at slow fps
            while (lag >= MS_PER_UPDATE) {
                update();

                lag = lag - MS_PER_UPDATE;
            }

            // Track the elapsed time for displaying fps purposes
            _renderTimes.add(elapsed);

            if (_renderTimes.size() > 50) {
                _renderTimes.remove(0);
            }

            // set frameCovered. this is for extrapolating rendering
            _frameCovered = lag / MS_PER_UPDATE;

            // tell the canvas to display all its listeners
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
        TextRenderer textRenderer = new TextRenderer(new Font("Verdana",
                Font.BOLD, 18));

        textRenderer.beginRendering(glAD.getSurfaceWidth(),
                glAD.getSurfaceHeight());
        textRenderer.setColor(Color.YELLOW);
        textRenderer.setSmoothing(true);

        // Get the sum of the render times in the list
        double totalTime = 0;

        for (Long d : _renderTimes) {
            totalTime = totalTime + d;
        }

        // nanoTime to millis
        totalTime = totalTime / 1000000;

        // Display the average of the render times
        textRenderer.draw(String.format("%.02f ms",
                totalTime / _renderTimes.size()), 0, 0);
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
    public void init(GLAutoDrawable glAD) {
        // Disable V-Sync. Bad for poopoo computers though
        glAD.getGL().setSwapInterval(0);
    }

    /*
     * (non-Javadoc)
     * @see com.jogamp.opengl.GLEventListener#reshape(com.jogamp.opengl.GLAutoDrawable, int, int, int, int)
     */
    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
            int arg4) {
        // TODO
    }

    /*
     * main
     */
    public static void main(String[] args) {
        new App().gameLoop();
    }
}
