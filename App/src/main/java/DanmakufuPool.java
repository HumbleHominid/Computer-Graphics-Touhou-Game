import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;

public class DanmakufuPool {
    private Danmakufu[] _pool;

    private Danmakufu _firstAvailable;
    // Delet
    private DanmakufuModel dm = new DanmakufuModel("assets/GLSL/vertex/dan.shader",
            "assets/GLSL/fragment/dan.shader", "assets/images/pill_danmakufu.png");

    public DanmakufuPool() {
        _pool = new Danmakufu[2000];

        fillPool();
    }

    // not sure i need this. will remove if excess overhead
    public DanmakufuPool(int size) {
        _pool = new Danmakufu[size];

        fillPool();
    }

    public int getPoolSize() {
        return _pool.length;
    }

    private void fillPool() {
        _pool[_pool.length - 1] = new Danmakufu();
        _pool[_pool.length - 1].setNext(null);
        _firstAvailable = _pool[_pool.length - 1];

        for (int i = _pool.length - 2; i >= 0; i--) {
            Danmakufu newDan = new Danmakufu();

            _pool[i] = newDan;
            _pool[i + 1].setNext(newDan);
        }
    }

    public void update() {
        for (int i = 0; i < _pool.length; i++) {
            Danmakufu danfu = _pool[i];

            if (!danfu.update()) {
                danfu.setNext(_firstAvailable);
                _firstAvailable = danfu;
            }
        }
    }

    public void render(GLAutoDrawable glAD, double elapsed, Matrix3D pMat) {
        for (int i = 0; i < _pool.length; i++) {
            _pool[i].render(glAD, elapsed, pMat);
        }
    }

    public void addDanmakufu(double x, double y, double xVel, double yVel,
            int lifetime) {
        if (_firstAvailable == null) {
            return;
        }

        Danmakufu newDan = _firstAvailable;
        _firstAvailable = newDan.getNext();

        newDan.init(x, y, xVel, yVel, lifetime, dm);
    }
}
