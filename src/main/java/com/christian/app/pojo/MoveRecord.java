package com.christian.app.pojo;

import com.christian.app.game.GameConstants;
import com.christian.app.game.GameUtil;
import com.christian.app.piece.Piece;
import com.christian.app.game.MoveStatus;
import com.christian.app.util.Position;
import java.util.Objects;

public record MoveRecord(
    int moveRecordId,
    int moveNumber,
    boolean activeColor,
    MoveStatus moveStatus,
    Position movedPiecePosition,
    Position destination,
    String extraInfo,
    char movedPieceSymbol,
    char capturedPieceSymbol,
    boolean isCheck,
    boolean isMate) {

  public static MoveRecord create(int moveRecordId, int moveNumber, boolean activeColor, MoveStatus moveStatus, Piece pieceMoved, Piece pieceCaptured, Position destination, String extraInfo, boolean isCheck, boolean isMate) {
    Position piecePosition = pieceMoved == null ? null : pieceMoved.getPosition();
    char movedPieceSymbol = pieceMoved == null ? GameConstants.EMPTY_TILE : pieceMoved.getSymbol();
    char capturedPieceSymbol = pieceCaptured == null ? GameConstants.EMPTY_TILE : pieceCaptured.getSymbol();
    return new MoveRecord(
        moveRecordId,
        moveNumber,
        activeColor,
        moveStatus,
        piecePosition,
        destination,
        extraInfo,
        movedPieceSymbol,
        capturedPieceSymbol,
        isCheck,
        isMate
    );
  }

  public boolean isLegalMove() {
    return moveStatus.isLegalMove();
  }

  public boolean isEnPassantAvailable() {
    return moveStatus == MoveStatus.LEGAL_DOUBLE_JUMP;
  }

  public String getMoveString(boolean includeFile, boolean includeRank) {
    StringBuilder builder = new StringBuilder();
    builder.append(movedPieceSymbol);
    if (includeFile) {
      builder.append(GameUtil.toFileChar(movedPiecePosition.getFile()));
    }
    if (includeRank) {
      builder.append(GameUtil.toRankChar(movedPiecePosition.getRank()));
    }
    return builder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof MoveRecord record)) {
      return false;
    }
    return moveNumber == record.moveNumber && isMate == record.isMate && isCheck == record.isCheck
        && moveRecordId == record.moveRecordId && activeColor == record.activeColor
        && movedPieceSymbol == record.movedPieceSymbol
        && capturedPieceSymbol == record.capturedPieceSymbol && Objects.equals(extraInfo,
        record.extraInfo) && Objects.equals(destination, record.destination)
        && moveStatus == record.moveStatus && Objects.equals(movedPiecePosition,
        record.movedPiecePosition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(moveStatus, movedPiecePosition, destination, extraInfo, moveNumber,
        movedPieceSymbol, capturedPieceSymbol, activeColor, isCheck, isMate);
  }
}
