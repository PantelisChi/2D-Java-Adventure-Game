package thegoldenapples;

import javafx.scene.image.Image;
/**
 *
 * @author Pantelis Chintiadis
 */
public class PropH extends Actor {
    
    public PropH(String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        this.setIsFlipH(true);
        spriteFrame.setScaleX(-1);
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
    }
    
    @Override
    public void update() {

    }
    
}
