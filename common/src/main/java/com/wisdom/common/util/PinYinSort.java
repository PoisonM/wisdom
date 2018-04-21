package com.wisdom.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Comparator;

/**
 * FileName: PinYinSort
 *
 * @author: 赵得良
 * Date:     2018/4/21 0021 13:05
 * Description: 拼音排序类
 */
public class PinYinSort {
    public static void main(String[] args) {

//        String[] arr={ "王二六","张三", "李四", "王五","赵六", "JAVA", "123","怡情"};
//        Arrays.sort(arr,new ComparatorPinYin());
//        for (String string : arr) {
//            System.out.println(string);
//        }
        System.out.println("args = [" + PinYinSort.getSortType() + "]");
    }

    /**
     * 功能：实现汉语拼音序比较
     */
    static class ComparatorPinYin implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return ToPinYinString(o1).compareTo(ToPinYinString(o2));
        }

    }

    public static char[] getSortType() {

        char a = 'a';
        char[] chars = new char[26];
        for (int i = 0; i < 26; i++) {
            chars[i] = a;
            a++;
        }
        return chars;
    }

    /**
     * 汉字转拼音
     */
    public static String ToPinYinString(String str) {

        StringBuilder sb = new StringBuilder();
        String[] arr = null;

        for (int i = 0; i < str.length(); i++) {
            arr = PinyinHelper.toHanyuPinyinStringArray(str.charAt(i));
            if (arr != null && arr.length > 0) {
                for (String string : arr) {
                    sb.append(string);
                }
            }
        }

        return sb.toString();
    }
}
