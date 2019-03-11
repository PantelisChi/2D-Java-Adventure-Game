package thegoldenapples;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
/**
 *
 * @author Pantelis Chintiadis
 */
public class GamePlayLoop extends AnimationTimer { //συνδεση με τον ηρωα για αναγνωριση των κινησεων του
    protected TheGoldenApples thegoldenApples;
    public GamePlayLoop(TheGoldenApples oArchelaos) {
        thegoldenApples = oArchelaos;
    }
    @Override
    public void handle(long now) {
        thegoldenApples.oArchelaos.update();
        thegoldenApples.ladonas.update();
    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void stop(){
        super.stop();
    }
}
