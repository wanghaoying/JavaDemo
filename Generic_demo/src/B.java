import java.util.ArrayList;

public class B extends A<String> {
    @Override
    public void setValue(String value) {
        System.out.println("---B.setValue()---");
    }

    public static void main(String[] args) {
        B b = new B();
        ArrayList arrayList = new ArrayList<String>();
        b.setValue("123");
    }
}