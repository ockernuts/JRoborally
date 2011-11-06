package be.ockerman.roborally.board.domain;

import java.util.HashMap;

public class Board {
    private int sizex; 
    private int sizey;
    private HashMap<BoardPoint, Tile> tiles = new HashMap<BoardPoint, Tile>();

    public void initSize(int sizex, int sizey) {
        this.sizex = sizex;
        this.sizey = sizey;
        for(int x = 0; x< sizex; x++ ) {
            for (int y= 0; y < sizey; y++) {
                BoardPoint bp = new BoardPoint(x,y);
                tiles.put(bp, new PlainTile(bp));
            }
        }
    }
    
    public int getSizeX() {
        return sizex; 
    }
    
    public int getSizeY() {
        return sizey; 
    }
    
    public void setTile(Tile tile) {
        tiles.put(tile.getBoardPoint(), tile);
    }

    public Tile getTile(BoardPoint bp) {
        return tiles.get(bp);
        
    }
    
    public Tile getTile(int x, int y) {
        return tiles.get(new BoardPoint(x,y));
    }



  
  
}
