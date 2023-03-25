package exercise;

import java.lang.reflect.Field;
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
        List<Field> fields = List.of(obj.getClass().getDeclaredFields());
        Map<String, List<String>> result = new HashMap<>();

        fields.stream()
                .filter(field -> field.isAnnotationPresent(NotNull.class) || field.isAnnotationPresent(MinLength.class))
                .forEach(field -> {
                    String fieldName = field.getName();
                    List<String> errors = getErrorsForField(field, obj);
                    if (!errors.isEmpty()) {
                        result.put(fieldName, errors);
                    }
                });

        return result;
    }

    public static List<String> getErrorsForField(Field field, Object obj) {
        List<String> errors = new ArrayList<>();
        String value;

        try {
            field.setAccessible(true);
            value = (String) field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (field.isAnnotationPresent(NotNull.class) && value == null) {
            errors.add("can not be null");
        }

        if (field.isAnnotationPresent(MinLength.class)) {
            int minLength = field.getAnnotation(MinLength.class).minLength();
            if (value == null || value.length() < minLength) {
                errors.add("length less than " + minLength);
            }
        }
        return errors;
    }
}
// END
