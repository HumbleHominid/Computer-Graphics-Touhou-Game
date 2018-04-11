public class Danmakufu implements Drawable {
    // positions
    private double _x, _y;

    // velocities
    private double _xVel, _yVel;

    // if the Danmakufu is being used somehow
    private boolean _inUse = false;

    private Danmakufu _next;

    public void init(double x, double y, double xVel, double yVel) {
        // Positions
        _x = x;
        _y = y;

        // Velocities
        _xVel = xVel;
        _yVel = yVel;

        // Say it is being used
        _inUse = true;
    }

    // Clear reverences to other object when marking not in use if necessary
    public void update() {
        // The game tickrate is 60 Hz
        _x = _x + _xVel;
        _y = _y + _yVel;

        // FIX. HARD CODE BAD
        if (_x < 0 || _y < 0 || _x > 10 || _y > 10) {
            _inUse = false;
        }
    }

    public void render(double elapsed) {
        if (!_inUse) {
            return;
        }

        // Don't forget to extrapolate the rendering
        // The game tickrate is 60 Hz
    }

    public boolean isInUse() {
        return _inUse;
    }

    public final Danmakufu getNext() {
        return _next;
    }

    public void setNext(Danmakufu next) {
        _next = next;
    }
}
