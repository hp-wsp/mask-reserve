package com.ts.server.mask.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 省份证号工具类
 *
 * @author TS Group
 */
public class IDCardUtils {
    private static final Logger logger = LoggerFactory.getLogger(IDCardUtils.class);
    private static final Map<String, String> CARDID_AREA_CODE = initCardIdAreaCode();
    private static final String[] CARDID_VERIFY_CODE = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
    private static final String[] CARDID_WI = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };

    /**
     * 验证身份证有效性
     *
     * @param cardId 身份证号
     * @return true:有效
     */
    public static boolean validate(String cardId){

        cardId = StringUtils.upperCase(cardId);
        if (cardId.length() != 15 && cardId.length() != 18) {
            logger.debug("CardID len not 15 or 18");
            return false;
        }

        boolean is18Len = cardId.length() == 18;
        String standard = is18Len ? cardId.substring(0, 17) : String.format("%s19%s", cardId.substring(0, 6), cardId.substring(6, 15));
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher numMatcher = pattern.matcher(standard);
        if(!numMatcher.matches()){
            logger.debug("CardID 17 not number");
            return false;
        }

        String birthday = getBirthday(cardId);
        try{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            LocalDateTime.now().getYear();
            int age = (LocalDateTime.now().getYear() - LocalDateTime.ofInstant(format.parse(birthday).toInstant(), ZoneId.systemDefault()).getYear());
            if(age > 150 || age < 0){
                logger.debug("CardID age > 150 or age < 0");
                return false;
            }
        }catch (Exception e){
            logger.debug("CardID validate date error, date is {}", birthday);
            return false;
        }

        String areaCode = standard.substring(0,2);
        if (!CARDID_AREA_CODE.containsKey(areaCode)) {
            logger.debug("Area cod not exist");
            return false;
        }

        if(is18Len){
            int totalWi = 0;
            for (int i = 0; i < 17; i++) {
                totalWi = totalWi + (Integer.parseInt(standard.substring(i, i + 1)) * Integer.parseInt(CARDID_WI[i]));
            }
            String verifyCode = CARDID_VERIFY_CODE[totalWi % 11];
            String complete = standard + verifyCode;
            if(!complete.equals(cardId)){
                logger.debug("Verify code is error");
                return false;
            }
        }

        return true;
    }

    /**
     * 通过身份证得到出生日期
     *
     * @param cardId 身份证号
     * @return 日期格式 yyyy-MM-dd
     */
    public static String getBirthday(String cardId){
        int yOffset = 6;
        int yLen = cardId.length() == 18 ? 4 : 2;
        int mOffset = yOffset + yLen;
        int mLen = 2;
        int dOffset = mOffset + mLen;
        int dLen = 2;

        String year = (yLen == 2) ? ("19" + cardId.substring(yOffset, yOffset + yLen)) : cardId.substring(yOffset, yOffset + yLen);
        String month = cardId.substring(mOffset, mOffset+ mLen);
        String day = cardId.substring(dOffset, dOffset + dLen);

        return String.format("%s-%s-%s", year, month, day);
    }

    /**
     * 通过身份证号得到性别
     *
     * @param cardId
     * @return
     */
    public static String getSex(String cardId){
        char code = cardId.length() == 18 ? cardId.charAt(16) : cardId.charAt(14);
        return (code % 2 == 0) ? "女" : "男";
    }
    
    /**
     * 混淆省份证
     * 
     * @param v 证件号
     * @return
     */
    public static String mixIdCard(String v){
		if( v == null || v.length() < 7){
			return v;
		}
		return StringUtils.left(v, 3) + StringUtils.repeat("*", v.length() - 7) + StringUtils.right(v, 4);
	}

    /**
     * 身份证区域编号
     *
     * @return
     */
    private static Map<String, String> initCardIdAreaCode() {
        Map<String,String> map = new HashMap<>();
        map.put("11", "北京");
        map.put("12", "天津");
        map.put("13", "河北");
        map.put("14", "山西");
        map.put("15", "内蒙古");
        map.put("21", "辽宁");
        map.put("22", "吉林");
        map.put("23", "黑龙江");
        map.put("31", "上海");
        map.put("32", "江苏");
        map.put("33", "浙江");
        map.put("34", "安徽");
        map.put("35", "福建");
        map.put("36", "江西");
        map.put("37", "山东");
        map.put("41", "河南");
        map.put("42", "湖北");
        map.put("43", "湖南");
        map.put("44", "广东");
        map.put("45", "广西");
        map.put("46", "海南");
        map.put("50", "重庆");
        map.put("51", "四川");
        map.put("52", "贵州");
        map.put("53", "云南");
        map.put("54", "西藏");
        map.put("61", "陕西");
        map.put("62", "甘肃");
        map.put("63", "青海");
        map.put("64", "宁夏");
        map.put("65", "新疆");
        map.put("71", "台湾");
        map.put("81", "香港");
        map.put("82", "澳门");
        map.put("91", "国外");
        return map;
    }
}
