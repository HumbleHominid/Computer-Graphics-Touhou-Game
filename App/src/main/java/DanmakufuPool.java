import graphicslib3D.*;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;

public class DanmakufuPool {
    // enum of all the danmakufs
    public enum ModelList {
        ARROW("arrow.png", 30.0f),
        PINK_CIRCLE("pink_circle.png", 30.0f),
        STAR("star.png", 30.0f),
        FLOWER("flower.png", 25.0f);

        private Model _model;

        private ModelList(String tex, float scale) {
            _model = new Model("assets/GLSL/vertex/dan.shader",
                    "assets/GLSL/fragment/dan.shader", "assets/images/" + tex,
                    scale);
        }

        public Model getModel() {
            return _model;
        }
    }

    // make the enum public
    public ModelList modelList;

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
