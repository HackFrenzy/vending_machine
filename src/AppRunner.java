import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;
import model.Product;
import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private CardAcceptor cardAcceptor;
    private CoinAcceptor coinAcceptor;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        coinAcceptor = new CoinAcceptor();
    }

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите способ оплаты: ");
        System.out.println("1. Карта");
        System.out.println("2. Монеты");
        int paymentMethod = scanner.nextInt();

        AppRunner app = new AppRunner();

        if (paymentMethod == 1) {
            app.performCardPayment();
        } else if (paymentMethod == 2) {
            app.performCoinPayment();
        } else {
            System.out.println("Неверный выбор способа оплаты.");
        }
    }

    public static void main(String[] args) {
        AppRunner.run();
    }
    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println(product.getActionLetter() + " - " + product.getName() + " (Цена: " + product.getPrice() + ")");
        }
    }

    private void startSimulation() {
        System.out.println("В автомате доступны:");
        showProducts(products);

        System.out.println("Монет на сумму: " + coinAcceptor.getAmount());


        UniversalArray<Product> allowProducts = getAllowedProducts();
        chooseAction(allowProducts);
    }

    private static void performCardPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер карты: ");
        String cardNumber = scanner.nextLine();

        System.out.println("Введите одноразовый пароль: ");
        String password = scanner.nextLine();

        // Логика оплаты картой
        // ...
    }

    private void performCoinPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите сумму в монетах: ");
        int amount = scanner.nextInt();

        coinAcceptor.setAmount(amount);

        System.out.println("Список доступных продуктов:");
        showProducts(products);
        UniversalArray<Product> allowedProducts = getAllowedProducts();
        chooseAction(allowedProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (coinAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private String fromConsole() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    private void chooseAction(UniversalArray<Product> products) {
        System.out.println(" a - Пополнить баланс");
        System.out.println(" h - Выйти");

        UniversalArray<Product> allowProducts = getAllowedProducts();

        if (allowProducts.size() == 0) {
            System.out.println("Нет доступных продуктов для покупки.");
            return;
        }

        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            System.out.println("Введите сумму для пополнения: ");
            int amount = Integer.parseInt(fromConsole());
            coinAcceptor.setAmount(coinAcceptor.getAmount() + amount);
            System.out.println("Вы пополнили баланс на " + amount);
            chooseAction(allowProducts);
            return;
        } else if ("h".equalsIgnoreCase(action)) {
            System.out.println("Вы вышли из программы.");
            return;
        }

        try {
            int paymentMethod;
            if (coinAcceptor.getAmount() > 0) {
                System.out.println("Выберите метод оплаты:");
                System.out.println("1. Карта");
                System.out.println("2. Монеты");
                paymentMethod = Integer.parseInt(fromConsole());
            } else {
                paymentMethod = 1; // Если нет средств в монетах, автомат использует оплату картой
            }

            for (int i = 0; i < allowProducts.size(); i++) {
                if (allowProducts.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    int productPrice = allowProducts.get(i).getPrice();
                    if (paymentMethod == 1) {
                        performCardPayment();
                    } else if (paymentMethod == 2) {
                        if (coinAcceptor.getAmount() >= productPrice) {
                            coinAcceptor.setAmount(coinAcceptor.getAmount() - productPrice);
                            System.out.println("Вы купили " + allowProducts.get(i).getName());
                            System.out.println("Спасибо за покупку");
                        } else {
                            System.out.println("Недостаточно средств для покупки.");
                        }
                    }
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный выбор действия.");
        }
    }
}