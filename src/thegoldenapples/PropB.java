package thegoldenapples;

import javafx.scene.image.Image;
/**
 *
 * @author Pantelis Chintiadis
 */
public class PropB extends Actor {
    
    public PropB(String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        this.setIsFlipH(true);
        spriteFrame.setScaleX(-1);
        this.setIsFlipV(true);
        spriteFrame.setScaleY(-1);
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
    }
    
    @Override
    public void update() {

    }
    
}