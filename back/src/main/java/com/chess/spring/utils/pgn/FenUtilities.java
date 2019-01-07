package com.chess.spring.utils.pgn;

import com.chess.spring.engine.board.BoardBuilder;
import com.chess.spring.engine.pieces.utils.PlayerColor;
import com.chess.spring.engine.board.Board;
import com.chess.spring.engine.board.BoardService;
import com.chess.spring.engine.pieces.*;

public class FenUtilities {

    private FenUtilities() {
        throw new RuntimeException("Not Instantiable!");
    }

    public static Board createGameFromFEN(final String fenString) {
        return parseFEN(fenString);
    }

    public static String createFENFromGame(final Board board) {
        return calculateBoardText(board) + " " +
               calculateCurrentPlayerText(board) + " " +
               calculateCastleText(board) + " " +
               calculateEnPassantSquare(board) + " " +
               "0 1";
    }

    private static Board parseFEN(final String fenString) {
        final String[] fenPartitions = fenString.trim().split(" ");
        final BoardBuilder builder = new BoardBuilder();
        final boolean whiteKingSideCastle = whiteKingSideCastle(fenPartitions[2]);
        final boolean whiteQueenSideCastle = whiteQueenSideCastle(fenPartitions[2]);
        final boolean blackKingSideCastle = blackKingSideCastle(fenPartitions[2]);
        final boolean blackQueenSideCastle = blackQueenSideCastle(fenPartitions[2]);
        final String gameConfiguration = fenPartitions[0];
        final char[] boardTiles = gameConfiguration.replaceAll("/", "")
                .replaceAll("8", "--------")
                .replaceAll("7", "-------")
                .replaceAll("6", "------")
                .replaceAll("5", "-----")
                .replaceAll("4", "----")
                .replaceAll("3", "---")
                .replaceAll("2", "--")
                .replaceAll("1", "-")
                .toCharArray();
        int i = 0;
        while (i < boardTiles.length) {
            switch (boardTiles[i]) {
                case 'r':
                    builder.setPiece(new Rook(PlayerColor.BLACK, i));
                    i++;
                    break;
                case 'n':
                    builder.setPiece(new Knight(PlayerColor.BLACK, i));
                    i++;
                    break;
                case 'b':
                    builder.setPiece(new Bishop(PlayerColor.BLACK, i));
                    i++;
                    break;
                case 'q':
                    builder.setPiece(new Queen(PlayerColor.BLACK, i));
                    i++;
                    break;
                case 'k':
                    final boolean isCastled = !blackKingSideCastle && !blackQueenSideCastle;
                    builder.setPiece(new King(PlayerColor.BLACK, i, blackKingSideCastle, blackQueenSideCastle));
                    i++;
                    break;
                case 'p':
                    builder.setPiece(new Pawn(PlayerColor.BLACK, i));
                    i++;
                    break;
                case 'R':
                    builder.setPiece(new Rook(PlayerColor.WHITE, i));
                    i++;
                    break;
                case 'N':
                    builder.setPiece(new Knight(PlayerColor.WHITE, i));
                    i++;
                    break;
                case 'B':
                    builder.setPiece(new Bishop(PlayerColor.WHITE, i));
                    i++;
                    break;
                case 'Q':
                    builder.setPiece(new Queen(PlayerColor.WHITE, i));
                    i++;
                    break;
                case 'K':
                    builder.setPiece(new King(PlayerColor.WHITE, i, whiteKingSideCastle, whiteQueenSideCastle));
                    i++;
                    break;
                case 'P':
                    builder.setPiece(new Pawn(PlayerColor.WHITE, i));
                    i++;
                    break;
                case '-':
                    i++;
                    break;
                default:
                    throw new RuntimeException("Invalid FEN String " +gameConfiguration);
            }
        }
        builder.setMoveMaker(moveMaker(fenPartitions[1]));
        return builder.build();
    }

    private static PlayerColor moveMaker(final String moveMakerString) {
        if(moveMakerString.equals("w")) {
            return PlayerColor.WHITE;
        } else if(moveMakerString.equals("b")) {
            return PlayerColor.BLACK;
        }
        throw new RuntimeException("Invalid FEN String " +moveMakerString);
    }

    private static boolean whiteKingSideCastle(final String fenCastleString) {
        return fenCastleString.contains("K");
    }

    private static boolean whiteQueenSideCastle(final String fenCastleString) {
        return fenCastleString.contains("Q");
    }

    private static boolean blackKingSideCastle(final String fenCastleString) {
        return fenCastleString.contains("k");
    }

    private static boolean blackQueenSideCastle(final String fenCastleString) {
        return fenCastleString.contains("q");
    }

    private static String calculateCastleText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        if(board.whitePlayer().isKingSideCastleCapable()) {
            builder.append("K");
        }
        if(board.whitePlayer().isQueenSideCastleCapable()) {
            builder.append("Q");
        }
        if(board.blackPlayer().isKingSideCastleCapable()) {
            builder.append("k");
        }
        if(board.blackPlayer().isQueenSideCastleCapable()) {
            builder.append("q");
        }
        final String result = builder.toString();

        return result.isEmpty() ? "-" : result;
    }

    private static String calculateEnPassantSquare(final Board board) {
        final Pawn enPassantPawn = board.getEnPassant();
        if(enPassantPawn != null) {
            return BoardService.INSTANCE.getPositionAtCoordinate(enPassantPawn.getPosition() +
                    (8) * enPassantPawn.getPieceAllegiance().getOppositeDirection());
        }
        return "-";
    }

    private static String calculateBoardText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardService.NUM_TILES; i++) {
            final String tileText = board.getPiece(i) == null ? "-" :
                    board.getPiece(i).getPieceAllegiance().isWhite() ? board.getPiece(i).toString() :
                    board.getPiece(i).toString().toLowerCase();
            builder.append(tileText);
        }
        builder.insert(8, "/");
        builder.insert(17, "/");
        builder.insert(26, "/");
        builder.insert(35, "/");
        builder.insert(44, "/");
        builder.insert(53, "/");
        builder.insert(62, "/");
        return builder.toString()
                .replaceAll("--------", "8")
                .replaceAll("-------", "7")
                .replaceAll("------", "6")
                .replaceAll("-----", "5")
                .replaceAll("----", "4")
                .replaceAll("---", "3")
                .replaceAll("--", "2")
                .replaceAll("-", "1");
    }

    private static String calculateCurrentPlayerText(final Board board) {
        return board.getCurrentPlayer().toString().substring(0, 1).toLowerCase();
    }

}
