package Game;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class WaitToPlay {
    private GraphicsContext gc;
    private Status status;
    private Group root;
    private Image backgroundImg,searchingPlayerImg;
    private static double w = 1500, h = 900;

    // [Construtor]
    public WaitToPlay(GraphicsContext gc, Status status, Group root) throws IOException {
        this.gc = gc;
        this.status = status;
        this.root = root;
        images();
    }

    // [Carrega imagens]
    private void images() {
        backgroundImg = new Image("/Img/Background.png");
        searchingPlayerImg = new Image("/Img/esperando.png");
    }

    // [Desenha tela WaitToPlay]
    public void drawing(KeyEvent key, Group root ){
        gc.drawImage(backgroundImg, 0,0,w,h);
        gc.drawImage(searchingPlayerImg, 330, 20, 800, 200);
    }
}
