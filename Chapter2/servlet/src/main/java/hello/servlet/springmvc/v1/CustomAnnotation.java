package hello.servlet.springmvc.v1;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomAnnotation {
    String test();
}

class CustomAnnotationUse{

    @CustomAnnotation(test="test custom annotation`s attribute")
    public void test() {
        System.out.println("test custom annotation");
    }

}
