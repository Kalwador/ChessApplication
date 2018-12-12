package com.chess.spring.engine.move;

import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.Board.Builder;
import com.chess.spring.engine.board.BoardUtils;
import com.chess.spring.engine.pieces.Pawn;
import com.chess.spring.engine.pieces.Piece;
import com.chess.spring.engine.pieces.Rook;

public abstract class Move {

    protected  Board board;
    protected  int destinationCoordinate;
    protected  Piece movedPiece;
    protected  boolean isFirstMove;

    private Move( Board board,
                  Piece pieceMoved,
                  int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = pieceMoved;
        this.isFirstMove = pieceMoved.isFirstMove();
    }

    private Move( Board board,
                  int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + this.destinationCoordinate;
        result = 31 * result + this.movedPiece.hashCode();
        result = 31 * result + this.movedPiece.getPiecePosition();
        result = result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals( Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Move)) {
            return false;
        }
         Move otherMove = (Move) other;
        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
               getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
               getMovedPiece().equals(otherMove.getMovedPiece());
    }

    public Board getBoard() {
        return this.board;
    }

    public int getCurrentCoordinate() {
        return this.movedPiece.getPiecePosition();
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
    }

    public Board execute() {
         Board.Builder builder = new Builder();
        this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.movedPiece.equals(piece)).forEach(builder::setPiece);
        this.board.currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        return builder.build();
    }

    public Board undo() {
         Board.Builder builder = new Builder();
        for ( Piece piece : this.board.getAllPieces()) {
            builder.setPiece(piece);
        }
        builder.setMoveMaker(this.board.currentPlayer().getAlliance());
        return builder.build();
    }

    String disambiguationFile() {
        for( Move move : this.board.currentPlayer().getLegalMoves()) {
            if(move.getDestinationCoordinate() == this.destinationCoordinate && !this.equals(move) &&
               this.movedPiece.getPieceType().equals(move.getMovedPiece().getPieceType())) {
                return BoardUtils.INSTANCE.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0, 1);
            }
        }
        return "";
    }

    public enum MoveStatus {

        DONE {
            @Override
            public boolean isDone() {
                return true;
            }
        },
        ILLEGAL_MOVE {
            @Override
            public boolean isDone() {
                return false;
            }
        },
        LEAVES_PLAYER_IN_CHECK {
            @Override
            public boolean isDone() {
                return false;
            }
        };

        public abstract boolean isDone();

    }

    public static class PawnPromotion
            extends PawnMove {

         Move decoratedMove;
         Pawn promotedPawn;
         Piece promotionPiece;

        public PawnPromotion( Move decoratedMove,
                              Piece promotionPiece) {
            super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
            this.decoratedMove = decoratedMove;
            this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
            this.promotionPiece = promotionPiece;
        }

        @Override
        public int hashCode() {
            return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
        }

        @Override
        public boolean equals( Object other) {
            return this == other || other instanceof PawnPromotion && (super.equals(other));
        }

        @Override
        public Board execute() {
             Board pawnMovedBoard = this.decoratedMove.execute();
             Board.Builder builder = new Builder();
            pawnMovedBoard.currentPlayer().getActivePieces().stream().filter(piece -> !this.promotedPawn.equals(piece)).forEach(builder::setPiece);
            pawnMovedBoard.currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
            builder.setPiece(this.promotionPiece.movePiece(this));
            builder.setMoveMaker(pawnMovedBoard.currentPlayer().getAlliance());
            builder.setMoveTransition(this);
            return builder.build();
        }

        @Override
        public boolean isAttack() {
            return this.decoratedMove.isAttack();
        }

        @Override
        public Piece getAttackedPiece() {
            return this.decoratedMove.getAttackedPiece();
        }

        @Override
        public String toString() {
            return BoardUtils.INSTANCE.getPositionAtCoordinate(this.movedPiece.getPiecePosition()) + "-" +
                   BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate) + "=" + this.promotionPiece.getPieceType();
        }

    }

    public static class MajorMove
            extends Move {

        public MajorMove( Board board,
                          Piece pieceMoved,
                          int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals( Object other) {
            return this == other || other instanceof MajorMove && super.equals(other);
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType().toString() + disambiguationFile() +
                   BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class MajorAttackMove
            extends AttackMove {

        public MajorAttackMove( Board board,
                                Piece pieceMoved,
                                int destinationCoordinate,
                                Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals( Object other) {
            return this == other || other instanceof MajorAttackMove && super.equals(other);

        }

        @Override
        public String toString() {
            return movedPiece.getPieceType() + disambiguationFile() + "x" +
                   BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class PawnMove
            extends Move {

        public PawnMove( Board board,
                         Piece pieceMoved,
                         int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals( Object other) {
            return this == other || other instanceof PawnMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class PawnAttackMove
            extends AttackMove {

        public PawnAttackMove( Board board,
                               Piece pieceMoved,
                               int destinationCoordinate,
                               Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals( Object other) {
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardUtils.INSTANCE.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0, 1) + "x" +
                   BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class PawnEnPassantAttack extends PawnAttackMove {

        public PawnEnPassantAttack( Board board,
                                    Piece pieceMoved,
                                    int destinationCoordinate,
                                    Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals( Object other) {
            return this == other || other instanceof PawnEnPassantAttack && super.equals(other);
        }

        @Override
        public Board execute() {
             Board.Builder builder = new Builder();
            this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.movedPiece.equals(piece)).forEach(builder::setPiece);
            this.board.currentPlayer().getOpponent().getActivePieces().stream().filter(piece -> !piece.equals(this.getAttackedPiece())).forEach(builder::setPiece);
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            builder.setMoveTransition(this);
            return builder.build();
        }

        @Override
        public Board undo() {
             Board.Builder builder = new Builder();
            for ( Piece piece : this.board.getAllPieces()) {
                builder.setPiece(piece);
            }
            builder.setEnPassantPawn((Pawn)this.getAttackedPiece());
            builder.setMoveMaker(this.board.currentPlayer().getAlliance());
            return builder.build();
        }

    }

    public static class PawnJump
            extends Move {

        public PawnJump( Board board,
                         Pawn pieceMoved,
                         int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals( Object other) {
            return this == other || other instanceof PawnJump && super.equals(other);
        }

        @Override
        public Board execute() {
             Board.Builder builder = new Builder();
            this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.movedPiece.equals(piece)).forEach(builder::setPiece);
            this.board.currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
             Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            builder.setMoveTransition(this);
            return builder.build();
        }

        @Override
        public String toString() {
            return BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    static abstract class CastleMove
            extends Move {

         Rook castleRook;
         int castleRookStart;
         int castleRookDestination;

        CastleMove( Board board,
                    Piece pieceMoved,
                    int destinationCoordinate,
                    Rook castleRook,
                    int castleRookStart,
                    int castleRookDestination) {
            super(board, pieceMoved, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        Rook getCastleRook() {
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {
             Board.Builder builder = new Builder();
            for ( Piece piece : this.board.getAllPieces()) {
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            //calling movePiece here doesn't work, we need to explicitly create a new Rook
            builder.setPiece(new Rook(this.castleRook.getPieceAllegiance(), this.castleRookDestination, false));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            builder.setMoveTransition(this);
            return builder.build();
        }

        @Override
        public int hashCode() {
             int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castleRook.hashCode();
            result = prime * result + this.castleRookDestination;
            return result;
        }

        @Override
        public boolean equals( Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CastleMove)) {
                return false;
            }
             CastleMove otherCastleMove = (CastleMove) other;
            return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
        }

    }

    public static class KingSideCastleMove
            extends CastleMove {

        public KingSideCastleMove( Board board,
                                   Piece pieceMoved,
                                   int destinationCoordinate,
                                   Rook castleRook,
                                   int castleRookStart,
                                   int castleRookDestination) {
            super(board, pieceMoved, destinationCoordinate, castleRook, castleRookStart,
                    castleRookDestination);
        }

        @Override
        public boolean equals( Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof KingSideCastleMove)) {
                return false;
            }
             KingSideCastleMove otherKingSideCastleMove = (KingSideCastleMove) other;
            return super.equals(otherKingSideCastleMove) && this.castleRook.equals(otherKingSideCastleMove.getCastleRook());
        }

        @Override
        public String toString() {
            return "O-O";
        }

    }

    public static class QueenSideCastleMove
            extends CastleMove {

        public QueenSideCastleMove( Board board,
                                    Piece pieceMoved,
                                    int destinationCoordinate,
                                    Rook castleRook,
                                    int castleRookStart,
                                    int rookCastleDestination) {
            super(board, pieceMoved, destinationCoordinate, castleRook, castleRookStart,
                    rookCastleDestination);
        }

        @Override
        public boolean equals( Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof QueenSideCastleMove)) {
                return false;
            }
             QueenSideCastleMove otherQueenSideCastleMove = (QueenSideCastleMove) other;
            return super.equals(otherQueenSideCastleMove) && this.castleRook.equals(otherQueenSideCastleMove.getCastleRook());
        }

        @Override
        public String toString() {
            return "O-O-O";
        }

    }

    static abstract class AttackMove
            extends Move {

        private  Piece attackedPiece;

        AttackMove( Board board,
                    Piece pieceMoved,
                    int destinationCoordinate,
                    Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate);
            this.attackedPiece = pieceAttacked;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals( Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AttackMove)) {
                return false;
            }
             AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

    }

    private static class NullMove
            extends Move {

        private NullMove() {
            super(null, -1);
        }

        @Override
        public int getCurrentCoordinate() {
            return -1;
        }

        @Override
        public int getDestinationCoordinate() {
            return -1;
        }

        @Override
        public Board execute() {
            throw new RuntimeException("cannot execute null move!");
        }

        @Override
        public String toString() {
            return "Null MoveImplementation";
        }
    }

    public static class MoveFactory {

        private static  Move NULL_MOVE = new NullMove();

        private MoveFactory() {
            throw new RuntimeException("Not instantiatable!");
        }

        public static Move getNullMove() {
            return NULL_MOVE;
        }

        public static Move createMove( Board board,
                                       int currentCoordinate,
                                       int destinationCoordinate) {
            for ( Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                    move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}