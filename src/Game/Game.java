package Game;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class Game {
    private GraphicsContext gc;
    private Status status;
    private Group root;
    private Image backgroundImg, yourTurn, rockImg, paperImg, scissorImg, circle, yourPoints, opponentPoints;
    private static double w = 1500, h = 900;
    private MenuItem rock, paper, scissor;
    private int choice, points, clientPoints;
    private boolean buttonsOn;


    public Game(GraphicsContext gc, Status status, Group root) {
        this.gc = gc;
        this.status = status;
        this.root = root;
        this.choice = 0;
        this.points = 0;
        this.clientPoints = 0;
        images();
        this.rock = new MenuItem(rockImg, 200,270,gc,root);     // x1= 200 x2= 200 + 300
        rock.removeFromView(root);
        this.paper = new MenuItem(paperImg, 600,270,gc,root);   // x1= 600 x2= 600 + 300
        paper.removeFromView(root);
        this.scissor = new MenuItem(scissorImg, 1000,270,gc,root);   // x1= 1000 x2= 1000 + 300
        scissor.removeFromView(root);
        this.buttonsOn = false;
    }


    private void images() {
        backgroundImg = new Image("/Img/Background.png");
        yourTurn = new Image("/Resources/yourTurnImg.png");
        rockImg = new Image("/Img/pedra.png");
        paperImg = new Image("/Img/papel.png");
        scissorImg = new Image("/Img/tesoura.png");
        circle = new Image("/Resources/circle.png");
        yourPoints = new Image("/Resources/yourPoints.png");
        opponentPoints = new Image("/Resources/opponentPoints.png");
    }

    public void drawing(KeyEvent key, Group root, TCPServer server, RoundResult roundResult){
        gc.drawImage(backgroundImg, 0,0,w,h);
        gc.drawImage(yourTurn, 330, 20, 800, 200);

        if(!buttonsOn) {
            rock.addToView(root);
            paper.addToView(root);
            scissor.addToView(root);
            setButtonsOn(true);
        }
        gc.drawImage(yourPoints, 0, 700, 250, 100);
        gc.drawImage(opponentPoints, 1250, 700, 250, 100);

        // [Adiciona pontos do servidor]
        this.setPoints(roundResult.getPoints());
        if(points != 0){
            if(points == 1){
                gc.drawImage(circle, 50,770,50,50);
            } else if(points == 2){
                gc.drawImage(circle, 50,770,50,50);
                gc.drawImage(circle, 110,770,50,50);
            } else if(points == 3){
                gc.drawImage(circle, 50,770,50,50);
                gc.drawImage(circle, 110,770,50,50);
                gc.drawImage(circle, 170,770,50,50);
            }

        }

        // [Adiciona pontosdo cliente]
        this.setClientPoints(roundResult.getClientPoints());
        if(clientPoints != 0){
            if(clientPoints == 1){
                gc.drawImage(circle, 1300,770,50,50);
            } else if(clientPoints == 2){
                gc.drawImage(circle, 1300,770,50,50);
                gc.drawImage(circle, 1360,770,50,50);
            } else if(clientPoints == 3){
                gc.drawImage(circle, 1300,770,50,50);
                gc.drawImage(circle, 1360,770,50,50);
                gc.drawImage(circle, 1420,770,50,50);
            }
        }


        rock.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // [Remove carta apos ser clicada]
                root.getChildren().remove(1, root.getChildren().size());
                setChoice(1);
                server.setJogada(1);
                try {
                    server.getOutObject().writeInt(choice);
                    server.getOutObject().flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Main.setStatus(Status.WAITINGOPPONENT);
                // [Libera o conta ponto na tela de RoundResult]
                roundResult.setContaPonto(true);
            }
        });

        paper.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // [Remove carta apos ser clicada]
                root.getChildren().remove(1, root.getChildren().size());
                setChoice(2);
                server.setJogada(2);
                try {
                    server.getOutObject().writeInt(choice);
                    server.getOutObject().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Main.setStatus(Status.WAITINGOPPONENT);
                // [Libera o conta ponto na tela de RoundResult]
                roundResult.setContaPonto(true);

            }
        });

        scissor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // [Remove carta apos ser clicada]
                root.getChildren().remove(1, root.getChildren().size());
                setChoice(3);
                server.setJogada(3);
                try {
                    server.getOutObject().writeInt(choice);
                    server.getOutObject().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Main.setStatus(Status.WAITINGOPPONENT);
                // [Libera o conta ponto na tela de RoundResult]
                roundResult.setContaPonto(true);
            }
        });

    }


    public void setChoice(int choice) {
        this.choice = choice;
    }

    public void setButtonsOn(boolean buttonsOn) {
        this.buttonsOn = buttonsOn;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setClientPoints(int clientPoints) {
        this.clientPoints = clientPoints;
    }
}
