public class Block {
    private String color;
    private Coord coord;

    public Block(String color, Coord coord){
        this.color = color;
        this.coord = coord;
    }

    public String getColor(){
        return this.color;
    }

    public Coord getCoord(){
        return this.coord;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setCoord(Coord coord){
        this.coord = coord;
    }

    @Override
    public boolean equals(Object other){
        if(this == other) return true;
        if(other instanceof Block){
            Block that = (Block)other;
            return this.color.equals(that.color) && this.coord.equals(that.coord);
        }
        return false;
    }

    @Override
    public String toString(){
        return "Coord: " + coord.toString() + ", Color: " + color;
    }

}
