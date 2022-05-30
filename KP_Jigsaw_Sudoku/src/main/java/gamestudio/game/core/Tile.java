package gamestudio.game.core;

public class Tile {
    private int value;
    private boolean state;
    private Color ColorDefault;
    private Color ColorUsable;
    public Tile(){
        this.value = 0;
        this.state = false;
        this.ColorDefault = Color.NONE;
        this.ColorUsable = Color.NONE;
    }


    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public boolean getState(){
        return state;
    }

    public void setState(boolean state){
        this.state = state;
    }

    public Color getColorDefault(){
        return ColorDefault;
    }

    public void setColorDefault(Color ColorDefault){
        this.ColorDefault = ColorDefault;
    }

    public void setColorUsable(Color ColorUsable){
        this.ColorUsable = ColorUsable;
    }

    public Color getColorUsable(){
        return ColorUsable;
    }

}
