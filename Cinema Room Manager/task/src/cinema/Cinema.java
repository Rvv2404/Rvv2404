package cinema;

import java.util.Scanner;

class CinemaStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Введення кількості рядів і місць у кожному ряді
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();

        // Ініціалізація розташування місць
        char[][] cinema = new char[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                cinema[i][j] = 'S'; // Ініціалізуємо всі місця як 'S' (вільні)
            }
        }

        // Ініціалізація статистики
        int purchasedTickets = 0;
        int currentIncome = 0;
        int totalIncome = calculateTotalIncome(rows, seatsPerRow);

        // Цикл меню
        while (true) {
            System.out.println("\n1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();

            if (choice == 1) {
                // Показати розташування місць
                System.out.println("\nCinema:");
                printSeating(cinema);
            } else if (choice == 2) {
                // Купити квиток
                while (true) {
                    System.out.println("Enter a row number:");
                    int chosenRow = scanner.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    int chosenSeat = scanner.nextInt();

                    if (isInvalidSeat(chosenRow, chosenSeat, rows, seatsPerRow)) {
                        System.out.println("Wrong input!");
                    } else if (cinema[chosenRow - 1][chosenSeat - 1] == 'B') {
                        System.out.println("That ticket has already been purchased!");
                    } else {
                        // Обчислити вартість квитка
                        int ticketPrice = calculateTicketPrice(rows, seatsPerRow, chosenRow);

                        // Позначити місце як заброньоване
                        cinema[chosenRow - 1][chosenSeat - 1] = 'B';

                        // Оновити статистику
                        purchasedTickets++;
                        currentIncome += ticketPrice;

                        // Показати вартість квитка
                        System.out.println("Ticket price: $" + ticketPrice);
                        break;
                    }
                }
            } else if (choice == 3) {
                // Показати статистику
                double percentage = (double) purchasedTickets / (rows * seatsPerRow) * 100;
                System.out.printf("Number of purchased tickets: %d%n", purchasedTickets);
                System.out.printf("Percentage: %.2f%%%n", percentage);
                System.out.printf("Current income: $%d%n", currentIncome);
                System.out.printf("Total income: $%d%n", totalIncome);
            } else if (choice == 0) {
                // Завершення програми
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Допоміжний метод для відображення розташування місць
    private static void printSeating(char[][] cinema) {
        System.out.print("  ");
        for (int j = 1; j <= cinema[0].length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        for (int i = 0; i < cinema.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Допоміжний метод для обчислення вартості квитка
    private static int calculateTicketPrice(int rows, int seatsPerRow, int chosenRow) {
        int totalSeats = rows * seatsPerRow;
        if (totalSeats <= 60) {
            return 10;
        } else {
            int frontHalfRows = rows / 2;
            return chosenRow <= frontHalfRows ? 10 : 8;
        }
    }

    // Допоміжний метод для обчислення загального доходу
    private static int calculateTotalIncome(int rows, int seatsPerRow) {
        int totalSeats = rows * seatsPerRow;
        if (totalSeats <= 60) {
            return totalSeats * 10;
        } else {
            int frontHalfRows = rows / 2;
            int backHalfRows = rows - frontHalfRows;
            return frontHalfRows * seatsPerRow * 10 + backHalfRows * seatsPerRow * 8;
        }
    }

    // Допоміжний метод для перевірки чи місце є некоректним
    private static boolean isInvalidSeat(int row, int seat, int rows, int seatsPerRow) {
        return row < 1 || row > rows || seat < 1 || seat > seatsPerRow;
    }
}
