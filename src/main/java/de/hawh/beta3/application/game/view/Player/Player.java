package de.hawh.beta3.application.game.view.Player;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
   private Coordinate pos;
    private String orientation;

    private List<Coordinate> trail;

    private Image image;

    public Player(int id){
      this.id = id;
      pos = new Coordinate(1,1);
      trail=new ArrayList<>();
      trail.add(pos);
      orientation = "RIGHT";
      //TODO change to relative path
        //TODO bike image depends on ID
      this.image = new Image("C:\\Users\\vivia\\IdeaProjects\\gameoftrons\\src\\main\\resources\\images\\bike.png");
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
                    trail.add(new Coordinate(pos.x, pos.y+= ( 1 * (Math.abs(difY) / difY ) ) ) );
                }
            }

            if(difX!=0){
                for (int i = 1; i <= Math.abs(difX); i++) {
                    trail.add(new Coordinate(pos.x+= ( 1 * (Math.abs(difX) / difX ) ), pos.y));
                }
            }


       } else if(orientation.equals("LEFT") || orientation.equals("RIGHT")){
            if(difX!=0){
                for (int i = 1; i <= Math.abs(difX); i++) {
                    trail.add(new Coordinate(pos.x+= ( 1 * (Math.abs(difX) / difX ) ), pos.y));
                }
            }
            if(difY!=0) {
                for (int i = 1; i <= Math.abs(difY); i++) {
                    trail.add(new Coordinate(pos.x, pos.y+= ( 1 * (Math.abs(difY) / difY ) ) ) );
                }
            }
        }
        orientation=newOrientation;
        rotateImage();
    }

    private void rotateImage() {
        ImageView iv = new ImageView(image);
        switch(orientation){
            case "RIGHT":
                iv.setRotate(0);
                break;
            case "LEFT":
                iv.setRotate(180);
                break;
            case "UP":
                iv.setRotate(270);
                break;
            case "DOWN":
                iv.setRotate(90);
                break;
        }
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        image = iv.snapshot(params, null);
    }

    public Image getImage() {
        return image;
    }
}
