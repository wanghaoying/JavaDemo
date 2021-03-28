package org.example.importTest;

import org.example.service.CarService;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

public class MyImportSelector implements ImportSelector {
    /**
     * importingClassMetadata 中包含了被@Import修饰的类上的所有注解信息
     * @param importingClassMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        Set<String> annotationTypes = importingClassMetadata.getAnnotationTypes();
//        for (String annotationType : annotationTypes) {
//            System.out.println(annotationType);
//        }
        return new String[] {CarService.class.getName()};

//        return new String[0];
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return null;
    }
}
