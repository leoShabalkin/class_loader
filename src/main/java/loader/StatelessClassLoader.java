package loader;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Leonid_Shabalkin on 21/10/2016.
 */
public class StatelessClassLoader extends ClassLoader {
    private HashMap<String, Class<?>> cache = new HashMap<>();
    private String jarFileName;
    private String packageName;

    public StatelessClassLoader(String jarFileName, String packageName) {
        this.jarFileName = jarFileName;
        this.packageName = packageName;
        cacheClasses();
    }

    private void cacheClasses() {
        try {
            JarFile jarFile = new JarFile(jarFileName);
            Collections.list(jarFile.entries()).forEach(j -> cacheClasses(j, jarFile));
        } catch (IOException ignore) {
            System.out.println("Warning : No jar file found");
        }
    }

    private void cacheClasses(JarEntry jarEntry, JarFile jarFile) {
        try {
            if (match(normalize(jarEntry.getName()), packageName)) {
                byte[] classData = loadClassData(jarFile, jarEntry);
                if (classData != null) {
                    Class<?> clazz = defineClass(stripClassName(normalize(jarEntry.getName())), classData, 0, classData.length);
                    if (clazz == null || clazz.getDeclaredFields().length > 0) {
                        System.out.println("class %s NOT loaded in cache");
                    } else {
                        cache.put(clazz.getName(), clazz);
                        System.out.println(String.format("class %s loaded in cache", clazz.getName()));
                    }
                }
            }
        } catch (IOException ignore) {
            System.out.println("Warning : No jar file found");
        }
    }

    public synchronized Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> result = cache.get(name);
        if (result == null) {
            result = cache.get(packageName + "." + name);
        }
        if (result == null) {
            result = super.findSystemClass(name);
        }
        System.out.println(String.format("loadClass(%s)", name));
        return result;
    }

    private String stripClassName(String className) {
        return className.substring(0, className.length() - 6);
    }

    private String normalize(String className) {
        return StringUtils.replace(className, "/", ".");
    }

    private boolean match(String className, String packageName) {
        return className.startsWith(packageName) && className.endsWith(".class");
    }

    private byte[] loadClassData(JarFile jarFile, JarEntry jarEntry) throws IOException {
        final long size = jarEntry.getSize();
        if (size == -1 || size == 0) {
            return null;
        }
        byte[] data = new byte[(int) size];
        InputStream in = jarFile.getInputStream(jarEntry);
        in.read(data);
        return data;
    }
}
