package org.example;

import java.util.Arrays;

public class Main {

    public static final int width = 5;
    public static void main(String[] args) {

        int[][] move = new int[2][width*width];

        for(int i = 0 ;i < 2;i++)
            for (int j =0; j < width*width; j++)
                move[i][j] = -1;

        int [] startPoint = new int[]{2, 2};
        int[][] action = {
                {2,1},
                {2,-1},
                {-2,-1},
                {-2,1},
                {1,2},
                {-1,2},
                {1,-2},
                {-1,-2},
        };

        int[][] environment = new int[width][width];
        int offset = 0;
        if(!run(startPoint, action, environment, move, offset)){
            System.out.println("Not found");
        }

    }

    public static boolean run(int[] startPoint,int[][] action,int[][] environment, int[][] moves, int offset){
        offset++;
        if (offset < width*width){
            moves[0][offset] = startPoint[0];
            moves[1][offset] = startPoint[1];
        }


        if (checkGoalState(environment)){
            System.out.println("Finished with below moves ");
            System.out.println(Arrays.toString(moves[0]));
            System.out.println(Arrays.toString(moves[1]));
            return true;
        }

        int[][] nextMove = new int[8][3]; // Format: {moveX, moveY, onwardMovesCount}
        for (int i = 0; i < 8; i++) {
            nextMove[i][0] = action[i][0];
            nextMove[i][1] = action[i][1];
            nextMove[i][2] = warnsdorfsRule(new int[]{startPoint[0] + action[i][0], startPoint[1] + action[i][1]}, environment, action);
        }

        Arrays.sort(nextMove, (a, b) -> Integer.compare(a[2], b[2]));

        for (int[] move : nextMove)
            if (moveAction(startPoint, move, environment)) {
                if (run(startPoint, action, environment, moves, offset))
                    return true;
                popAction(startPoint, move, environment);
                if (offset > 0) {
                    moves[0][offset] = -1;
                    moves[1][offset] = -1;
                    offset--;
                }

            }



        for(int i=0; i<width;i++)
            for(int j=0; j<width;j++)
                environment[i][j] = 0;

        return false;
    }

    public static boolean moveAction(int[] point, int[] action,int[][] environment){
        if (outsideEnvConstraint(point, action))
            if(checkMoveConstraints(environment, point, action)){
                point[0] += action[0];
                point[1] += action[1];
                environment[point[0]][point[1]] = 1;
                return true;
            }
        return false;
    }

    public static void popAction(int[] point, int[] action,int[][] environment){
        environment[point[0]][point[1]] = 0;
        point[0] -= action[0];
        point[1] -= action[1];
    }

    public static boolean outsideEnvConstraint(int[] point, int[] action){
        return  (point[0] + action[0]) < width && (point[1] + action[1]) < width &&
                    (point[0] + action[0]) >= 0 && (point[1] + action[1]) >= 0;
    }



    public static boolean checkGoalState(int[][] env){
        for(int i=0; i< width; i++)
            for (int j = 0; j < width; j++)
                if (env[i][j] != 1)
                    return false;
        return true;
    }


    public static boolean checkMoveConstraints(int[][] environment,int[] point,  int[] move){
        return environment[point[0]+move[0]][point[1]+move[1]] == 0;
    }

    /*
    add below function by read https://en.wikipedia.org/wiki/Knight%27s_tour reference
    */

    public static int warnsdorfsRule(int[] point, int[][] environment, int[][] action){
        int count = 0;
        for (int[] item : action)
            if (outsideEnvConstraint(new int[]{point[0], point[1]}, item) && environment[point[0] + item[0]][point[1] + item[1]] == 0)
                count++;
        return count;
    }

}