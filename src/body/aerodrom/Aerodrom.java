package body.aerodrom;

import Exceptions.InvalidCode;
import Exceptions.InvalidName;
import Exceptions.InvalidXCoordinate;
import Exceptions.InvalidYCoordinate;

public class Aerodrom {
    private String name;
    private String code; // three letter
    private int x;
    private int y;

    public Aerodrom(String name, String code, int x, int y) throws InvalidName, InvalidCode, InvalidXCoordinate, InvalidYCoordinate {
        setName(name);
        setCode(code);
        setX(x);
        setY(y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidName {
        if(name == null || name.isBlank()) {
            throw new InvalidName();
        }
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws InvalidCode {
        if(!code.matches("^[A-Z]{3}$")) throw new InvalidCode(code);
        this.code = code;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) throws InvalidXCoordinate {
        String xVal = String.valueOf(x);
        if(xVal.isBlank()) throw new InvalidXCoordinate();
        if(x <= -90 || x >= 90) throw new InvalidXCoordinate();
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) throws InvalidYCoordinate {
        String yVal = String.valueOf(y);
        if(yVal.isBlank()) throw new InvalidYCoordinate();
        if(y <= -90 || y >= 90) throw new InvalidYCoordinate();
        this.y = y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append(" | ");
        sb.append("Code: ").append(code).append(" | ");
        sb.append("Coords: (").append(x);
        sb.append(", ").append(y);
        sb.append(")\n");
        return sb.toString();
    }
}