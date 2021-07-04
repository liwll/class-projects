import java.util.InputMismatchException;
import java.util.Scanner;

public class ShipLoader {
    private CargoStack dock;

    public ShipLoader() {
        dock = new CargoStack();
    }

    public static void main(String[] args) {
        ShipLoader loader = new ShipLoader();
        System.out.println("Welcome to ShipLoader!\n\n"
                + "Cargo Ship Parameters\n"
                + "--------------------------------------------------");
        CargoShip ship = loader.createShip();
        System.out.printf("\n%s\n%s\n%s\n", "Cargo ship created.",
                "Pulling ship in to dock...",
                "Cargo ship ready to be loaded.");
        loader.menu(ship);
    }

    public CargoShip createShip() {
        try {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Number of stacks: ");
            int numStacks = userIn.nextInt();
            System.out.print("Maximum height of stacks: ");
            int maxHeight = userIn.nextInt();
            System.out.print("Maximum total cargo weight: ");
            int maxWeight = userIn.nextInt();
            return new CargoShip(numStacks, maxHeight, maxWeight);
        }
        catch(IllegalArgumentException e) {
            System.out.println("\nInvalid input, please try again.\n");
            return createShip();
        }
        catch(InputMismatchException e) {
            System.out.println("\nInput mismatch, please try again.\n");
            return createShip();
        }
    }

