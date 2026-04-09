import java.util.Scanner;

class Car{
    String model;
    int year;
    int horse_power;
}

public class Main {
    public static char[][][] map2 = {
// СЕКТОР 0 (9x8)
            {
                    {'_', '#', '#', '#', '#', '#', '#', '_'},
                    {'#', 'F', 'X', '_', '_', '_', '_', '#'},
                    {'#', '#', '#', '#', '#', '#', '_', '#'},
                    {'#', '_', '_', '_', '_', '_', '_', '#'},
                    {'#', '_', '#', '#', '#', '#', '#', '#'},
                    {'#', '_', '#', '#', '#', '#', '#', '#'},
                    {'#', '_', '_', '_', '_', '#', '#', '#'},
                    {'#', '#', 'U', '#', '_', '_', '_', '#'},
                    {'_', '#', '#', '#', '#', '#', '#', '_'}
            },
// СЕКТОР 1 (10x7)
            {
                    {'_', '#', '#', '#', '#', '#', '_'},
                    {'#', '#', '#', '#', 'U', '_', '#'},
                    {'#', '_', '_', '_', '_', '_', '#'},
                    {'#', '_', '#', '#', '#', '#', '#'},
                    {'#', '_', '_', '_', '_', '_', '#'},
                    {'#', '#', '#', '#', '#', '_', '#'},
                    {'#', 'O', '_', '_', '_', '_', '#'},
                    {'#', '#', 'D', '#', '#', '#', '#'},
                    {'#', '#', '_', '_', '_', '_', '#'},
                    {'_', '#', '#', '#', '#', '#', '_'}
            },
// СЕКТОР 2 (7x6)
            {
                    {'_', '#', '#', '#', '#', '_'},
                    {'#', '#', '#', '#', 'D', '#'},
                    {'#', '_', '_', '_', '_', '#'},
                    {'#', '_', '#', '#', '#', '#'},
                    {'#', '_', '_', '_', '_', '#'},
                    {'#', '#', '_', '#', '_', '#'},
                    {'_', '#', '#', '#', '#', '_'}
            }
    };
    public static int currentFloor = 0;
    public static int playerY = 1;
    public static int playerX = 4;
    public static int filter = 30;
    public static boolean end = false;
    public static String[] floorNames = {"СЕКТОР 0","СЕКТОР 1","СЕКТОР 2"};
    public static Scanner scanner = new Scanner(System.in);
    public static int dronBattery = 3;

    public static void main(String[] args) {
        System.out.println("Игра началась");
        gameLoop();
        scanner.close();
    }


