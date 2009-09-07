package cn.touki.util;

import java.math.BigDecimal;
import java.util.HashMap;

public class CurrencyUtils {

    private static final int DEF_DIV_SCALE = 2;

    /**
     * 四舍五入格式化货币数字.
     * <p>
     * 例如
     * <li>传入168.1235,返回168.12;
     * <li>传入168.1259,返回168.13
     * 
     * @param amount 货币金额
     * @return String 两位小数的货币金额
     */
    public static String format(String amount) {
        BigDecimal amountBD = new BigDecimal(amount);
        BigDecimal b1 = new BigDecimal("1");
        amountBD = amountBD.divide(b1, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return amountBD.toString();
    }

    /**
     * 通过传入的参数查询用户支付的币种符号
     * 
     * @return String 币种符号
     */
    public static String getCurrencyCode(String num) {
        String currency = "";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("0", "RMB");
        map.put("1", "USD");
        map.put("2", "HKD");
        map.put("3", "URO");
        map.put("4", "JPY");
        if (map.get(num) != null) {
            currency = map.get(num);
        }
        return currency;

    }
}
