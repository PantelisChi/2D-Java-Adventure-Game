package thegoldenapples;
import static thegoldenapples.TheGoldenApples.WIDTH;
import static thegoldenapples.TheGoldenApples.HEIGHT;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
/**
 *
 * @author Pantelis Chintiadis
 */
public class Archelaos extends Hero {
    protected TheGoldenApples thegoldenApples; //το κανω protected σε περιπτωση που κανω μια υποκλαση στην κλαση Archelaos ετσι ωστε να παρει και αυτοι τις παραμετρους αν χρειαστει
    protected static final double SPRITE_PIXELS_X = 98;
    protected static final double SPRITE_PIXELS_Y = 131;
    protected static final double rightBoundary = WIDTH - SPRITE_PIXELS_X;
    protected static final double leftBoundary = 0;
    protected static final double bottomBoundary = HEIGHT - SPRITE_PIXELS_Y;
    protected static final double topBoundary = 0;
    boolean animator = false;
    int framecounter = 0;
    int runningspeed = 6;
    public Archelaos(TheGoldenApples oArchelaos,String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        thegoldenApples = oArchelaos;
    }
    @Override
    public void update() {
        setXYLocation();
        setBoundaries();
        setImageState();
        moveArchelaos(iX,iY);
        //playAudioClip();
        checkCollision();
    }
    private void setXYLocation(){
        if(thegoldenApples.isRight()) {iX+=vX;} //Υπολογισμος μετακινησης ηρωα στις xy συντεταγμενες
        if(thegoldenApples.isLeft()) {iX-=vX;}
        if(thegoldenApples.isDown()) {iY+=vY;}
        if(thegoldenApples.isUp()) {iY-=vY;}
    }
    private void setBoundaries() {
        if (iX >= rightBoundary) { iX=rightBoundary; }
        if (iX <= leftBoundary) { iX=leftBoundary; }
        if (iY >= bottomBoundary) { iY=bottomBoundary; }
        if (iY <= topBoundary) { iY=topBoundary; }
    }
    private void setImageState() {
        if( !thegoldenApples.isRight() && !thegoldenApples.isLeft() && !thegoldenApples.isDown() && !thegoldenApples.isUp() ) {
            spriteFrame.setImage(imageStates.get(0));
            animator=false;
            framecounter=0;
        }
        if(thegoldenApples.isRight()) {
            spriteFrame.setScaleX(1);
            this.setIsFlipH(false);
            if(!animator && ( !thegoldenApples.isDown() && !thegoldenApples.isUp() )) {
                spriteFrame.setImage(imageStates.get(1));
                if(framecounter >= runningspeed) {
                    animator = true;
                    framecounter = 0;
                } else { framecounter+=1; }
            } else if(animator) {
                spriteFrame.setImage(imageStates.get(2));
                if(framecounter >= runningspeed) {  
                    animator = false;
                    framecounter = 0;
                } else { framecounter+=1; }
            }
        }
        if(thegoldenApples.isLeft()) { 
            spriteFrame.setScaleX(-1);
            this.setIsFlipH(true);
            if(!animator && ( !thegoldenApples.isDown() && !thegoldenApples.isUp() )) {
                spriteFrame.setImage(imageStates.get(1));
                if(framecounter >= runningspeed) {
                    animator=true;
                    framecounter = 0;
                } else { framecounter+=1; }
            } else if(animator) {
                spriteFrame.setImage(imageStates.get(2));
                if(framecounter >= runningspeed) {
                    animator=false;
                    framecounter = 0;
                } else { framecounter+=1; }
            }
        }
        if(thegoldenApples.isDown()) { 
            spriteFrame.setImage(imageStates.get(6)); 
        }
        if(thegoldenApples.isUp()) { 
            spriteFrame.setImage(imageStates.get(4)); 
        }
        if(thegoldenApples.iswKey()) {
            spriteFrame.setImage(imageStates.get(5));
        }
        if(thegoldenApples.issKey()) {
            spriteFrame.setImage(imageStates.get(8));
        }
    }
    private void moveArchelaos(double x, double y) {
        spriteFrame.setTranslateX(iX);
        spriteFrame.setTranslateY(iY);
    }
    private void playAudioClip() {
        if(thegoldenApples.isLeft()) { thegoldenApples.playaSound1(); }
        if(thegoldenApples.isRight()) { thegoldenApples.playaSound1(); }
        if(thegoldenApples.isUp()) { thegoldenApples.playaSound1(); }
        if(thegoldenApples.isDown()) { thegoldenApples.playaSound2(); }
        if(thegoldenApples.iswKey()) { thegoldenApples.playaSound3(); }
        if(thegoldenApples.issKey()) { thegoldenApples.playaSound4(); }
    }
    public void checkCollision() {
        for(int i=0; i<thegoldenApples.castDirector.getCurrentCast().size(); i++) {
            Actor object = thegoldenApples.castDirector.getCurrentCast().get(i);
            if(collide(object)) {
                thegoldenApples.castDirector.addToRemovedActors(object);
                thegoldenApples.root.getChildren().remove(object.getSpriteFrame());
                thegoldenApples.castDirector.resetRemovedActors();
                scoringEngine(object);
            }
        }
    }
    private void scoringEngine(Actor object) {
        if(object instanceof Prop) { 
            thegoldenApples.gameScore-=1;
            thegoldenApples.playaSound2();
        } else if(object instanceof PropV) { 
            thegoldenApples.gameScore-=2;
            thegoldenApples.playaSound2();
        } else if(object instanceof PropH) { 
            thegoldenApples.gameScore-=1;
            thegoldenApples.playaSound2();
        } else if(object instanceof PropB) { 
            thegoldenApples.gameScore-=2;
            thegoldenApples.playaSound2(); 
        } else if (object instanceof Treasure) {
            thegoldenApples.gameScore+=5;
            thegoldenApples.playaSound1();
        } else if (object.equals(thegoldenApples.electricBall)) {
            thegoldenApples.gameScore-=5;
            thegoldenApples.playaSound0();
        } else if (object.equals(thegoldenApples.goldenApple)) {
            thegoldenApples.gameScore+=5;
            thegoldenApples.playaSound4();
        } else if (object.equals(thegoldenApples.ladonas)) {
            thegoldenApples.gameScore+=5;
            thegoldenApples.playaSound3();
        }
        thegoldenApples.scoreText.setText(String.valueOf(thegoldenApples.gameScore));
    }
    @Override
    public boolean collide(Actor object) {
        boolean collisionDetect = false;
        if ( thegoldenApples.oArchelaos.spriteFrame.getBoundsInParent().intersects( object.getSpriteFrame().getBoundsInParent() ) ) {
            Shape intersection = SVGPath.intersect(thegoldenApples.oArchelaos.getSpriteBound(), object.getSpriteBound());
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                return true;
            }
        }
    return false;
    }
}
