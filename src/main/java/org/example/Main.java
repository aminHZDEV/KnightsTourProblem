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
        int counter = 0;
        if(!run(startPoint, action, environment, counter)){
            System.out.println("Not found");
        }

    }

    public static boolean run(int[] startPoint,int[][] action,int[][] environment, int counter){
        counter++;
        if (counter > 64) {
            for (int i = 0; i < width; i++)
                for (int j = 0; j < width; j++)
                    environment[i][j] = 0;
            return false;
        }
        if (checkGoalState(environment)){
            System.out.println("Finish");
            System.out.println(Arrays.deepToString(environment));
            return true;
        }

        for(int i=0 ; i <width; i++)
            if(moveAction(startPoint, action[i], environment)){
                run(startPoint, action, environment, counter);
                popAction(startPoint, action[i], environment);
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
        point[0] -= action[0];
        point[1] -= action[1];
        environment[point[0]][point[1]] = 0;

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
}