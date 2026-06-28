import Controller.Controller;

public class Main {
    public static void main(String[] args) {
        try{
            Controller controller = new Controller();
            controller.run();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
