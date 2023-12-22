package com.cusmybatis.sqlsession;

import com.cusmybatis.config.Function;
import com.cusmybatis.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 用于读取xml文件，建立连接
 */
public class CusConfiguration {
    //属性-类的加载器
    private static ClassLoader loader = ClassLoader.getSystemClassLoader();
    //读取xml文件信息，并处理
    public Connection build(String resource) {
        Connection connection = null;
        try {
            //加载配置文件 cus_mybatis.xml,获取对应的InputStream流
            InputStream stream = loader.getResourceAsStream(resource);
            //解析cus_mybatis.xml -》 dom4j
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(stream);
            //获取到配置文件的根元素
            Element rootElement = document.getRootElement();
            //根据root解析，返回connection
             connection = evalDataSource(rootElement);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;

    }

    //解析root元素
    private Connection evalDataSource(Element root) {
        if(!"database".equals(root.getName())) {
            throw new RuntimeException("root节点应该是<database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        //遍历root下的子节点，获取属性值
        for (Object item: root.elements("property")) {
            Element i = (Element) item;
            String name = i.attributeValue("name");
            String value = i.attributeValue("value");
            if (name == null || value == null) {
                throw new RuntimeException("property没有设置name或value属性");
            }
            switch (name) {
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "driverClassName":
                    driverClassName = value;
                    break;
                default:
                    throw new RuntimeException("属性名没有匹配到...");
            }
        }

        Connection connection = null;
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  connection;
    }


    //读取xxxMapper.xml文件，创建MapperBean对象

    /**
     *
     * @param path 是xml的路径 + 文件名，是从类的加载路径计算，如果该xml文件在resources文件下，写文件名即可
     * @return
     */
    public MapperBean readMapper(String path){
        MapperBean mapperBean = new MapperBean();
        try {
            InputStream resourceAsStream = loader.getResourceAsStream(path);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            //获取namesapce
            String namespace = rootElement.attributeValue("namespace").trim();
            //设置mapperBean的接口名
            mapperBean.setInterfaceName(namespace);

            //得到root迭代器,可以遍历子元素，生成function
            Iterator rootIterator = rootElement.elementIterator();
            List<Function> list = new ArrayList<>();
            while (rootIterator.hasNext()) {

                Element e = (Element)rootIterator.next() ;
                Function function = new Function();
                String sqlType = e.getName().trim();
                String funcName = e.attributeValue("id").trim();
                String resultType = e.attributeValue("resultType").trim();
                String sql = e.getText();
                String parameterType = e.attributeValue("parameterType");





                function.setFuncName(funcName);
                function.setSql(sql);
                function.setParameterType(parameterType);
                function.setSqlType(sqlType);

                //function中的resultType 返回的是对象。resultType,是resultType的实例
                Class<?> aClass = Class.forName(resultType);
                Object instance = aClass.newInstance();
                function.setResultType(instance);
                //将封装好的function对象加入到集合中
                list.add(function);
            }
            //循环结束后
            mapperBean.setFunctions(list);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mapperBean;
    }
}
