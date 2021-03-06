package com.pawns;

import com.level.Board;
import com.level.Field;
import com.model.Direction;
import com.pawns.university.Dumbbell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnTests {

    private final int[][] testLvl = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private Board board;

    private Field field1;
    private Field field2;
    private Field field3;

    private Pawn p;

    @BeforeEach
    void iniBoard() {
        board = new Board(testLvl);

        field1 = board.getField((short) 0, (short) 0);
        field2 = board.getField((short) 0, (short) 1);
        field3 = board.getField((short) 1, (short) 0);

        p = new Dumbbell();
        board.pawnsOnBoard.add(p);
        p.createSegment(field1, BodyType.Head);
    }

    @Test
    void moveOneRight() {

        p.move(field1, field2, Direction.RIGHT );

        Assertions.assertNotNull(field1.getSegment());
        Assertions.assertNotNull(field2.getSegment());

        Assertions.assertEquals(field1.getSegment().getBodyType(), BodyType.TailRight);
        Assertions.assertEquals(field2.getSegment().getBodyType(), BodyType.Head);
    }


    @Test
    void moveOneDown() {

        p.move(field1, field3, Direction.DOWN );

        Assertions.assertNotNull(field1.getSegment());
        Assertions.assertNotNull(field3.getSegment());

        Assertions.assertEquals(field1.getSegment().getBodyType(), BodyType.TailDown);
        Assertions.assertEquals(field3.getSegment().getBodyType(), BodyType.Head);
    }
}
