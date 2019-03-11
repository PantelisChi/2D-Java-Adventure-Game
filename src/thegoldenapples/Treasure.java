package thegoldenapples;

import javafx.scene.image.Image;
/**
 *
 * @author Pantelis Chintiadis
 */
public class Treasure extends Actor{
    public Treasure(String SVGdata, double xLocation, double yLocation, Image...spriteCels){
        super(SVGdata, xLocation, yLocation, spriteCels);
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
        hasValu = true;
        isBonus = true;
    }
    @Override
    public void update(){}
}