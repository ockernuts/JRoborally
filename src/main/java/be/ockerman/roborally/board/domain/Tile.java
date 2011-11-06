package be.ockerman.roborally.board.domain;

public abstract class Tile {
    private BoardPoint boardPoint;
    private TileType tileType;
    
    protected Tile(BoardPoint boardPoint, TileType tileType) {
        this.boardPoint = boardPoint;
        this.tileType = tileType; 
    }
    
    public BoardPoint getBoardPoint() {
        return boardPoint; 
    }
    
    
    public TileType getTileType() {
        return tileType; 
    }

    public int getX() {
        return boardPoint.getX();
    }
    
    public int getY() {
        return boardPoint.getY(); 
    }

}
