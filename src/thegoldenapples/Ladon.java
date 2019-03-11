package thegoldenapples;

import java.util.Random;
import javafx.scene.image.Image;
/**
 *
 * @author Pantelis Chintiadis
 */
public class Ladon extends Actor{
    protected static final Random randomNum = new Random();
    int attackCounter = 0;
    int attackFrequency = 250;
    int attackBoundary = 300;
    boolean takeSides = false;
    boolean onScreen = false;
    boolean callAttack = false;
    boolean shootBullet = false;
    int spriteMoveR, spriteMoveL, destination, randomLocation, bulletRange, bulletOffset;
    double randomOffset;
    double electricBallGravity = 0.2;
    double goldenAppleGravity = 0.1;
    int pauseCounter = 0;
    boolean launchIt = false;
    boolean bulletType = false;
    int oArchelaosLocation;
    TheGoldenApples thegoldenApples;
    public Ladon(TheGoldenApples oArchelaos,String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        thegoldenApples = oArchelaos;
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
        isAlive = true;
        isBonus = true;
        hasValu = true;
    }
    @Override
    public void update() {
        if(!callAttack) {
            if(attackCounter >= attackFrequency) {
                attackCounter=0;
                spriteMoveR = 1300;
                spriteMoveL = -70;
                randomLocation = randomNum.nextInt(attackBoundary);
                oArchelaosLocation = (int) thegoldenApples.oArchelaos.getiY();
                bulletType = randomNum.nextBoolean();
                if(bulletType){
                    spriteFrame.setTranslateY(oArchelaosLocation);
                    randomOffset = oArchelaosLocation + 5;
                } else {
                    spriteFrame.setTranslateY(oArchelaosLocation);
                    randomOffset = oArchelaosLocation + 5;
                }
                takeSides = randomNum.nextBoolean();
                callAttack = true;
            } else {
                attackCounter+=1; 
            }
        } 
        else { 
            initiateAttack(); 
        }
        if (shootBullet){
            shootProjectile();
            if(pauseCounter >= 60) {
                launchIt = true;
                pauseCounter = 0;
            } else { 
                pauseCounter++; 
            }
        }
    }
    private void initiateAttack(){
        if(!takeSides) {
            spriteFrame.setScaleX(1);
            this.setIsFlipH(false);
            if(!onScreen) {
                destination = 1000;
                if(spriteMoveR >= destination) {
                    spriteMoveR-=4;
                    spriteFrame.setTranslateX(spriteMoveR);
                } else {
                    bulletOffset = 970;
                    shootBullet = true;
                    onScreen = true;
                }
            }
            if(onScreen && launchIt) {
                destination = 1300;
                if(spriteMoveR <= destination) {
                    spriteMoveR+=2;
                    spriteFrame.setTranslateX(spriteMoveR);
                } else {
                    onScreen=false;
                    callAttack=false;
                    launchIt = false;
                    loadBullet();
                    loadGoldenApple();
                    loadEnemy();
                    attackFrequency = 60 + randomNum.nextInt(480);
                }
            }
        }
        if(takeSides) {
            spriteFrame.setScaleX(-1);
            this.setIsFlipH(true);
            if(!onScreen) {
                destination = 100; //οριο κινησης δρακου στα δεξια
                if(spriteMoveL <= destination) {
                    spriteMoveL+=4;//ταχυτητα δρακου απο την αριστερη προς την δεξια πλευρα
                    spriteFrame.setTranslateX(spriteMoveL);
                } else {
                    bulletOffset = 120;
                    shootBullet = true;
                    onScreen=true;
                }
            }
            if(onScreen && launchIt) {
                destination = -370; 
                if(spriteMoveL >= destination) {
                    spriteMoveL-=2;
                    spriteFrame.setTranslateX(spriteMoveL);
                } else {
                    onScreen = false;
                    callAttack = false;
                    launchIt = false;
                    loadBullet();
                    loadGoldenApple();
                    loadEnemy();
                    attackFrequency = 60 + randomNum.nextInt(480);
                }
            }
        }
    }
    private void shootProjectile() {
        if(!bulletType && !takeSides) {
            thegoldenApples.electricBall.spriteFrame.setTranslateY(randomOffset);
            thegoldenApples.electricBall.spriteFrame.setScaleX(-1);
            thegoldenApples.electricBall.spriteFrame.setScaleY(1); //μεγεθος ηλεκτρικης μπαλας
            bulletRange = -350;// η ηλεκτρικες μπαλες φευγουν εκτος οθονης απο τα αριστερα
            if(bulletOffset >= bulletRange) {
                bulletOffset-=16; //ταχυτητα ηλεκτρικης μπαλας
                thegoldenApples.electricBall.spriteFrame.setTranslateX(bulletOffset);
                randomOffset = randomOffset + electricBallGravity;
            } else { 
                shootBullet = false; 
            }
        }
        if(!bulletType && takeSides) {
            thegoldenApples.electricBall.spriteFrame.setTranslateY(randomOffset);
            thegoldenApples.electricBall.spriteFrame.setScaleX(1);
            thegoldenApples.electricBall.spriteFrame.setScaleY(1);
            bulletRange = 1324;
            if(bulletOffset <= bulletRange) {
                bulletOffset+=16;
                thegoldenApples.electricBall.spriteFrame.setTranslateX(bulletOffset);
                randomOffset = randomOffset + electricBallGravity;
            } else { 
                shootBullet = false; 
            }
        }
        if(bulletType && !takeSides) {
            thegoldenApples.goldenApple.spriteFrame.setTranslateY(randomOffset);
            thegoldenApples.goldenApple.spriteFrame.setScaleX(-1);
            thegoldenApples.goldenApple.spriteFrame.setScaleY(1);
            bulletRange = -350;
            if(bulletOffset >= bulletRange) {
                bulletOffset-=14;
                thegoldenApples.goldenApple.spriteFrame.setTranslateX(bulletOffset);
                randomOffset = randomOffset + goldenAppleGravity;
            } else { 
                shootBullet = false; 
            }
        }
        if(bulletType && takeSides) {
            thegoldenApples.goldenApple.spriteFrame.setTranslateY(randomOffset);
            thegoldenApples.goldenApple.spriteFrame.setScaleX(1);
            thegoldenApples.goldenApple.spriteFrame.setScaleY(1);
            bulletRange = 1324;
            if(bulletOffset <= bulletRange) {
                bulletOffset+=14;
                thegoldenApples.goldenApple.spriteFrame.setTranslateX(bulletOffset);
                randomOffset = randomOffset + goldenAppleGravity;
            } else { 
                shootBullet = false; 
            }
        }
    }
    private void loadBullet() {
        for (int i=0; i<thegoldenApples.castDirector.getCurrentCast().size(); i++) {
            Actor object = thegoldenApples.castDirector.getCurrentCast().get(i);
            if(object.equals(thegoldenApples.electricBall)) { return; }
        }
       thegoldenApples.castDirector.addCurrentCast(thegoldenApples.electricBall);
        thegoldenApples.root.getChildren().add(thegoldenApples.electricBall.spriteFrame);
    }
    private void loadGoldenApple() {
        for (int i=0; i<thegoldenApples.castDirector.getCurrentCast().size(); i++) {
            Actor object = thegoldenApples.castDirector.getCurrentCast().get(i);
            if(object.equals(thegoldenApples.goldenApple)) { return;}
        }
        thegoldenApples.castDirector.addCurrentCast(thegoldenApples.goldenApple);
        thegoldenApples.root.getChildren().add(thegoldenApples.goldenApple.spriteFrame);
    }
    private void loadEnemy() {
        for (int i=0; i<thegoldenApples.castDirector.getCurrentCast().size(); i++) {
            Actor object = thegoldenApples.castDirector.getCurrentCast().get(i);
            if(object.equals(thegoldenApples.ladonas)) { return;}
        }
        thegoldenApples.castDirector.addCurrentCast(thegoldenApples.ladonas);
        thegoldenApples.root.getChildren().add(thegoldenApples.ladonas.spriteFrame);
    }
}