public class ParticleSpawner {
    private Particle _prototype;

    public ParticleSpawner(Particle prototype) {
        _prototype = prototype;
    }

    public Particle spawnParticle() {
        return _prototype.cloneParticle();
    }
}
