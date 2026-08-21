package com.game;

import com.game.Piece.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Board {

  private static final Logger LOGGER = LogManager.getLogger();

  private final List<Piece> board;

  public Board() {
    board = new ArrayList<>();
  }

  public void place(List<Piece> pieces) {
    pieces.forEach(this::place);
  }

  public void place(Piece piece) {
    if (piece != null && isInside(piece.getPosition())) {
      board.add(piece);
    } else {
      LOGGER.warn("Piece could not be placed into board: {}", piece);
    }
  }

  public Piece get(Position position) {
    return board.stream().filter(piece -> piece.getPosition().equals(position)).findFirst().orElse(null);
  }

  public List<Piece> searchFor(Type type, Integer file, Integer rank, Boolean isWhite) {
    Stream<Piece> stream = board.stream();
    if (type != null) {
      stream = stream.filter(p -> p.getType() == type);
    }
    if (file != null) {
      stream = stream.filter(p -> file.equals(p.getPosition().getFile()));
    }
    if (rank != null) {
      stream = stream.filter(p -> rank.equals(p.getPosition().getRank()));
    }
    if (isWhite != null) {
      stream = stream.filter(p -> isWhite.equals(p.isWhite()));
    }
    return stream.toList();
  }

  public boolean isInside(Position pos) {
    return pos != null && ChessUtils.isWithinBoardFiles(pos.getFile()) && ChessUtils.isWithinBoardRanks(pos.getRank());
  }

}
