package com.solvd.financialinstitution.parsers.sax;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class FinancialNetworkHandler extends DefaultHandler {
    private static final String MODEL_PKG = "com.solvd.financialinstitution.model.";
    private final StringBuilder text = new StringBuilder();
    private final Deque<Element> stack = new ArrayDeque<>();
    private Object root;

    public com.solvd.financialinstitution.model.FinancialNetwork getRoot() {
        if (root instanceof com.solvd.financialinstitution.model.FinancialNetwork r) return r;
        return null;
    }

    private static final class Element {
        final String name;
        final Object obj;

        Element(String n, Object o) {
            this.name = n;
            this.obj = o;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        text.setLength(0);
        Class<?> cls = resolveClass(qName);
        if (cls != null && !cls.isEnum()) {
            Object obj = newInstance(cls);
            if (obj != null) {
                for (int i = 0; i < attrs.getLength(); i++) {
                    String an = attrs.getQName(i);
                    String av = attrs.getValue(i);
                    setScalar(obj, an, av);
                }
                if (stack.isEmpty()) root = obj;
                else {
                    Object parent = nearestParent();
                    attachChild(parent, obj);
                }
                stack.push(new Element(qName, obj));
                return;
            }
        }
        stack.push(new Element(qName, null));
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        text.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String value = text.toString().trim();
        stack.pop();
        if (!value.isEmpty()) {
            Object parent = nearestParent();
            if (parent != null) {
                Class<?> enumCls = resolveClass(qName);
                if (enumCls != null && enumCls.isEnum()) {
                    setEnum(parent, qName, enumCls, value);
                } else {
                    setScalar(parent, qName, value);
                }
            }
        }
    }

    private Object nearestParent() {
        for (Element e : stack) if (e.obj != null) return e.obj;
        return null;
    }

    private Class<?> resolveClass(String qName) {
        String clsName = toUpperCamel(qName);
        try {
            return Class.forName(MODEL_PKG + clsName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static String toUpperCamel(String s) {
        StringBuilder b = new StringBuilder();
        boolean up = true;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '-' || c == '_') {
                up = true;
                continue;
            }
            if (up) {
                b.append(Character.toUpperCase(c));
                up = false;
            } else b.append(c);
        }
        return b.toString();
    }

    private Object newInstance(Class<?> cls) {
        try {
            return cls.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    private void attachChild(Object parent, Object child) {
        if (parent == null || child == null) return;
        if (attachToListField(parent, child)) return;
        if (attachToSingleField(parent, child)) return;
        attachBySetter(parent, child);
    }

    private boolean attachToListField(Object parent, Object child) {
        Field[] fs = parent.getClass().getDeclaredFields();
        for (Field f : fs) {
            if (!List.class.isAssignableFrom(f.getType())) continue;
            Type gt = f.getGenericType();
            if (!(gt instanceof ParameterizedType p)) continue;
            Type[] args = p.getActualTypeArguments();
            if (args.length != 1) continue;
            if (!(args[0] instanceof Class<?> ec)) continue;
            if (!ec.isAssignableFrom(child.getClass())) continue;
            try {
                f.setAccessible(true);
                Object listObj = f.get(parent);
                if (listObj == null) {
                    listObj = new ArrayList<>();
                    f.set(parent, listObj);
                }
                @SuppressWarnings("unchecked")
                List<Object> list = (List<Object>) listObj;
                list.add(child);
                return true;
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    private boolean attachToSingleField(Object parent, Object child) {
        Field[] fs = parent.getClass().getDeclaredFields();
        for (Field f : fs) {
            if (!f.getType().isAssignableFrom(child.getClass())) continue;
            try {
                f.setAccessible(true);
                Object cur = f.get(parent);
                if (cur == null) {
                    f.set(parent, child);
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    private void attachBySetter(Object parent, Object child) {
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(parent.getClass()).getPropertyDescriptors()) {
                Method w = pd.getWriteMethod();
                if (w == null) continue;
                Class<?> pt = pd.getPropertyType();
                if (pt.isAssignableFrom(child.getClass())) {
                    w.invoke(parent, child);
                    return;
                }
                if (List.class.isAssignableFrom(pt)) {
                    Field f = findField(parent.getClass(), pd.getName());
                    if (f == null) continue;
                    Type gt = f.getGenericType();
                    if (!(gt instanceof ParameterizedType p)) continue;
                    Type[] args = p.getActualTypeArguments();
                    if (args.length != 1) continue;
                    if (!(args[0] instanceof Class<?> ec)) continue;
                    if (!ec.isAssignableFrom(child.getClass())) continue;
                    Object listObj = pd.getReadMethod() != null ? pd.getReadMethod().invoke(parent) : null;
                    if (listObj == null) {
                        listObj = new ArrayList<>();
                        w.invoke(parent, listObj);
                    }
                    @SuppressWarnings("unchecked")
                    List<Object> list = (List<Object>) listObj;
                    list.add(child);
                    return;
                }
            }
        } catch (Exception ignored) {
        }
    }

    private Field findField(Class<?> cls, String name) {
        Class<?> c = cls;
        while (c != null) {
            try {
                Field f = c.getDeclaredField(name);
                f.setAccessible(true);
                return f;
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            }
        }
        return null;
    }

    private void setEnum(Object target, String prop, Class<?> enumCls, String value) {
        Object enumVal;
        try {
            enumVal = java.lang.Enum.valueOf((Class) enumCls, value);
        } catch (IllegalArgumentException ex) {
            enumVal = java.lang.Enum.valueOf((Class) enumCls, value.toUpperCase());
        }
        if (!setByPropertyOrField(target, prop, enumVal, enumCls) && prop.endsWith("Type")) {
            setByPropertyOrField(target, "type", enumVal, enumCls);
        }
    }

    private void setScalar(Object target, String prop, String value) {
        try {
            PropertyDescriptor pd = getProperty(target.getClass(), prop);
            if (pd != null && pd.getWriteMethod() != null) {
                Class<?> t = pd.getPropertyType();
                Object v = convert(value, t);
                if (v != null) {
                    pd.getWriteMethod().invoke(target, v);
                    return;
                }
            }
            Field f = findField(target.getClass(), prop);
            if (f != null) {
                Object v = convert(value, f.getType());
                if (v != null) {
                    f.set(target, v);
                    return;
                }
            }
            if (prop.endsWith("Type")) {
                setScalar(target, "type", value);
            } else if (prop.endsWith("Name")) {
                setScalar(target, "name", value);
            }
        } catch (Exception ignored) {
        }
    }

    private PropertyDescriptor getProperty(Class<?> cls, String name) {
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(cls).getPropertyDescriptors()) {
                if (pd.getName().equals(name)) return pd;
            }
        } catch (IntrospectionException ignored) {
        }
        return null;
    }

    private Object convert(String value, Class<?> type) {
        if (type == String.class) return value;
        if (type == int.class || type == Integer.class) return value.isEmpty() ? 0 : Integer.parseInt(value);
        if (type == long.class || type == Long.class) return value.isEmpty() ? 0L : Long.parseLong(value);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        if (type == double.class || type == Double.class) return value.isEmpty() ? 0d : Double.parseDouble(value);
        if (type == BigDecimal.class) return value.isEmpty() ? BigDecimal.ZERO : new BigDecimal(value);
        if (type == LocalDate.class) return LocalDate.parse(value);
        if (type == LocalDateTime.class) return LocalDateTime.parse(value);
        if (type.isEnum()) {
            try {
                return java.lang.Enum.valueOf((Class) type, value);
            } catch (IllegalArgumentException ex) {
                return java.lang.Enum.valueOf((Class) type, value.toUpperCase());
            }
        }
        return null;
    }

    private boolean setByPropertyOrField(Object target, String prop, Object v, Class<?> t) {
        try {
            PropertyDescriptor pd = getProperty(target.getClass(), prop);
            if (pd != null && pd.getWriteMethod() != null && (t == null || pd.getPropertyType().isAssignableFrom(t))) {
                pd.getWriteMethod().invoke(target, v);
                return true;
            }
            Field f = findField(target.getClass(), prop);
            if (f != null && (t == null || f.getType().isAssignableFrom(t))) {
                f.set(target, v);
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}
