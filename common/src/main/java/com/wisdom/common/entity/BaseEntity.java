/**
 * 
 */
package com.wisdom.common.entity;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * <p>
 * 功能描述：实体类基类<BR>
 * 功能说明：重载toString方法，可将各属性转为String输出，便于日志打印
 * </p>
 * 
 */
public class BaseEntity implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 2249325746951841661L;

//    /** 访问控制器,序列化的时候,不做处理 */
//    private transient ThreadLocal<BaseEntity> visitor = new ThreadLocal<BaseEntity>() {
//
//        @Override
//        protected BaseEntity initialValue() {
//            return null;
//        }
//
//    };

    /** 简单名称 */
    private String simpleName = getClass().getSimpleName();

    /**
     * 避免子类中相互引用引起输出toString时栈溢出
     *
     * @return String 返回字符串
     */
    @Override
    public String toString() {
        try {
            //PropertyDescriptor[] props = Introspector.getBeanInfo(getClass(), Object.class).getPropertyDescriptors();
            PropertyDescriptor[] props = BeanUtils.getPropertyDescriptors(getClass());
            Object[] params = new Object[0];

            Secret secretAnnotation;
            Object result;
            boolean isFirst = true;
            Method m;
            boolean accessible;

            StringBuilder strBuilder = new StringBuilder(512);
            strBuilder.append(getClass().getName()).append('{');
            for (PropertyDescriptor descriptor : props) {
                m = descriptor.getReadMethod();
                if (m == null) {
                    continue;
                }

                // 设置为允许访问
                accessible = m.isAccessible();
                if (!accessible) {
                    m.setAccessible(true);
                }

                try {
                    // 若为保密属性，不打印
                    secretAnnotation = m.getAnnotation(Secret.class);
                    if (secretAnnotation != null) {
                        continue;
                    }

                    result = m.invoke(this, params);

                    if (!isFirst) {
                        strBuilder.append(", ");
                    }
                    else {
                        isFirst = false;
                    }

                    strBuilder.append(descriptor.getName()).append(": ");

                    // 对是否为String分别区分
                    if (result instanceof String) {
                        strBuilder.append('"');
                        strBuilder.append(result);
                        strBuilder.append('"');
                    }
                    else {
                        strBuilder.append(result);
                    }
                }
                catch (Exception e) {
                    // e.printStackTrace();
                }

                if (!accessible) {
                    m.setAccessible(false);
                }
            }

            strBuilder.append('}');

            return strBuilder.toString();
        }
        catch (Exception e) {
        }

        return super.toString();
    }
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
//    @Override
//    public String toString() {
//        if (visitor.get() == null) {
//            visitor.set(this);
//            try {
//                return toString0();
//            }
//            finally {
//                visitor.set(null);
//            }
//        }
//        return simpleName + "@" + Integer.toHexString(hashCode());
//    }


}
