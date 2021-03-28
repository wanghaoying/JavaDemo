package org.example.beanFilter;

import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Set;

public class ComponentScanFilter implements TypeFilter {
    /**
     *
     * @param metadataReader  包含了读取的目标类的元数据，即包含了那些接受包扫描的类的信息
     * @param metadataReaderFactory  一个获取其他类（父类或接口）的metadatReader的工厂
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取这个被扫描类上的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
//        System.out.println(annotationTypes);
        // 获取这个被扫描类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
//        System.out.println(classMetadata.getClassName());
        // 获取这个被扫描的类的资源信息
        Resource resource = metadataReader.getResource();
//        System.out.println(resource.getURL());

        if (classMetadata.getClassName().contains("er"))
            return true;

        return false;
    }
}
