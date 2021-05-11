package Game;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class WaitingOpponent {
    private GraphicsContext gc;
    private Status status;
    private Group root;
    private Image backgroundImg, waitingOpponent, rockImg, paperImg, scissorImg;
    private static double w = 1500, h = 900;

    // [Construtor]
    public WaitingOpponent(GraphicsContext gc, Status status, Group root) {
        this.gc = gc;
        this.status = status;
        this.root = root;
        images();
    }

    // [Carrega imagens]
    private void images() {
        backgroundImg = new Image("/Img/Background.png");
        waitingOpponent = new Image("/Img/esperando.png");
        rockImg = new Image("/Img/pedra.png");
        paperImg = new Image("/Img/papel.png");
        scissorImg = new Image("/Img/tesoura.png");
    }

    // [Desenha tela WaitingOpponent]
    public void drawing(KeyEvent key, Group root, TCPServer server, RoundResult roundResult) {
        gc.drawImage(backgroundImg, 0, 0, w, h);
        gc.drawImage(waitingOpponent, 330, 20, 800, 200);
        if(server.getJogada() == 1){
            gc.drawImage(rockImg, 250, 270);
        } else if(server.getJogada() == 2){
            gc.drawImage(paperImg, 250, 270);
        }else if(server.getJogada() == 3){
            gc.drawImage(scissorImg, 250, 270);
        }

        if(server.getJogada() > 0 && server.getJogadaCliente() > 0){
            Main.setStatus(Status.ROUNDRESULT);
            roundResult.setButtonsOn(false);
        }
    }
}
