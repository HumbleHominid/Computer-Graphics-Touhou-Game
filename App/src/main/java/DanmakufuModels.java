// enum of all the danmakufs
public enum DanmakufuModels {
    ARROW("arrow.png", 30.0f),
    PINK_CIRCLE("pink_circle.png", 30.0f),
    STAR("star.png", 30.0f),
    FLOWER("flower.png", 5.0f);

    private Model _model;

    private DanmakufuModels(String tex, float scale) {
        _model = new Model("assets/GLSL/vertex/dan.shader",
                "assets/GLSL/fragment/dan.shader", "assets/images/" + tex,
                scale);
    }

    public Model getModel() {
        return _model;
    }
}
