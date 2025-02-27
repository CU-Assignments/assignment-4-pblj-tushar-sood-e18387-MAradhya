import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Card {
    String symbol;
    String value;
    public Card(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " of " + symbol;
    }
}

public class CardCollection {
    private static Map<String, List> cardMap = new HashMap<>();
    public static void addCard(String symbol, String value) {
        cardMap.putIfAbsent(symbol, new ArrayList<>());
        cardMap.get(symbol).add(new Card(symbol, value));
        System.out.println(value + " of " + symbol + " added.");
    }

    public static void findCards(String symbol) {
        List<Card> cards = cardMap.get(symbol);
        if (cards != null && !cards.isEmpty()) {
            System.out.println("Cards with symbol " + symbol + ":");
            cards.forEach(System.out::println);
        } else {
            System.out.println("No cards found for symbol: " + symbol);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Card\n2. Find Cards by Symbol\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter card symbol (e.g., Hearts, Spades): ");
                    String symbol = sc.nextLine();
                    System.out.print("Enter card value (e.g., Ace, King): ");
                    String value = sc.nextLine();
                    addCard(symbol, value);
                }
                case 2 -> {
                    System.out.print("Enter symbol to find cards: ");
                    String symbol = sc.nextLine();
                    findCards(symbol);
                }
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}