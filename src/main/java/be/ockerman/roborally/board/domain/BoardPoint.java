package be.ockerman.roborally.board.domain;

public class BoardPoint {
    
    private int x; 
    private int y; 
    
    public BoardPoint() {
        x=0;
        y=0;
    }
    
    public BoardPoint(int x, int y) {
        this.x = x;
        this.y = y; 
    }
    
    public int getX() { 
        return x; 
        
    }
    
    public int getY() {
        return y; 
    }
    
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BoardPoint other = (BoardPoint) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    
    

}
