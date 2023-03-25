package exercise;

import java.lang.reflect.Field;
import java.text.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// BEGIN
class Validator {

    public static List<String> validate(Object obj) {

        List<String> result = new ArrayList<>();

        Class<?> aClass = obj.getClass();

        try {
            Field[] fields = aClass.getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);
                var annotationClass = field.isAnnotationPresent(NotNull.class);
                var value = field.get(obj);

                if (annotationClass && value == null) {
                    result.add(field.getName());
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Map<String, List<String>> advancedValidate(Object obj) {

        Map<String, List<String>> result = new HashMap<>();

        Class<?> aClass = obj.getClass();

        try {
            Field[] fields = aClass.getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);
                var value = field.get(obj);
                var notNullAnnotation = field.isAnnotationPresent(NotNull.class);
                var minLenAnnotation = field.isAnnotationPresent(MinLength.class);
                var fieldName = field.getName();

                var a = field.get

                if (minLenAnnotation && fieldName.length() <)

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;

    }
}
// END
