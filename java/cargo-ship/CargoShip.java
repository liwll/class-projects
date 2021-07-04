public class CargoShip {
    private CargoStack[] stacks;
    private int numStacks;
    private int maxHeight;
    private double maxWeight;
    private double totalWeight;

    public CargoShip(int numStacks, int initMaxHeight,
                     double initMaxWeight) throws IllegalArgumentException {
        if (numStacks <= 0 || initMaxHeight <= 0 || initMaxWeight <= 0) {
            throw new IllegalArgumentException();
        }
        else {
            this.numStacks = numStacks;
            maxHeight = initMaxHeight;
            maxWeight = initMaxWeight;
            stacks = new CargoStack[numStacks];
            for (int i = 0; i < stacks.length; i++) {
                stacks[i] = new CargoStack();
            }
            totalWeight = 0;
        }
    }

    public void pushCargo(Cargo cargo, int stack)
            throws IllegalArgumentException, FullStackException,
            ShipOverweightException, CargoStrengthException {
        if (cargo == null || stack < 1 || stack > stacks.length)
            throw new IllegalArgumentException();
        if (stacks[stack - 1].size() >= maxHeight)
            throw new FullStackException();
        if (totalWeight + cargo.getWeight() > maxWeight)
            throw new ShipOverweightException();
        if (!stacks[stack-1].validStrength(cargo))
            throw new CargoStrengthException();
        else {
            stacks[stack - 1].push(cargo);
            totalWeight += cargo.getWeight();
        }
    }

    public Cargo popCargo(int stack) throws IllegalArgumentException,
            EmptyStackException {
        if (stack < 1 || stack > stacks.length)
            throw new IllegalArgumentException();
        if (stacks[stack - 1].size() == 0)
            throw new EmptyStackException();
        else {
            totalWeight -= stacks[stack - 1].peek().getWeight();
            return stacks[stack - 1].pop();
        }
    }

    public void findAndPrint(String name) {
        int numFound = 0;
        double totalWeight = 0;
        String table = " Stack   Depth   Weight   Strength\n" +
                "=======+=======+========+==========\n";
        CargoStack[] tempStore = new CargoStack[numStacks];
        for (int i = 0; i < tempStore.length; i++) {
            tempStore[i] = new CargoStack();
        }
        for (int i = 0; i < numStacks; i++) {
            int origSize = stacks[i].size();
            Cargo cargo;
            for (int j = 0; j < origSize; j++) {
                cargo = stacks[i].peek();
                System.out.println(cargo.getName());
                if (cargo.getName().equals(name)) {
                    numFound++;
                    totalWeight += cargo.getWeight();
                    table += String.format("%-7d|%-7d|%-8.0f|%-10s\n",
                            (i+1), j, cargo.getWeight(), cargo.getStrength());
                }
                tempStore[i].push(stacks[i].pop());
            }
        }

        for (int i = 0; i < numStacks; i++) {
            int origSize = tempStore[i].size();
            for (int j = 0; j < origSize; j++) {
                stacks[i].push(tempStore[i].pop());
            }
        }

        if (numFound > 0) {
            table += String.format("\n%s%d\n%s%.0f\n", "Total Count: ",
                    numFound, "Total Weight: ", totalWeight);
            System.out.println(table);
        }
        else
            System.out.println("Cargo " + name +
                    " could not be found on the ship.");
    }

    public CargoStack[] getStacks() {
        return stacks;
    }

    public int getNumStacks() {
        return numStacks;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }
}
