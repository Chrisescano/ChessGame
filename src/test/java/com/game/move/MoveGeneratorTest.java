package com.game.move;

import com.game.Board;
import com.game.Piece;
import com.game.move.MoveGenerator.Results;
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

  /**
   * <b>Sliding Generation Tests</b>
   */

  @Test
  private void testEmptyBoardSlidingGeneration() {
    Piece rook = new Piece('R', 0, 7);
    board.place(rook);

    Results results = generator.generate(rook, board);

    Assert.assertEquals(results.getSize(), 14);
  }

  @Test
  private void testOccupiedBoardSlidingGeneration() {
    Piece rook = new Piece('R', 0, 7);
    Piece pawn = new Piece('p', 0, 6);
    Piece knight = new Piece('N', 1, 7);
    board.place(rook);
    board.place(pawn);
    board.place(knight);

    Results results = generator.generate(rook, board);

    Assert.assertEquals(results.getSize(), 1);
  }

  /**
   * <b>Step Generation Tests</b>
   */

  @Test
  private void testEmptyBoardStepGeneration() {
    Piece knight = new Piece('N', 0, 7);
    board.place(knight);

    Results results = generator.generate(knight, board);

    Assert.assertEquals(results.getSize(), 2);
  }

  @Test
  private void testOccupiedBoardStepGeneration() {
    Piece knight = new Piece('N', 0, 7);
    Piece rook = new Piece('R', 1, 5);
    Piece pawn = new Piece('p', 2, 6);
    board.place(knight);
    board.place(rook);
    board.place(pawn);

    Results results = generator.generate(knight, board);

    Assert.assertEquals(results.getSize(), 1);
  }

  /**
   * <b>Pawn Generation Tests</b>
   */

  @Test
  private void testEmptyBoardPawnNotMovedStepGeneration() {
    Piece pawn = new Piece('P', 1, 7);
    board.place(pawn);

    Results results = generator.generate(pawn, board);

    Assert.assertEquals(results.getSize(), 2);
  }

  @Test
  private void testEmptyBoardPawnMovedStepGeneration() {
    Piece pawn = new Piece('P', 1, 7);
    pawn.setMoved(true);
    board.place(pawn);

    Results results = generator.generate(pawn, board);

    Assert.assertEquals(results.getSize(), 1);
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

    Results results = generator.generate(pawn, board);

    Assert.assertEquals(results.getSize(), 1);
  }
}