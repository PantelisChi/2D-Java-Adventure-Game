package thegoldenapples;

import javafx.scene.image.Image;
/**
 *
 * @author Pantelis Chintiadis
 */
public class PropV extends Actor {
    
    public PropV(String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        this.setIsFlipV(true);
        spriteFrame.setScaleY(-1);
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
    }
    
    @Override
    public void update() {

    }
    
}