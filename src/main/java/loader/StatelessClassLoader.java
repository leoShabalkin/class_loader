package loader;

/**
 * Created by Leonid_Shabalkin on 21/10/2016.
 */
public class StatelessClassLoader extends CustomClassLoader {

    public StatelessClassLoader(String jarFileName, String packageName) {
        super(jarFileName, packageName);
    }

    @Override
    protected void addCache(Class<?> clazz) {
        if (clazz == null || clazz.getDeclaredFields().length > 0) {
            System.out.println("class %s NOT loaded in cache");
        } else {
            super.addCache(clazz);
            System.out.println(String.format("class %s loaded in cache", clazz.getName()));
        }
    }
}
