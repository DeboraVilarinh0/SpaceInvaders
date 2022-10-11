package spaceInvanders;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {

    protected Position position;


    Element (int x, int y){
        position = new Position(x,y);
    }
    public void draw(TextGraphics graphics, String colorCode, String character){

        graphics.setForegroundColor(TextColor.Factory.fromString(colorCode));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), character);
    }



    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition () {return position;}


    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());

    }

    public Position moveLeft () {
        return new Position(position.getX() - 1, position.getY());

    }
    }




