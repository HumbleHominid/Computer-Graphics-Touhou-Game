import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.common.nio.Buffers;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.nio.*;
import java.util.Random;
import java.util.HashSet;

public class App extends JFrame implements GLEventListener, KeyListener {
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
    // The perspective matrix to be used
    private Matrix3D _pMat;
    // the player
    private Player _player;
    // a hashed set of the keys being pressed
    private HashSet<Integer> _pressed = new HashSet<Integer>();

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
        _myCanvas.addKeyListener(this);

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

            _renderTimes[_numRenderTimes++] = _renderTime;

            // set frameCovered. this is for extrapolating rendering
            _elapsed = lag / NS_PER_UPDATE;

            // tell the canvas to display all its listeners
            _myCanvas.display();
        }
    }

    // User Interaction
    public void processInput() {
        if (_player != null) {
            _player.processInput(_pressed);
        }
    }

    // Physics and Collision I guess idk
    public void update() {
        _danmakufuPool.update();

        _player.update();
    }

    // Clears the canvas
    private void clearCanvas(GLAutoDrawable glAD) {
        GL4 gl = (GL4) glAD.getGL();

        // Clearing the canvas is important
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
    }

    // Draws the background
    private void drawBackground(GLAutoDrawable glAD) {
        GL4 gl = (GL4) glAD.getGL();

        float[] bkg = { 0.1f, 0.1f, 0.1f, 1.0f };
        FloatBuffer buff = Buffers.newDirectFloatBuffer(bkg);
        gl.glClearBufferfv(GL_COLOR, 0, buff);
    }

    // Defines an orthographic perspective
    private Matrix3D perspective() {
        Matrix3D perspective = new Matrix3D();

        float canvasWidth = (float) _myCanvas.getWidth();
        float canvasHeight = (float) _myCanvas.getHeight();

        // bounds
        float top = canvasHeight, right = canvasWidth, bot = 0.0f, left = 0.0f;
        // clipping
        float near = -1.0f, far = 1.0f;
        // vars
        float width = right - left;
        float height = top - bot;
        float clipDiff = far - near;

        // viewport x
        perspective.setElementAt(0, 0, 2.0f / width);
        perspective.setElementAt(0, 3, -1.0f * ((right + left) / width));
        // viewport y
        perspective.setElementAt(1, 1, 2.0f / height);
        perspective.setElementAt(1, 3, -1.0f * ((top + bot) / height));
        // viewport z
        perspective.setElementAt(2, 2, 1.0f / clipDiff);
        perspective.setElementAt(2, 3, -1.0f * (near / clipDiff));

        return perspective;
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
        clearCanvas(glAD);

        drawBackground(glAD);

        // Display the player
        _player.render(glAD, _elapsed, _pMat);

        // Display the danmakufuPool
        _danmakufuPool.render(glAD, _elapsed, _pMat);
    }

    /*
     * (non-Javadoc)
     * @see com.jogamp.opengl.GLEventListener#dispose(com.jogamp.opengl.GLAutoDrawable)
     */
    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO idk what this does
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

        // Create the orthographic perspective matrix
        _pMat = perspective();

        // create a new player
        _player = new Player();
        _player.setModel(new Model("assets/images/juice.jpg", 75.0f));

        // Testing stuff; please ignore
        if (false) {
            Random rand;
            Model model;
            rand = new Random(System.currentTimeMillis());

            for (int i = 0; i < _danmakufuPool.getPoolSize() / 2; i++) {
                double x = rand.nextDouble() * _myCanvas.getWidth();
                double y = rand.nextDouble() * _myCanvas.getHeight();
                double xVel = (rand.nextDouble() * 2) * (rand.nextInt() % 2 == 0 ? 1 : -1);
                double yVel = (rand.nextDouble() * 2) * (rand.nextInt() % 2 == 0 ? 1 : -1);
                int lifetime = rand.nextInt() % 1000;

                model = DanmakufuModels.values()[rand.nextInt(DanmakufuModels.values().length)].getModel();

                _danmakufuPool.addDanmakufu(x, y, xVel, yVel, lifetime, model);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.jogamp.opengl.GLEventListener#reshape(com.jogamp.opengl.GLAutoDrawable, int, int, int, int)
     */
    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
            int arg4) {
        _pMat = perspective();
    }

    /* ********************
     * KeyListener Handlers
     * *******************/
    /*
     * (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) {
        _pressed.add(e.getKeyCode());
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e) {
        _pressed.remove(e.getKeyCode());
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO or not lol
    }

    /*
     * main
     */
    // scary
    public static void main(String[] args) {
        new App().gameLoop();
    }
}
