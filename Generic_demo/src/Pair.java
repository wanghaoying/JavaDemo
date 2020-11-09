public class Pair<T>{
    private T first=null;
    private T second=null;

    public Pair(T fir,T sec){
        this.first=fir;
        this.second=sec;
    }
    public T getFirst(){
        return this.first;
    }
    public T getSecond(){
        return this.second;
    }
    public void setFirst(T fir){
        this.first=fir;
    }
}
