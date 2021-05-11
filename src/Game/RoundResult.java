package Game;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RoundResult {
    private GraphicsContext gc;
    private Status status;
    private Group root;
    private Image backgroundImg, title, rockImg, paperImg, scissorImg, result, youLose, youWin, deadlockMessage, nextButtonImg;
    private static double w = 1500, h = 900;
    private MenuItem nextButton;
    private int choice, points, clientPoints;
    private boolean buttonsOn, contaPonto;

    // [Construtor]
    public RoundResult(GraphicsContext gc, Status status, Group root) {
        this.gc = gc;
        this.status = status;
        this.root = root;
        images();
        this.nextButton = new MenuItem(nextButtonImg, 500,650,gc,root);
        nextButton.removeFromView(root);
        this.buttonsOn = false;
        this.points = 0;
        this.clientPoints = 0;
        this.contaPonto = true;
    }

    // [Carrega imagens]
    private void images() {
        backgroundImg = new Image("/Img/Background.png");
        rockImg = new Image("/Img/pedra.png");
        paperImg = new Image("/Img/papel.png");
        scissorImg = new Image("/Img/tesoura.png");
        youLose = new Image("/Img/Perdeu.png");
        youWin = new Image("/Img/Venceu.png");
        deadlockMessage = new Image("/Img/Empate.png");
        nextButtonImg = new Image("/Img/proximo.png");
    }

    // [Desenha tela RoundResult]
    public void drawing(KeyEvent key, Group root, TCPServer server, Game gameScreen, GameOver gameOver) {
        // [Calcula resultado por partida]
        if (server.getJogada() == server.getJogadaCliente()) {
            title = deadlockMessage;
        } else if (server.getJogada() == 1 && server.getJogadaCliente() == 2) {
            title = youLose;
        } else if (server.getJogada() == 1 && server.getJogadaCliente() == 3) {
            title = youWin;
        } else if (server.getJogada() == 2 && server.getJogadaCliente() == 1) {
            title = youWin;
        } else if (server.getJogada() == 2 && server.getJogadaCliente() == 3) {
            title = youLose;
        } else if (server.getJogada() == 3 && server.getJogadaCliente() == 1) {
            title = youLose;
        } else if (server.getJogada() == 3 && server.getJogadaCliente() == 2) {
            title = youWin;
        }

        if(title == youWin && contaPonto){
            points++;
            contaPonto = false;
        } else if(title == youLose && contaPonto){
            clientPoints++;
            contaPonto = false;
        }

        // [Verifica ganhador]
        if(points == 3 || clientPoints == 3){
            if(points == 3){
                gameOver.setGanhou(true);
            }
            Main.setStatus(Status.GAMEOVER);
            // [Remove botao de nextRound]
            nextButton.removeFromView(root);
            // [Reinicia status do botao para futuras partidas]
            setButtonsOn(true);
        }

        // [Exibe background]
        gc.drawImage(backgroundImg, 0, 0, w, h);

        // [Exibe carta jogada pelo servidor]
        if(server.getJogada() == 1){
            gc.drawImage(rockImg, 250, 270);
        } else if(server.getJogada() == 2){
            gc.drawImage(paperImg, 250, 270);
        }else if(server.getJogada() == 3){
            gc.drawImage(scissorImg, 250, 270);
        }

        // [Exibe carta jogada pelo cliente]
        if(server.getJogadaCliente() == 1){
            gc.drawImage(rockImg, 920, 270);
        } else if(server.getJogadaCliente() == 2){
            gc.drawImage(paperImg, 920, 270);
        }else if(server.getJogadaCliente() == 3){
            gc.drawImage(scissorImg, 920, 270);
        }

        gc.drawImage(title, 330, 20, 800, 200);
        gc.drawImage(result, 650, 300);

        // [Adiciona botao de nextRound]
        if(!buttonsOn) {
            nextButton.addToView(root);
            setButtonsOn(true);
        }

        nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // [Remove botao]
                root.getChildren().remove(1, root.getChildren().size());
                // [Vai para a tela de jogo novamente]
                Main.setStatus(Status.GAME);
                gameScreen.setButtonsOn(false);
                // [Reinicio o valor da jogada do cliente e servidor]
                server.setJogadaCliente(0);
                server.setJogada(0);
            }
        });
    }

    // [Getters e setters]
    public void setButtonsOn(boolean buttonsOn) {
        this.buttonsOn = buttonsOn;
    }

    public int getPoints() {
        return points;
    }

    public int getClientPoints() {
        return clientPoints;
    }

    public void setContaPonto(boolean contaPonto) {
        this.contaPonto = contaPonto;
    }
}
