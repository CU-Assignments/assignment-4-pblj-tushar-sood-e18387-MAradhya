import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

class TicketBookingSystem {
    private final boolean[] seats = new boolean[10]; // 10 seats
    private final ReentrantLock lock = new ReentrantLock();

    public void bookSeat(int seatNumber, String customer) {
        lock.lock();
        try {
            if (!seats[seatNumber]) {
                seats[seatNumber] = true;
                System.out.println(customer + " successfully booked seat " + seatNumber);
            } else {
                System.out.println(seatNumber + " is already booked. " + customer + " couldn’t book it.");
            }
        } finally {
            lock.unlock();
        }
    }
}

class BookingThread extends Thread {
    private final TicketBookingSystem system;
    private final int seatNumber;

    public BookingThread(TicketBookingSystem system, int seatNumber, String customer, int priority) {
        super(customer);
        this.system = system;
        this.seatNumber = seatNumber;
        setPriority(priority);
    }

    @Override
    public void run() {
        system.bookSeat(seatNumber, getName());
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        TicketBookingSystem system = new TicketBookingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Book Seat (VIP)");
            System.out.println("2. Book Seat (Regular)");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 3) {
                System.out.println("Exiting...");
                break;
            }

            System.out.print("Enter seat number (0-9): ");
            int seatNumber = scanner.nextInt();

            if (seatNumber < 0 || seatNumber >= 10) {
                System.out.println("Invalid seat number. Please try again.");
                continue;
            }

            System.out.print("Enter customer name: ");
            String customer = scanner.next();

            int priority = (choice == 1) ? Thread.MAX_PRIORITY : Thread.NORM_PRIORITY;
            Thread bookingThread = new BookingThread(system, seatNumber, customer, priority);
            bookingThread.start();
        }

        scanner.close();
    }
}