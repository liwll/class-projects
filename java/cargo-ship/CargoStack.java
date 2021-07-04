import java.util.Stack;

/**
 * The data structure class for a CargoStack, which is a stack made of Cargoes
 * There is an attribute manyItems which keeps track of the size of the stack.
 *
 * @author
 * liwll
 */
public class CargoStack extends Stack{
    private int manyItems;

    /**
     * The default constructor for a CargoStack
     */
    public CargoStack(){}

    public void push(Cargo cargo) {
        super.push(cargo);
        manyItems++;
    }

    @Override
    public Cargo pop() {
        Cargo temp = peek();
        super.pop();
        manyItems--;
        return temp;
    }

    @Override
    public Cargo peek() {
            return (Cargo) super.peek();
    }

    @Override
    public int size() {
        return manyItems;
    }

    @Override
    public boolean isEmpty() {
        return super.empty();
    }

    public boolean validStrength(Cargo cargo) {
        if (isEmpty())
            return true;
        else {
            CargoStrength stackStrength = peek().getStrength();
            CargoStrength cargoStrength = cargo.getStrength();
            switch (stackStrength) {
                case STURDY:
                    return true;
                case MODERATE:
                    return cargoStrength != CargoStrength.STURDY;
                case FRAGILE:
                    return cargoStrength == CargoStrength.FRAGILE;
                default:
                    return false;
            }
        }
    }
}
