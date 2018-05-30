package com.wisdom.beauty.util;

import org.springframework.asm.*;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * FileName: LogInterceptor
 *
 * @author: 赵得良
 * Date:     2018/5/30 0030 17:55
 * Description: 参数获取工具
 */
public abstract class ParameterNameUtils {

    public static String[] getMethodParamNames(Class<?> clazz, final Method method) throws Exception {

        try {

            final String[] paramNames = new String[method.getParameterTypes().length];
            String className = clazz.getName();

            int lastDotIndex = className.lastIndexOf(".");
            className = className.substring(lastDotIndex + 1) + ".class";
            InputStream is = clazz.getResourceAsStream(className);

            final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassReader cr = new ClassReader(is);

            cr.accept(new ClassVisitor(Opcodes.ASM4, cw) {
                @Override
                public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
                    final Type[] args = Type.getArgumentTypes(desc);
                    // 方法名相同并且参数个数相同
                    if (!name.equals(method.getName()) || !sameType(args, method.getParameterTypes())) {
                        return super.visitMethod(access, name, desc, signature, exceptions);
                    }
                    MethodVisitor v = cv.visitMethod(access, name, desc, signature, exceptions);
                    return new MethodVisitor(Opcodes.ASM4, v) {
                        @Override
                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                            int i = index - 1;
                            // 如果是静态方法，则第一就是参数
                            // 如果不是静态方法，则第一个是"this"，然后才是方法的参数
                            if (Modifier.isStatic(method.getModifiers())) {
                                i = index;
                            }
                            if (i >= 0 && i < paramNames.length) {
                                paramNames[i] = name;
                            }
                            super.visitLocalVariable(name, desc, signature, start, end, index);
                        }

                    };
                }
            }, 0);
            return paramNames;
        } catch (Exception e) {
            throw e;
        }
    }


    private static boolean sameType(Type[] types, Class<?>[] clazzes) {
        // 个数不同
        if (types.length != clazzes.length) {
            return false;
        }

        for (int i = 0; i < types.length; i++) {
            if (!Type.getType(clazzes[i]).equals(types[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取方法的参数名
     *
     * @param method
     * @return argsNames[]
     */
    public static String[] getMethodParamNames(final Method method) throws Exception {

        return getMethodParamNames(method.getDeclaringClass(), method);
    }

}