import loader.StatelessClassLoader;
import sample.StatelessClass;

/**
 * Created by Leonid_Shabalkin on 24/10/2016.
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        StatelessClassLoader statelessClassLoader = new StatelessClassLoader("class_loader.jar", "sample.impl");
        Class<?> clazz = statelessClassLoader.loadClass("StatelessClassImpl");
        //Class<?> clazz = statelessClassLoader.loadClass("StatelessClassBadImpl");
        StatelessClass sample = (StatelessClass) clazz.newInstance();
        sample.print("Hello");
    }
}
