package com.test.transfer.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.SingleIterator;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: terwer
 * @date: 2021/12/12 21:19
 * @description: 工厂类，使用反射 生产对象
 */
public class BeanFactory {
    /**
     * 任务一：读取并解析xml，通过反射实例化对象并存储，用map结构存储
     * 任务二：对外提供根据ID获取对象的接口
     */
    private static Map<String, Object> map = new HashMap<>();

    static {
        // 任务一：读取并解析xml，通过反射实例化对象并存储，用map结构存储
        // 加载xml
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        // 解析xml
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();

            List<Element> beanList = rootElement.selectNodes("//bean");

            for (int i = 0; i < beanList.size(); i++) {
                Element element = beanList.get(i);
                // 通过bean元素，获取id和class
                String id = element.attributeValue("id");
                String clazz = element.attributeValue("class");

                // 通过反射实例化对象
                Class<?> aClass = Class.forName(clazz);
                Object o = aClass.newInstance();
                map.put(id, o);
            }

            // 维护对象的依赖关系
            // 找到有property子元素的bean
            List<Element> propertyList = rootElement.selectNodes("//property");
            for (int j = 0; j < propertyList.size(); j++) {
                Element element = propertyList.get(j);
                String name = element.attributeValue("name");
                String ref = element.attributeValue("ref");

                // 找到有property的bean
                Element parent = element.getParent();

                // 父元素反射赋值
                String parentId = parent.attributeValue("id");
                Object parentObject = map.get(parentId);

                Method[] methods = parentObject.getClass().getMethods();
                for (int i = 0; i < methods.length; i++) {
                    Method method = methods[i];
                    if (method.getName().equals("set" + name)) {
                        method.invoke(parentObject, map.get(ref));
                    }
                }

                // 重新赋值
                map.put(parentId, parentObject);
            }

        } catch (DocumentException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 任务二：对外提供根据ID获取对象的接口
     *
     * @param id
     * @return
     */
    public static Object getBean(String id) {
        return map.get(id);
    }
}
