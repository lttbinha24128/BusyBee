package busybee.child;

public class ChildRow {

    private String name;
    private int age;
    private String note;

    public ChildRow(String name, int age, String note) {
        this.name = name;
        this.age = age;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getNote() {
        return note;
    }
}