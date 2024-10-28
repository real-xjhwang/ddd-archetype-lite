package com.xjhwang.types.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.experimental.UtilityClass;

import java.io.Writer;

/**
 * Xml 工具类
 *
 * @author 黄雪杰 on 2024-10-28 17:42
 */
@UtilityClass
public class XmlUtils {
    
    public static XStream getXStream() {
        
        return new XStream(new XppDriver() {
            
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                
                return new PrettyPrintWriter(out) {
                    
                    @Override
                    public void startNode(String name, Class clazz) {
                        
                        super.startNode(name, clazz);
                    }
                    
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        
                        if (!StringUtils.isNumeric(text)) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
    }
    
    public static String beanToXml(Object object) {
        
        XStream xStream = getXStream();
        xStream.alias("xml", object.getClass());
        xStream.processAnnotations(object.getClass());
        String xml = xStream.toXML(object);
        if (StringUtils.isNotEmpty(xml)) {
            return xml;
        } else {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T xmlToBean(String xml, Class<T> clazz) {
        
        XStream xStream = new XStream(new DomDriver());
        // XStream对象设置默认安全防护，同时设置允许的类
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[] {clazz});
        xStream.processAnnotations(clazz);
        xStream.setMode(XStream.NO_REFERENCES);
        xStream.alias("xml", clazz);
        return (T)xStream.fromXML(xml);
    }
}
