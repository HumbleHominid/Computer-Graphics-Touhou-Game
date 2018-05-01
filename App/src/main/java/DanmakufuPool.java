import graphicslib3D.*;

import com.jogamp.opengl.*;

public class DanmakufuPool {
    // Container
    private Danmakufu[] _pool;

    // First free danmakufu in the list
    private Danmakufu _firstAvailable;

    public DanmakufuPool() {
        _pool = new Danmakufu[2000];

        fillPool();
    }

    public int getPoolSize() {
        return _pool.length;
    }

    private void fillPool() {
        _pool[_pool.length - 1] = new Danmakufu();
        _pool[_pool.length - 1].setNext(null);
        _firstAvailable = _pool[_pool.length - 1];

        // iterating down since they all have to be linked to each other
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
            int lifetime, Model model) {
        if (_firstAvailable == null || model == null) {
            return;
        }

        Danmakufu newDan = _firstAvailable;
        _firstAvailable = newDan.getNext();

        newDan.init(x, y, xVel, yVel, lifetime, model);
    }
}
