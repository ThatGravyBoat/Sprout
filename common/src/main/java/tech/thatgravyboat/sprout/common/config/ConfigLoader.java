package tech.thatgravyboat.sprout.common.config;

import tech.thatgravyboat.sprout.common.config.annotations.Category;
import tech.thatgravyboat.sprout.common.config.annotations.Config;
import tech.thatgravyboat.sprout.common.config.annotations.Property;
import dev.architectury.injectables.annotations.ExpectPlatform;

import java.lang.reflect.Field;

public class ConfigLoader {

    public static void registerConfig(Object object) {
        BuiltCategory category;
        try {
            category = buildCategory(null, object);
        } catch (Exception e) {
            e.printStackTrace();
            category = null;
        }
        if (category instanceof BuiltConfig config) {
            registerConfig(config);
        } else {
            throw new IllegalArgumentException("Config supplied does not have a @Config annotation");
        }
    }

    @ExpectPlatform
    public static void registerConfig(BuiltConfig config) {
        throw new AssertionError();
    }

    public static BuiltCategory buildCategory(String categoryDesc, Object object) throws IllegalAccessException {
        Class<?> configClass = object.getClass();
        BuiltCategory category;
        if (configClass.isAnnotationPresent(Config.class)) {
            category = new BuiltConfig(configClass.getAnnotation(Config.class).value());
        } else if (configClass.isAnnotationPresent(Category.class)) {
            category = new BuiltCategory(categoryDesc, configClass.getAnnotation(Category.class).value());
        } else {
            throw new IllegalStateException("Config does not contain any @Config annotation or @Category");
        }

        for (Field field : configClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Property.class) && field.canAccess(object)) {
                PropertyType type = field.getAnnotation(Property.class).type();
                if (type.equals(PropertyType.CATEGORY)) {
                    category.categories.add(buildCategory(field.getAnnotation(Property.class).description(), field.get(object)));
                } else {
                    category.properties.add(new PropertyData(object, field, AnnotationData.getData(field)));
                }
            }
        }
        return category;
    }
}
