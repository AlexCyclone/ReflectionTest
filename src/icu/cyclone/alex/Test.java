package icu.cyclone.alex;

import java.lang.reflect.*;

public class Test {
    public static void run() {
        tryGetClass();
        tryGetSuperclass();
        tryGetMofiers();
        tryGetDeclaredFields();
        tryGetFieldByName();
        tryGetFieldValue();
        trySetFieldValue();
        tryGetMethodsList();
        tryGetMethod();
        tryInvoke();
        tryGetConstructors();
        tryCreateObject();
    }

    public static void tryGetClass() {
        System.out.println("Get class:");

        FileWorker sc = new FileWorker("a.txt");

        Class<?> cl1 = FileWorker.class;
        Class<?> cl2 = sc.getClass();
        Class<?> cl3 = getClassByName("icu.cyclone.alex.FileWorker");

        System.out.println(cl1);
        System.out.println(cl2);
        System.out.println(cl3);
        System.out.println();
    }

    public static Class<?> getClassByName(String name) {
        Class<?> c = null;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    //---------------------------------------------------------------------

    public static void tryGetSuperclass() {
        System.out.println("Get superclass:");

        Class<?> cl = FileWorker.class;

        Class<?> superclass = cl.getSuperclass();

        System.out.println(superclass);
        System.out.println();
    }

    //---------------------------------------------------------------------

    public static void tryGetMofiers() {
        System.out.println("Get modifiers:");

        Class<?> cl = FileWorker.class;

        int mod = cl.getModifiers();
        System.out.println(Integer.toBinaryString(mod));
        System.out.println("Public class " + Modifier.isPublic(mod));
        System.out.println("Private class " + Modifier.isPrivate(mod));
        System.out.println("Interface " + Modifier.isInterface(mod));
        System.out.println("Abstarct class " + Modifier.isAbstract(mod));
        System.out.println();
    }

    //---------------------------------------------------------------------

    public static void tryGetDeclaredFields() {
        System.out.println("Get declared fields:");

        FileWorker fw = new FileWorker("a.txt");
        Class<?> cl = fw.getClass();

        Field[] fields = cl.getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field.getName() + " - " + field.getType().getName());
        }
        System.out.println();
    }

    //---------------------------------------------------------------------

    public static void tryGetFieldByName() {
        System.out.println("Get field by name:");

        Field fld = getFieldByName(FileWorker.class, "fileName");

        System.out.println(fld.getName() + " - " + fld.getType());
        System.out.println();
    }

    public static Field getFieldByName(Class<?> cl, String name) {
        Field field = null;
        try {
            field = cl.getDeclaredField(name);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        return field;
    }

    //---------------------------------------------------------------------

    public static void tryGetFieldValue() {
        System.out.println("Get field value by name:");

        FileWorker fw = new FileWorker("a.txt");
        Class<?> cl = fw.getClass();
        Field fld1 = getFieldByName(cl, "fileName");
        Field fld2 = getFieldByName(cl, "isByteFile");
        Field fld3 = getFieldByName(cl, "size");
        Field fld4 = getFieldByName(cl, "file");

        Object value1 = getFieldValue(fw, fld1);
        Object value2 = getFieldValue(fw, fld2);
        Object value3 = getFieldValue(fw, fld3);
        Object value4 = getFieldValue(fw, fld4);

        System.out.println(fld1.getName() + " - " + value1);
        System.out.println(fld2.getName() + " - " + value2);
        System.out.println(fld3.getName() + " - " + value3);
        System.out.println(fld4.getName() + " - " + value4);

        System.out.println();
    }

    public static Object getFieldValue(Object obj, Field fld) {
        Object value = null;
        try {
            fld.setAccessible(true);
            value = fld.get(obj);
            // For primitives exists methods getDouble(), getInt() etc.
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    //---------------------------------------------------------------------

    public static void trySetFieldValue() {

        System.out.println("Set field value:");

        FileWorker fw = new FileWorker("a.txt");
        Class<?> cl = fw.getClass();
        Field fld1 = getFieldByName(cl, "fileName");
        Field fld2 = getFieldByName(cl, "isByteFile");
        Field fld3 = getFieldByName(cl, "size");
        Field fld4 = getFieldByName(cl, "file");

        setFieldValue(fw, fld1, "b.txt");
        setFieldValue(fw, fld2, Boolean.valueOf(false));
        setFieldValue(fw, fld3, Long.valueOf(1042L));

        System.out.println(fld1.getName() + " - " + getFieldValue(fw, fld1));
        System.out.println(fld2.getName() + " - " + getFieldValue(fw, fld2));
        System.out.println(fld3.getName() + " - " + getFieldValue(fw, fld3));
        System.out.println(fld4.getName() + " - " + getFieldValue(fw, fld4));

        System.out.println();
    }

    public static void setFieldValue(Object obj, Field fld, Object value) {
        try {
            fld.setAccessible(true);
            fld.set(obj, value);
            // For primitives exists methods setDouble(), setInt() etc.
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------------------------------

    public static void tryGetMethodsList() {
        System.out.println("Get methods:");

        Class cl = FileWorker.class;

        Method[] methods = cl.getDeclaredMethods();

        for (Method m : methods) {
            printMethod(m);
        }
        System.out.println();
    }

    public static void printMethod(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append("method: ")
                .append(method.getReturnType().getCanonicalName()).append(' ')
                .append(method.getName()).append('(');
        for (Class param : method.getParameterTypes()) {
            sb.append(param.getName()).append(", ");
        }
        if (method.getParameterCount() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(')');
        System.out.println(sb);
    }

    //---------------------------------------------------------------------

    public static void tryGetMethod() {
        System.out.println("Get method:");

        Class cl = FileWorker.class;

        Method method = null;
        try {
            method = cl.getDeclaredMethod("getFileSize", String.class);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        printMethod(method);
        System.out.println();
    }

    //---------------------------------------------------------------------

    public static void tryInvoke() {
        System.out.println("Invoke method:");
        FileWorker fw = new FileWorker("a.txt");
        Class<?> cl = fw.getClass();
        Object result = null;

        try {
            Method method = cl.getDeclaredMethod("getFileSize", String.class);
            method.setAccessible(true);
            result = method.invoke(fw, "a.txt");
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("Result: " + result);
        System.out.println();
    }

    //---------------------------------------------------------------------

    public static void tryGetConstructors() {
        System.out.println("Get constructors:");

        Constructor[] constructors = FileWorker.class.getDeclaredConstructors();

        for (Constructor constructor : constructors) {
            printConstructor(constructor);
        }
        System.out.println();
    }

    public static void printConstructor(Constructor<?> constructor) {
        StringBuilder sb = new StringBuilder();
        sb.append("constructor: ").append(constructor.getName()).append('(');
        for (Class param : constructor.getParameterTypes()) {
            sb.append(param.getName()).append(", ");
        }
        if (constructor.getParameterCount() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(')');
        System.out.println(sb);
    }

    //---------------------------------------------------------------------

    public static void tryCreateObject() {
        System.out.println("Create object:");

        FileWorker fw = null;

        try {
            fw = (FileWorker) Class.forName("icu.cyclone.alex.FileWorker")
                    .getConstructor(String.class).newInstance("a.txt");
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(fw);
        System.out.println();
    }
}