    public static void gameLoop() {
        while (!end) {
            if (filter <= 0) {
                System.out.println("Последний вдох принес лишь едкий привкус мутагена. Фильтр мертв, как и вы.");
                return;
            }

            System.out.print("Статус:");
            System.out.print(floorNames[currentFloor]);
            System.out.print(" | Позиция [Y:" + playerY + "X:" + playerX + "]");
            System.out.print(" | Фильтр: " + filter);
            System.out.print(" | батарея дрона: " + dronBattery);

            System.out.println();

            System.out.println("Карта:");
            printMap();

            System.out.println();
            System.out.print("Введите команду: ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) continue;

            char cmd = input.charAt(0);

            if (cmd == 'w' || cmd == 's' || cmd == 'a' || cmd == 'd') {
                movePlayer(cmd);
            } else if (cmd == 'e'){
                System.out.println("Укажите направление для взаимодействия:");
                String input2 = scanner.nextLine().trim().toLowerCase();
                char dir = input2.charAt(0);

                if (dir == 'w' || dir == 's' || dir == 'a' || dir == 'd') {
                    interact(dir);
                } else {
                    System.out.println("Неизвестное направление");
                }
            } else if (cmd == 'r' && input.length() >= 2) {
                char dir = input.charAt(1);
                if (dir == 'w' || dir == 's' || dir == 'a' || dir == 'd') {
                    operator(dir);
                } else {
                    System.out.println("Неверное направление");
                }
            } else {
                System.out.println("Неверная команда");
            }

        }

    }

    public static void operator(char dir) {
        int ny = playerY;
        int nx = playerX;

        int ny2 = playerY;
        int nx2 = playerX;

        switch (dir) {
            case 'w':ny--;ny2-=2;break;
            case 's':ny++;ny2+=2;break;
            case 'a':nx--;nx2-=2;break;
            case 'd':nx++;nx2+=2;break;
        }

        char cell = map2[currentFloor][ny][nx];
        char target = map2[currentFloor][ny2][nx2];

        if (cell == '_' && target == 'X') {
            filter-=5;
            dronBattery--;
            map2[currentFloor][ny2][nx2] = '_';
            System.out.println("Гермодверь открыта");
            return;
        }

        System.out.println("неверное использование");



    }


    public static void movePlayer(char dir) {
        int ny = playerY;
        int nx = playerX;

        switch (dir) {
            case 'w':ny--;break;
            case 's':ny++;break;
            case 'a':nx--;break;
            case 'd':nx++;break;
        }

        char target;
        try {
            target = map2[currentFloor][ny][nx];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Вы упали в бездну");
            end = true;
            return;
        }


        if (target == '#') {
            System.out.println("Там стена, пройти нельзя");
            return;
        }

        if (target == 'F') {
            System.out.println("Удаленный взлом завершен. Вы отзываете дрона и спокойно шагаете в чистую зону деконтаминации.");
            end = true;
            return;
        }

        if (target == 'O') {
            filter+=10;
            System.out.println("Вы подобрали защиту, фильтр: " + filter);
        }

        if (target == 'X') {
            System.out.println("Чтобы пройти нужно открыть гермодверь");
            return;
        }

        playerX = nx;
        playerY = ny;
        filter--;
    }


    public static void interact(char dir) {
        int ny = playerY;
        int nx = playerX;

        switch (dir) {
            case 'w':ny--;break;
            case 's':ny++;break;
            case 'a':nx--;break;
            case 'd':nx++;break;
        }

        char cell;
        try{
            cell = map2[currentFloor][ny][nx];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Там бездна");
            return;
        }

        if (cell == 'D') {
            int nextFloor = currentFloor - 1;
            boolean found = false;
            for (int y = 0; y < map2[nextFloor].length && !found;y++) {
                for (int x = 0; x < map2[nextFloor][y].length;x++) {
                    try {
                        if (map2[nextFloor][y][x] == 'U') {
                            currentFloor = nextFloor;
                            playerY = y;
                            playerX = x;
                            filter--;
                            found = true;
                            System.out.println("Вы опустились на сектор ниже");
                            return;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("ошибка поиска лестницы");
                        return;
                    }
                }
            }
            return;
        }

        if (cell == 'U') {
            int nextFloor = currentFloor + 1;
            boolean found = false;
            for (int y = 0; y < map2[nextFloor].length && !found;y++) {
                for (int x = 0; x < map2[nextFloor][y].length;x++) {
                    try {
                        if (map2[nextFloor][y][x] == 'D') {
                            currentFloor = nextFloor;
                            playerY = y;
                            playerX = x;
                            filter--;
                            found = true;
                            System.out.println("Вы поднялись на сектор выше");
                            return;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("ошибка поиска лестницы");
                        return;
                    }
                }
            }
            return;
        }

        System.out.println("В этом направлении нет лестницы");

    }

    public static void printMap() {
        char[][] floor = map2[currentFloor];
        for (int y = 0; y < floor.length;y++) {
            for (int x = 0; x < floor[y].length;x++) {
                if (playerX == x && playerY == y) {
                    System.out.print("[P]");
                } else {
                    System.out.print("[" + floor[y][x] + "]");
                }
            }
            System.out.println();
        }
        System.out.println();
    }


}