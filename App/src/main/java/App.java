import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.common.nio.Buffers;

import java.awt.*;

import javax.swing.*;
import java.nio.*;

public class App extends JFrame implements GLEventListener {
    // Game tick set to 60 ticks per second
    private final double NS_PER_UPDATE = 1000000000.0 / 60;
    private GLCanvas _myCanvas;
    // Frame tracking stuff
    // List of the past N render times. for getting fps values
    private long[] _renderTimes = new long[50];
    // Count of _renderTimes added. for looping
    private int _numRenderTimes = _renderTimes.length;
    // The amount of the next frame we covered in our update method
    private double _elapsed = 0.0;
    // The pool of danmakufu objects
    private DanmakufuPool _danmakufuPool;
    // The text renderer for rendering the framerate
    private TextRenderer _renderer;

    public App() {
        // set the window title
        setTitle("Crappy Touhou Clone.exe");
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
     * by NS_PER_UPDATE. Will render the game as fast as it can.
     */
    public void gameLoop() {
        long current; // nanoTime
        long _renderTime; // ns since last frame
        double lag = 0.0;
        long previous = System.nanoTime(); // nanoTime

        while (true) {
            current = System.nanoTime();
            _renderTime = current - previous;
            lag = lag + _renderTime;
            previous = current;

            // process the input
            processInput();

            // keep updating the game while we are running at slow fps
            while (lag >= NS_PER_UPDATE) {
                update();

                lag = lag - NS_PER_UPDATE;
            }

            // Track the elapsed time for displaying fps purposes
            if (_numRenderTimes == _renderTimes.length) {
                _numRenderTimes = 0;
            }

            _renderTimes[_numRenderTimes] = _renderTime;

            _numRenderTimes++;

            // set frameCovered. this is for extrapolating rendering
            _elapsed = lag / NS_PER_UPDATE;

            // tell the canvas to display all its listeners
            _myCanvas.display();
        }
    }

    public void processInput() {
        // TODO
    }

    public void update() {
        _danmakufuPool.update();
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
        GL4 gl = (GL4) glAD.getGL();

        // Clearing the canvas is important
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);

        // Display the danmakufuPool
        _danmakufuPool.render(glAD, _elapsed);

        // Print some text
        _renderer.beginRendering(glAD.getSurfaceWidth(),
                glAD.getSurfaceHeight());
        _renderer.setColor(Color.YELLOW);
        _renderer.setSmoothing(true);

        // Get the sum of the render times in the list
        double totalTime = 0;

        for (int i = 0; i < _renderTimes.length; i++) {
            totalTime = totalTime + _renderTimes[i];
        }

        // nanoTime to millis
        totalTime = totalTime / 1000000.0;

        // Display the average of the render times
        String frames = String.format("%.02f ms",
                totalTime / (double) _renderTimes.length);
        _renderer.draw(frames, 0, 0);
        _renderer.endRendering();
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

        // Create danmakufu pool
        _danmakufuPool = new DanmakufuPool();

        _danmakufuPool.addDanmakufu(0, 0, -0.001, 0, 150);
        _danmakufuPool.addDanmakufu(0, 0, 0, 0.002, 200);
        _danmakufuPool.addDanmakufu(0, 0, 0.001, 0.001, 250);

        // Create text renderer
        Font font = new Font("Verdana", Font.BOLD, 18);

        // Make the text renderer with the given font
        _renderer = new TextRenderer(font);
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
