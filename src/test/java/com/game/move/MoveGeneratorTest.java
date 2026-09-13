package com.game.move;

import com.game.Board;
import com.game.ChessGame.GameState;
import com.game.Piece;
import com.game.move.Move.Type;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MoveGeneratorTest {

  private Board board;
  private MoveGenerator generator;

  @BeforeClass
  public void setUp() {
    generator = new MoveGenerator();
  }

  @BeforeMethod
  public void beforeMethod() {
    board = new Board();
  }

  public int totalCount(List<Move> moves) {
    int count = 0;
    for (Move move : moves) {
      count += move.count();
    }
    return count;
  }

  /**
   * <b>Sliding Generation Tests</b>
   */

  @Test
  private void testEmptyBoardSlidingGeneration() {
    Piece rook = new Piece('R', 0, 7);
    board.place(rook);

    List<Move> moves = generator.generate(rook, new GameState(board, null));

    Assert.assertEquals(totalCount(moves), 14);
  }

  @Test
  private void testOccupiedBoardSlidingGeneration() {
    Piece rook = new Piece('R', 0, 7);
    Piece pawn = new Piece('p', 0, 6);
    Piece knight = new Piece('N', 1, 7);
    board.place(rook);
    board.place(pawn);
    board.place(knight);

    List<Move> moves = generator.generate(rook, new GameState(board, null));

    Assert.assertEquals(totalCount(moves), 1);
  }

  /**
   * <b>Step Generation Tests</b>
   */

  @Test
  private void testEmptyBoardStepGeneration() {
    Piece knight = new Piece('N', 0, 7);
    board.place(knight);

    List<Move> moves = generator.generate(knight, new GameState(board, null));

    Assert.assertEquals(totalCount(moves), 2);
  }

  @Test
  private void testOccupiedBoardStepGeneration() {
    Piece knight = new Piece('N', 0, 7);
    Piece rook = new Piece('R', 1, 5);
    Piece pawn = new Piece('p', 2, 6);
    board.place(knight);
    board.place(rook);
    board.place(pawn);

    List<Move> moves = generator.generate(knight, new GameState(board, null));

    Assert.assertEquals(totalCount(moves), 1);
  }

  /**
   * <b>Pawn Generation Tests</b>
   */

  @Test
  private void testEmptyBoardPawnNotMovedStepGeneration() {
    Piece pawn = new Piece('P', 1, 6);
    board.place(pawn);

    List<Move> moves = generator.generate(pawn, new GameState(board, null));

    Assert.assertEquals(totalCount(moves), 2);
  }

  @Test
  private void testEmptyBoardPawnMovedStepGeneration() {
    Piece pawn = new Piece('P', 1, 6);
    pawn.setMoved(true);
    board.place(pawn);

    List<Move> moves = generator.generate(pawn, new GameState(board, null));

    Assert.assertEquals(totalCount(moves), 1);
  }

  @Test
  private void testOccupiedBoardPawnStepGeneration() {
    Piece pawn = new Piece('P', 1, 7);
    Piece rook = new Piece('R', 1, 6);
    Piece knight = new Piece('n', 0, 6);
    Piece rook2 = new Piece('R', 2, 6);
    board.place(pawn);
    board.place(rook);
    board.place(knight);
    board.place(rook2);

    List<Move> moves = generator.generate(pawn, new GameState(board, null));

    Assert.assertEquals(totalCount(moves), 1);
  }

  @Test
  private void testEnPassant() {
    Piece pawnA = new Piece('P', 0, 5);
    Piece pawnB = new Piece('p', 1, 5);
    board.place(pawnA);
    board.place(pawnB);
    Move prevMove = new Move(pawnA.getType());
    prevMove.add(pawnA.getPosition(), Type.DOUBLE_JUMP);

    List<Move> moves = generator.generate(pawnB, new GameState(board, prevMove));

    Assert.assertEquals(totalCount(moves), 2);
    Assert.assertTrue(moves.stream().anyMatch(m -> m.getType().equals(Type.EN_PASSANT)));
  }
}