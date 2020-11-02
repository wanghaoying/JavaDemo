import org.junit.Test;

public class Generic <T>{
    private T t;

    public Generic() {
    }

    public T getT(){
        return t;
    }

    public void showT(T x){
        System.out.println(x);
    }

    @Test
    public void print(){
        Generic.printMsg("123,",123);
    }

    public static <T> void printMsg(T... args){
        for(T t : args){
            System.out.println("泛型测试 t is " + t);
        }
    }

}
