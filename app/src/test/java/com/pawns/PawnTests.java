package com.pawns;

import com.level.Board;
import com.level.Field;
import com.model.Direction;
import com.pawns.university.Bug;
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

    private Field fieldSpawnP1;
    private Field field2;
    private Field field3;
    private Field field4;

    private Pawn p1;
    private Pawn p2;

    @BeforeEach
    void iniBoard() {
        board = new Board(testLvl);

        fieldSpawnP1 = board.getField((short) 0, (short) 0);
        field2 = board.getField((short) 0, (short) 1);
        field3 = board.getField((short) 1, (short) 0);
        field4 = board.getField((short) 2,(short) 0);

        p1 = new Dumbbell();
        board.pawnsOnBoard.add(p1);
        p1.createSegment(fieldSpawnP1, BodyType.Head);

        p2 = new Bug();
        board.pawnsOnBoard.add(p2);
        p2.createSegment(field4, BodyType.Head);
    }

    @Test
    void moveOneRight() {

        p1.move(fieldSpawnP1, field2, Direction.RIGHT );

        Assertions.assertNotNull(fieldSpawnP1.getSegment());
        Assertions.assertNotNull(field2.getSegment());

        Assertions.assertEquals(fieldSpawnP1.getSegment().getBodyType(), BodyType.TailRight);
        Assertions.assertEquals(field2.getSegment().getBodyType(), BodyType.Head);
    }


    @Test
    void moveOneDown() {

        p1.move(fieldSpawnP1, field3, Direction.DOWN );

        Assertions.assertNotNull(fieldSpawnP1.getSegment());
        Assertions.assertNotNull(field3.getSegment());

        Assertions.assertEquals(fieldSpawnP1.getSegment().getBodyType(), BodyType.TailDown);
        Assertions.assertEquals(field3.getSegment().getBodyType(), BodyType.Head);
    }

    @Test
    void attackTest() {

        p2.move(field4, field3, Direction.UP);

        Assertions.assertNotNull(field4.getSegment());
        Assertions.assertNotNull(field3.getSegment());
        Assertions.assertNotNull(fieldSpawnP1.getSegment());

        p2.attack1(null, fieldSpawnP1);

        Assertions.assertNull(fieldSpawnP1.getSegment());


    }
}
