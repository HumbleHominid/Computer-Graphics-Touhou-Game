import java.util.HashSet;
import java.awt.event.KeyEvent;

public class PlayerInputComponent {
    public void processInput(Player player, HashSet<Integer> pressed) {
        float velScale = pressed.contains(KeyEvent.VK_ALT) ? 0.25f : 1.0f;

        for (Integer c : pressed) {
            switch (c) {
                case KeyEvent.VK_W:
                    player._y = player._y + (player._yVel * velScale);
                    break;
                case KeyEvent.VK_A:
                    player._x = player._x - (player._xVel * velScale);
                    break;
                case KeyEvent.VK_S:
                    player._y = player._y - (player._yVel * velScale);
                    break;
                case KeyEvent.VK_D:
                    player._x = player._x + (player._xVel * velScale);
                    break;
            }
        }
    }
}
