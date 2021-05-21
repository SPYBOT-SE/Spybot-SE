package com.level;

import java.util.ArrayList;
import java.util.HashMap;

public class levelSingle {

    private static boolean initialized = false;
    public static int[][] getLevel(int levelID) {

        if(!initialized) {
            initializeLevels();
        }

        if(!levels.containsKey(levelID)) {
            return Error;
        }

        return levels.get(levelID);
    }
    /**
     * Hashmap that maps button IDs to levels
     */
    private static HashMap<Integer, int[][]> levels = new HashMap<>();
    private static void initializeLevels() {
        levels.put(0, Ones);
        levels.put(1, TestLevel1);
        levels.put(2, TestLevel2);
        levels.put(3, SPF);
    }

    /*
    Definition:
    
    0x...0  Deaktiviert     Standard Hintergrund
    0x...1  Aktiviert       Standard Hintergrund
    0x...2  Deaktiviert     Classroom Hintergrund
    0x...3  Aktiviert       Classroom Hintergrund
    ... 
    0x...E  Deaktiviert     Hintergrund x
    0x...F  Aktiviert       Hintergrund x

    0x..0.  Kein Spieler!!
    0x..1.  Spieler 0
    0x..2.  Spieler 1
    ...
    0x..F.  Spieler n
    
    0x00..  KI Gegner Figur 0
    0x01..  KI Gegner Figur 1

    */
    public final static int[][] Ones = {
            {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0},
            {0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x1},
            {0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x1},
            {0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x1},
            {0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x1},
            {0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x1},
            {0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x1},
            {0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x1}
            // {1, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 1},
            // {1, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 1},
            // {1, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 1}
    };

    public final static int[][] TestLevel1 = {
            {0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x01, 0x01 },
            {0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01 },
            {0x01, 0x11, 0x01, 0x01, 0x01, 0x01, 0x01, 0x00, 0x01, 0x01, 0x01, 0x01 },
            {0x01, 0x11, 0x01, 0x00, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00 },
            {0x00, 0x01, 0x01, 0x00, 0x03, 0x05, 0x01, 0x01, 0x00, 0x01, 0x01, 0x01 },
            {0x00, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x21, 0x01, 0x01, 0x01 },
            {0x00, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01 },
            {0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x01, 0x01, 0x00, 0x01, 0x01, 0x01 }
    };

    public final static int[][] TestLevel2 = {
            {0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x01, 0x01 },
            {0x01, 0x11, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x11, 0x01 },
            {0x01, 0x11, 0x01, 0x01, 0x01, 0x01, 0x01, 0x00, 0x01, 0x01, 0x01, 0x01 },
            {0x01, 0x11, 0x01, 0x00, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00 },
            {0x00, 0x01, 0x01, 0x00, 0x03, 0x05, 0x01, 0x01, 0x00, 0x01, 0x01, 0x01 },
            {0x00, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x21, 0x01, 0x01, 0x01 },
            {0x01, 0x11, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x11, 0x01 },
            {0x01, 0x00, 0x00, 0x00, 0x01, 0x01, 0x01, 0x01, 0x00, 0x01, 0x01, 0x01 }
    };

    public final static int[][] TestLevel3 = {
            {0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x01, 0x01 },
            {0x01, 0x11, 0x01, 0x01, 0x01, 0x00, 0x01, 0x01, 0x01, 0x01, 0x11, 0x01 },
            {0x01, 0x11, 0x01, 0x01, 0x01, 0x00, 0x01, 0x00, 0x01, 0x01, 0x01, 0x01 },
            {0x01, 0x11, 0x01, 0x00, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00 },
            {0x00, 0x01, 0x01, 0x00, 0x03, 0x01, 0x01, 0x01, 0x00, 0x01, 0x01, 0x01 },
            {0x00, 0x01, 0x01, 0x01, 0x01, 0x00, 0x00, 0x01, 0x21, 0x01, 0x01, 0x01 },
            {0x01, 0x11, 0x01, 0x01, 0x01, 0x00, 0x00, 0x01, 0x01, 0x01, 0x11, 0x01 },
            {0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x01, 0x00, 0x01, 0x01, 0x01 }
    };

    public final static int[][] Error = {
            {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
            {0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x01, 0x01, 0x00, 0x01, 0x00, 0x00 },
            {0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00 },
            {0x00, 0x01, 0x01, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x01, 0x00 },
            {0x00, 0x01, 0x01, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x01, 0x01 },
            {0x00, 0x00, 0x01, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x01, 0x00 },
            {0x00, 0x00, 0x01, 0x00, 0x00, 0x01, 0x01, 0x01, 0x00, 0x00, 0x01, 0x00 },
            {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },


    };

    public final static int[][] SPF = {
            {0x01, 0x01, 0x01, 0x01},
            {0x01, 0x01, 0x01, 0x01},
            {0x01, 0x01, 0x01, 0x01},
            {0x01, 0x01, 0x01, 0x01},
    };
}
