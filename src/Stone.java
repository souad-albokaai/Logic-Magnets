public class Stone {
    private String color;
    private String type;

    public Stone(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return color + " " + type;
    }
}