    public void menu(CargoShip ship) {
        String optionC = "C) Create new cargo";
        String optionL = "L) Load cargo from dock";
        String optionU = "U) Unload cargo from ship";
        String optionM = "M) Move cargo between stacks";
        String optionK = "K) Clear dock";
        String optionP = "P) Print ship stacks";
        String optionS = "S) Search for cargo";
        String optionQ = "Q) Quit";

        System.out.printf("\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n\n",
                "Please select an option: ", optionC, optionL, optionU,
                optionM, optionK, optionP, optionS, optionQ);
        Scanner userIn = new Scanner(System.in);
        System.out.print("Select a menu option: ");
        String userOption = userIn.nextLine().toUpperCase();

        try {
            switch (userOption) {
                case "C":
                    menuCreateCargo(ship);
                    break;
                case "L":
                    menuLoadShip(ship);
                    break;
                case "U":
                    menuUnloadShip(ship);
                    break;
                case "M":
                    menuMoveCargo(ship);
                    break;
                case "K":
                    menuClearDock(ship);
                    break;
                case "P":
                    menuPrintShipStacks(ship);
                    break;
                case "S":
                    menuSearchCargo(ship);
                    break;
                case "Q":
                    System.out.println("Program terminating normally...");
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("\nInvalid input, please try again.");
            menu(ship);
        }
        catch(InputMismatchException e) {
            System.out.println("\nInput mismatch, please try again.");
            menu(ship);
        }
    }

    public void menuCreateCargo(CargoShip ship) {
        try {
            Scanner userIn = new Scanner(System.in);
            System.out.print("\nEnter the name of the cargo: ");
            String name = userIn.nextLine();
            System.out.print("Enter the weight of the cargo: ");
            double weight = userIn.nextDouble();
            userIn.nextLine();
            System.out.print("Enter the container strength (F/M/S): ");
            CargoStrength strength;
            switch (userIn.nextLine().toLowerCase()) {
                case "f":
                    strength = CargoStrength.FRAGILE;
                    break;
                case "m":
                    strength = CargoStrength.MODERATE;
                    break;
                case "s":
                    strength = CargoStrength.STURDY;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            Cargo cargo = new Cargo(name, weight, strength);
            if (!dock.validStrength(cargo))
                throw new CargoStrengthException();
            else {
                dock.push(cargo);
                System.out.println("\nCargo '" + name +
                        "' pushed onto the dock.\n");
                printShipStacks(ship);
            }
        }
        catch(CargoStrengthException e) {
            System.out.println("\nOperation failed! " +
                    "Cargo at top of stack cannot support weight.");
        }
        menu(ship);
    }

    public void menuLoadShip(CargoShip ship) {
        try {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Select the load destination stack index: ");
            int destination = userIn.nextInt();
            String name = dock.peek().getName();
            Cargo cargo = dock.peek();
            ship.pushCargo(cargo, destination);
            dock.pop();
            System.out.println("\nCargo '" + name
                    + "' moved from dock to stack " + destination + "\n");
            printShipStacks(ship);
        }
        catch(FullStackException e) {
            System.out.println("\nOperation failed! " +
                    "Cargo stack is at maximum height.");
        }
        catch(ShipOverweightException e) {
            System.out.println("\nOperation failed! " +
                    "Cargo would put ship overweight.");
        }
        catch(CargoStrengthException e) {
            System.out.println("\nOperation failed! " +
                    "Cargo at top of stack cannot support weight.");
        }
        catch(java.util.EmptyStackException e) {
            System.out.println("\nOperation failed! " +
                    "dock stack is empty.");
        }
        menu(ship);
    }

    public void menuUnloadShip(CargoShip ship) {
        try {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Select the unload source stack index: ");
            int index = userIn.nextInt();
            if (index < 1 || index > ship.getNumStacks())
                throw new IllegalArgumentException();
            Cargo cargo = ship.getStacks()[index - 1].peek();
            if (!dock.validStrength(cargo))
                throw new CargoStrengthException();
            else {
                System.out.println("Cargo moved from stack " +
                        index + " to dock.\n");
                dock.push(ship.popCargo(index));
                printShipStacks(ship);
            }
        }
        catch(java.util.EmptyStackException e) {
            System.out.println("\nOperation failed! " +
                    "Cargo stack is empty.");
        }
        catch (EmptyStackException e) {
            System.out.println("\nOperation failed! " +
                    "Cargo stack is empty.");
        }
        catch(CargoStrengthException e) {
            System.out.println("\nOperation failed! " +
                    "Cargo at top of dock cannot support weight.");
        }
        menu(ship);
    }

    public void menuMoveCargo(CargoShip ship) {
        double origMaxWeight = ship.getMaxWeight();
        try {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Source stack index: ");
            int source = userIn.nextInt();
            System.out.print("Destination stack index: ");
            int destination = userIn.nextInt();
            if (source < 1 || source > ship.getNumStacks())
                throw new IllegalArgumentException();
            Cargo cargo = ship.getStacks()[source - 1].peek();
            ship.setMaxWeight(ship.getMaxWeight() + cargo.getWeight());
            ship.pushCargo(cargo, destination);
            ship.popCargo(source);
            ship.setMaxWeight(origMaxWeight);
            System.out.println("Cargo moved from stack " + source +
                    " to stack " + destination + ".\n");
            printShipStacks(ship);
        }
        catch(IllegalArgumentException e) {
            ship.setMaxWeight(origMaxWeight);
            System.out.println("\nInvalid input, please try again.");
        }
        catch(FullStackException e) {
            ship.setMaxWeight(origMaxWeight);
            System.out.println("\nOperation failed! " +
                    "Cargo stack is at maximum height.");
        }
        catch(ShipOverweightException e) {
            ship.setMaxWeight(origMaxWeight);
            System.out.println("\nOperation failed! " +
                    "Cargo would put ship overweight.");
        }
        catch(CargoStrengthException e) {
            ship.setMaxWeight(origMaxWeight);
            System.out.println("\nOperation failed! " +
                    "Cargo at top of stack cannot support weight.");
        }
        catch(java.util.EmptyStackException e) {
            ship.setMaxWeight(origMaxWeight);
            System.out.println("\nOperation failed! " +
                    "Cargo stack is empty.");
        }
        catch(EmptyStackException e) {
            ship.setMaxWeight(origMaxWeight);
            System.out.println("\nOperation failed! " +
                    "Cargo stack is empty.");
        }
        menu(ship);
    }

    public void menuClearDock(CargoShip ship) {
        while (!dock.isEmpty()) {
            dock.pop();
        }
        System.out.println("Dock cleared.");
        menu(ship);
    }

    public void menuPrintShipStacks(CargoShip ship) {
        printShipStacks(ship);
        menu(ship);
    }

    public void menuSearchCargo(CargoShip ship) {
        Scanner userIn = new Scanner(System.in);
        System.out.print("Enter the name of the cargo: ");
        String name = userIn.nextLine();
        ship.findAndPrint(name);
        menu(ship);
    }

    public void printStacks(CargoShip ship) {
        int numStacks = ship.getNumStacks();
        int maxSize = 0;
        for (int i = 0; i < numStacks; i++) {
            if (ship.getStacks()[i].size() >= maxSize)
                maxSize = ship.getStacks()[i].size();
        }

        CargoStack[] cargoStacks = new CargoStack[numStacks];
        for (int i = 0; i < cargoStacks.length; i++) {
            cargoStacks[i] = new CargoStack();
        }
        String[] stackLines = new String[maxSize];
        for (int i = 0; i <stackLines.length; i++) {
            stackLines[i] = new String();
        }

        String strength;
        for (int i = stackLines.length - 1; i >= 0; i--) {
            for (int j = 0; j < numStacks; j++) {
                if (!(ship.getStacks()[j].size() - 1 == i))
                    stackLines[i] += String.format("%6s", " ");
                else {
                    switch (ship.getStacks()[j].peek().getStrength()) {
                        case FRAGILE:
                            strength = "F";
                            break;
                        case MODERATE:
                            strength = "M";
                            break;
                        case STURDY:
                            strength = "S";
                            break;
                        default:
                            strength = "0";
                            break;
                    }
                    stackLines[i] += String.format("%6s", strength);
                    cargoStacks[j].push(ship.getStacks()[j].pop());
                }
            }
        }

        for (int i = 0; i < numStacks; i++) {
            int orig = cargoStacks[i].size();
            for (int j = 0; j < orig; j++) {
                ship.getStacks()[i].push(cargoStacks[i].pop());
            }
        }

        for (int i = stackLines.length - 1; i >= 0; i--) {
            System.out.println(stackLines[i]);
        }
    }


    public void printShipStacks(CargoShip ship) {
        int numStacks = ship.getNumStacks();

        String shipDisplay = "\\=|";
        for (int i = 0; i < numStacks; i++) {
            shipDisplay += "=====|";
        }
        shipDisplay += "=/\n \\";
        for (int i = 0; i < numStacks; i++) {
            shipDisplay += "   " + String.format("%-3d", (i + 1));
        }
        shipDisplay += " /\n  \\";
        for (int i = 0; i < numStacks; i++) {
            shipDisplay += "-----";
        }
        for (int i = 0; i < numStacks - 1; i++) {
            shipDisplay += "-";
        }
        shipDisplay += "/";

        String dockDisplay = "";
        String strength;
        CargoStack tempStack = new CargoStack();
        int origSize = dock.size();
        for (int i = 0 ; i < origSize; i++) {
            switch (dock.peek().getStrength()) {
                case FRAGILE:
                    strength = "F";
                    break;
                case MODERATE:
                    strength = "M";
                    break;
                case STURDY:
                    strength = "S";
                    break;
                default:
                    strength = "0";
                    break;
            }
            dockDisplay += String.format("%10s\n", strength);
            tempStack.push(dock.pop());
        }

        for (int i = 0; i < origSize; i++) {
            dock.push(tempStack.pop());
        }

        dockDisplay += String.format("%s\n%13s\n%13s", "Dock: |=====|",
                "|=====|", "|=====|");

        String weightDisplay = String.format("%s%.0f%s%.0f", "Total Weight = ",
                ship.getTotalWeight(), " / ", ship.getMaxWeight());

        printStacks(ship);
        System.out.println(shipDisplay + "\n");
        System.out.println(weightDisplay);
        System.out.print(dockDisplay);
    }

}
