package de.hawh.beta3.application.game.view.Player;

import javafx.scene.CacheHint;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
   private Coordinate pos;
    private String orientation;

    private List<Coordinate> trail;

    private ImageView imageView;

    public Player(int id, Coordinate pos, String orientation){
      this.id = id;
      this.pos = pos;
      trail=new ArrayList<>();
      trail.add(pos);
      this.orientation = "RIGHT";
      //TODO change to relative path
        // TODO bike image depends on ID
      this.imageView = new ImageView("C:\\Users\\vivia\\IdeaProjects\\gameoftrons\\src\\main\\resources\\images\\bike"
              + "_" + this.id +".png");

      rotateImage(orientation);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setPos(int x, int y) {
        this.pos.x=x;
        this.pos.y=y;
    }

    public Coordinate getPos() {
        return pos;
    }


    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public List<Coordinate> getTrail() {
        return trail;
    }

    public void updateTrailAndOrientation(int newX, int newY, String newOrientation){
       int difX = newX-pos.x;
       int difY = newY-pos.y;



        if(orientation.equals("UP")||orientation.equals("DOWN")){
            if(difY!=0) {
                for (int i = 1; i <= Math.abs(difY); i++) {
                    trail.add(new Coordinate(pos.x, pos.y+ ( 1 * (Math.abs(difY) / difY ) ) ) );
                    pos=trail.get(trail.size()-1);
                }
            }

            if(difX!=0){
                for (int i = 1; i <= Math.abs(difX); i++) {
                    trail.add(new Coordinate(pos.x+ ( 1 * (Math.abs(difX) / difX ) ), pos.y));
                    pos=trail.get(trail.size()-1);
                }
            }


       } else if(orientation.equals("LEFT") || orientation.equals("RIGHT")){
            if(difX!=0){
                for (int i = 1; i <= Math.abs(difX); i++) {
                    trail.add(new Coordinate(pos.x+ ( 1 * (Math.abs(difX) / difX ) ), pos.y));
                    pos=trail.get(trail.size()-1);
                }
            }
            if(difY!=0) {
                for (int i = 1; i <= Math.abs(difY); i++) {
                    trail.add(new Coordinate(pos.x, pos.y+ ( 1 * (Math.abs(difY) / difY ) ) ) );
                    pos=trail.get(trail.size()-1);
                }
            }
        }
        if(!newOrientation.equals(orientation)) {
            rotateImage(newOrientation);
        }
    }

    private void rotateImage(String newOrientation) {
        if(newOrientation.equals("LEFT")|| orientation.equals("LEFT")) {
            Translate flipTranslation = new Translate(0, imageView.getImage().getHeight());
            Rotate flipRotation = new Rotate(180, Rotate.X_AXIS);
            imageView.getTransforms().addAll(flipTranslation, flipRotation);
        }


        switch(orientation){
            case "RIGHT":
                switch(newOrientation){
                    case "LEFT":
                        imageView.setRotate(180);
                        break;
                    case "UP":
                        imageView.setRotate(270);
                        break;
                    case "DOWN":
                        imageView.setRotate(90);
                        break;
                }
                break;
            case "LEFT":
                switch(newOrientation){
                    case "RIGHT":
                        imageView.setRotate(180);
                        break;
                    case "UP":
                        imageView.setRotate(90);
                        break;
                    case "DOWN":
                        imageView.setRotate(270);
                        break;
                }
                break;
            case "UP":
                switch(newOrientation){
                    case "RIGHT":
                        imageView.setRotate(90);
                        break;
                    case "LEFT":
                        imageView.setRotate(270);
                        break;
                    case "DOWN":
                        imageView.setRotate(180);
                        break;
                }
                break;
            case "DOWN":
                switch(newOrientation){
                    case "RIGHT":
                        imageView.setRotate(270);
                        break;
                    case "UP":
                        imageView.setRotate(180);
                        break;
                    case "LEFT":
                        imageView.setRotate(90);
                        break;
                }
                break;
        }

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        imageView.setImage(imageView.snapshot(params, null));
        orientation = newOrientation;

    }

    public Image getImage() {
        return imageView.getImage();
    }

}
