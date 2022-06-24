public class Coord {
    private int x;
    private int y;

    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x){
        if(x < 0) return;
        this.x = x;
    }

    public int getX(){
        return this.x;
    }

    public void setY(int y){
        if(y < 0) return;
        this.y = y;
    }

    public int getY(){
        return this.y;
    }

    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(other instanceof Coord){
            Coord that = (Coord)other;
            return this.x == that.x && this.y == that.y; 
        }
        return false;
    }

    @Override
    public String toString(){
        return "(Row: " + this.x + ",Column: " + this.y + ")";
    }
}
