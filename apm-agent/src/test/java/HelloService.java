import com.jwcjlu.apm.annotation.Apm;

class HelloService{
    @Apm
    public void sayHello(){
        System.out.println("hello world");
    }

}
