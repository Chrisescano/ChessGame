package com.christian.app.game;

import com.christian.app.parser.AlgebraicNotationParser;
import com.christian.app.piece.Piece;
import com.christian.app.piece.Type;
import com.christian.app.util.AlgebraicNotation;
import com.christian.app.util.MoveRecord;
import com.christian.app.util.MoveStatus;
import com.christian.app.util.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoveManager {

  private final AlgebraicNotationParser parser = new AlgebraicNotationParser();
  private final List<MoveRecord> moveRecords = new ArrayList<>();
  private final Board board;

  private int moveNumber;
  private boolean activeColor;

  public MoveManager(final Board board, final int moveNumber, final boolean activeColor) {
    this.board = board;
    this.moveNumber = moveNumber;
    this.activeColor = activeColor;
  }

  public boolean isMoveLegal(final String move) {
    AlgebraicNotation algebraicNotation = parser.parse(move);
    if (algebraicNotation == null || !GameUtil.isInsideBoard(algebraicNotation.getEndPosition())) {
      MoveRecord record = createRecord(MoveStatus.UNPARSEABLE_MOVE, move);
      logMoveRecord(record);
      return false;
    }

    List<Piece> results = searchBoard(activeColor, algebraicNotation);
    if (results.isEmpty()) {
      MoveRecord record = createRecord(MoveStatus.NO_PIECES_FOUND, move);
      logMoveRecord(record);
      return false;
    }

    List<MoveRecord> records = evaluatePieces(results, algebraicNotation);
    List<MoveRecord> legalRecords = records.stream().filter(MoveRecord::isLegalMove).toList();
    if (legalRecords.isEmpty()) {
      if (records.size() == 1) { //more specific reason for move legality
        logMoveRecord(records.getFirst());
      } else {
        logMoveRecord(createRecord(MoveStatus.NO_LEGAL_PIECES_FOUND, algebraicNotation.getEndPosition(), move));
      }
      return false;
    } else if (legalRecords.size() > 1) {
      boolean isPrintFiles = legalRecords.stream().map(r -> r.movedPiecePosition().getFile())
          .collect(Collectors.toSet()).size() == legalRecords.size();
      String extraInfo = String.format("did you mean %s", legalRecords.stream()
              .map(r -> r.getMoveString(isPrintFiles, !isPrintFiles))
              .map(moveStr -> "[" + moveStr + GameUtil.toChessNotation(algebraicNotation.getEndPosition()) + "]")
              .collect(Collectors.joining(" or "))
      );
      MoveRecord record = createRecord(MoveStatus.AMBIGUOUS_MOVE, algebraicNotation.getEndPosition(), extraInfo);
      logMoveRecord(record);
      return false;
    } else {
      logMoveRecord(legalRecords.getFirst());
      return legalRecords.getFirst().isLegalMove();
    }
  }

  private List<Piece> searchBoard(boolean activeColor, AlgebraicNotation algebraicNotation) {
    return board.search(
        activeColor,
        algebraicNotation.getType(),
        algebraicNotation.getStartFile(),
        algebraicNotation.getStartRank(),
        algebraicNotation.getEndPosition()
    );
  }

  private List<MoveRecord> searchRecords(Boolean isLegalMove) {
    Stream<MoveRecord> results = moveRecords.stream();

    if (isLegalMove != null) {
      results = results.filter(r -> r.isLegalMove() == isLegalMove);
    }

    return results.toList();
  }

  private List<MoveRecord> evaluatePieces(List<Piece> pieces, AlgebraicNotation algebraicNotation) {
    return pieces.stream().map(p -> isLegalPieceMove(p, algebraicNotation.getEndPosition(), algebraicNotation.isCapture())).toList();
  }

  private MoveRecord isLegalPieceMove(Piece piece, Position move, boolean isCapture) {
    Direction direction = piece.directionOf(move);
    List<Position> path = piece.getPath(direction);
    path = path.subList(0, path.indexOf(move) + 1);

    if (piece.getType() == Type.PAWN) {
      return isLegalPawnMove(piece, direction, path);
    }

    if (piece.getType() == Type.KING) {
      return isLegalKingMove(piece, direction, path);
    }

    return isLegalRegularMove(piece, path, isCapture);
  }

  private MoveRecord isLegalRegularMove(Piece piece, List<Position> path, boolean isCapture) {
    MoveStatus moveStatus = MoveStatus.LEGAL_MOVE;
    Piece pieceCaptured = null;
    String extraInfo = GameUtil.toChessNotation(path.getLast());
    for (int i = 0; i < path.size(); i++) {
      Position tile = path.get(i);
      if (GameUtil.isInsideBoard(tile)) {
        Piece boardPiece = board.getPiece(tile);
        if (isOccupied(boardPiece)) {
          if (isEnemies(piece, boardPiece)) {
            if (i == path.size() - 1) {
              if (isCapture) {
                moveStatus = MoveStatus.LEGAL_CAPTURE;
              } else {
                moveStatus = MoveStatus.ILLEGAL_MOVE;
                break;
              }
            } else {
              moveStatus = MoveStatus.ILLEGAL_MOVE;
              extraInfo = "path blocked";
            }
          } else {
            moveStatus = MoveStatus.ILLEGAL_MOVE;
            extraInfo = "path blocked";
            break;
          }
          pieceCaptured = boardPiece;
        }
      } else {
        moveStatus = MoveStatus.ILLEGAL_MOVE;
        extraInfo = "out of bounds";
      }
    }
    return createRecord(moveStatus, piece, pieceCaptured, path.getLast(), extraInfo);
  }

  private MoveRecord isLegalPawnMove(Piece piece, Direction direction, List<Position> path) {
    MoveStatus moveStatus = MoveStatus.LEGAL_MOVE;
    Piece pieceCaptured = null;
    String extraInfo = GameUtil.toChessNotation(path.getLast());
    if (direction == Direction.NORTH || direction == Direction.SOUTH) {
      if (!board.isEmpty(path.getFirst())) {
        moveStatus = MoveStatus.ILLEGAL_MOVE;
        extraInfo = "is occupied";
      } else {
        if (path.size() == 2) {
          if (board.isEmpty(path.getLast())) {
            if (piece.isMoved()) {
              moveStatus = MoveStatus.ILLEGAL_MOVE;
            } else {
              moveStatus = MoveStatus.LEGAL_DOUBLE_JUMP;
            }
          } else {
            moveStatus = MoveStatus.ILLEGAL_MOVE;
            extraInfo = "is occupied";
          }
        }
      }
    } else { //capturing
      if (board.isEmpty(path.getFirst())) {
        List<MoveRecord> records = searchRecords(true);
        if (!records.isEmpty() && records.getLast().isEnPassantAvailable()) {
          pieceCaptured = board.getPiece(path.getLast().getFile(), piece.getPosition().getRank());
          moveStatus = MoveStatus.LEGAL_EN_PASSANT;
        } else {
          moveStatus = MoveStatus.ILLEGAL_MOVE;
        }
      } else {
        Piece boardPiece = board.getPiece(path.getFirst());
        if (isEnemies(piece, boardPiece)) {
          pieceCaptured = boardPiece;
          moveStatus = MoveStatus.LEGAL_CAPTURE;
        } else {
          moveStatus = MoveStatus.ILLEGAL_MOVE;
        }
      }
    }
    return createRecord(moveStatus, piece, pieceCaptured, path.getLast(), extraInfo);
  }

  private MoveRecord isLegalKingMove(Piece piece, Direction direction, List<Position> path) {
    return createRecord(MoveStatus.ILLEGAL_MOVE, "king legal not yet implemented");
  }

  private MoveRecord createRecord(MoveStatus moveStatus, String extraInfo) {
    return MoveRecord.create(
        moveRecords.size(),
        moveNumber,
        activeColor,
        moveStatus,
        null,
        null,
        null,
        extraInfo,
        false,
        false
    );
  }

  private MoveRecord createRecord(MoveStatus moveStatus, Position destination, String extraInfo) {
    return MoveRecord.create(
        moveRecords.size(),
        moveNumber,
        activeColor,
        moveStatus,
        null,
        null,
        destination,
        extraInfo,
        false,
        false
    );
  }

  private MoveRecord createRecord(MoveStatus moveStatus, Piece piece, Position destination, String extraInfo) {
    return MoveRecord.create(
        moveRecords.size(),
        moveNumber,
        activeColor,
        moveStatus,
        piece,
        null,
        destination,
        extraInfo,
        false,
        false
    );
  }

  private MoveRecord createRecord(MoveStatus moveStatus, Piece pieceMoved, Piece pieceCaptured, Position destination, String extraInfo) {
    return MoveRecord.create(
        moveRecords.size(),
        moveNumber,
        activeColor,
        moveStatus,
        pieceMoved,
        pieceCaptured,
        destination,
        extraInfo,
        false,
        false
    );
  }

  private void logMoveRecord(MoveRecord moveRecord) {
    moveRecords.add(moveRecord);
    if (moveRecord.isLegalMove()) {
      activeColor = !activeColor;
      moveNumber++;
    }
  }

  private boolean isOccupied(Piece piece) {
    return piece != null;
  }

  private boolean isEnemies(Piece pieceA, Piece pieceB) {
    return pieceA.isWhite() ^ pieceB.isWhite();
  }

  public MoveRecord getLastMoveRecord() {
    if (!moveRecords.isEmpty()) {
      return moveRecords.getLast();
    }
    return null;
  }

  public int getLastMoveRecordId() {
    MoveRecord record = getLastMoveRecord();
    return record == null ? -1 : record.moveRecordId();
  }
}
