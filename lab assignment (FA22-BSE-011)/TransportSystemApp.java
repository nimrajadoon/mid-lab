
import java.util.ArrayList;
import java.util.List;

// Presentation Layer
public class TransportSystemApp {
    public static void main(String[] args) {
        TransportService transportService = new TransportService();
        User user = new User("John Doe");

        transportService.addObserver(user);

        System.out.println("Booking transport...");
        TransportRequest request = new TransportRequest(user, "Ride to City Center", 15.0); // Example trip
        transportService.bookTransport(request);
    }
}

// Observer Pattern - Observer Interface
interface Observer {
    void update(String message);
}

// User class implementing Observer
class User implements Observer {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for " + name + ": " + message);
    }

    public String getName() {
        return name;
    }
}

// Business Logic Layer
class TransportService {
    private TransportPipe transportPipe = new TransportPipe();
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void bookTransport(TransportRequest request) {
        System.out.println("Processing transport request...");
        transportPipe.process(request);

        // Notify observers (e.g., user) about booking
        notifyObservers("Your transport request for " + request.getDescription() + " has been confirmed.");
    }
}

// Pipe and Filter Pattern - Transport Pipe
class TransportPipe {
    private List<TransportFilter> filters = new ArrayList<>();

    public TransportPipe() {
        filters.add(new FlexibilityFilter());
        filters.add(new PaymentFilter());
    }

    public void process(TransportRequest request) {
        for (TransportFilter filter : filters) {
            if (!filter.apply(request)) {
                return; // Stop processing if any filter fails
            }
        }
        System.out.println("Transport request processed successfully.");
    }
}

// Pipe and Filter Pattern - TransportFilter Interface
interface TransportFilter {
    boolean apply(TransportRequest request);
}

// Filter: Flexibility (Customizable Use)
class FlexibilityFilter implements TransportFilter {
    @Override
    public boolean apply(TransportRequest request) {
        System.out.println("Checking flexibility...");
        // Example: Check if the user is eligible for flexible use (mock logic)
        if (request.getDescription().contains("City Center")) {
            System.out.println("Flexibility approved for: " + request.getDescription());
            return true;
        }
        System.out.println("Flexibility denied.");
        return false;
    }
}

// Filter: Payment (Pay as You Use)
class PaymentFilter implements TransportFilter {
    @Override
    public boolean apply(TransportRequest request) {
        System.out.println("Processing payment for " + request.getCost() + " USD...");
        // Mock payment logic
        System.out.println("Payment successful!");
        return true;
    }
}

// Data Access Layer
class TransportRequest {
    private User user;
    private String description;
    private double cost;

    public TransportRequest(User user, String description, double cost) {
        this.user = user;
        this.description = description;
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }
}


