package com.utils.util;

//import sun.misc.BASE64Decoder; jdk1.8
//import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * base64数据加解密
 */
public class Encrypt {

    private static String DES_ALGORITHM = "DES";

    /**
     * 根据参数生成 KEY
     */
    private static Key getKey(String strKey) {
        try {
            SecureRandom _secureRandom = SecureRandom.getInstance("SHA1PRNG");
            _secureRandom.setSeed(strKey.getBytes());
            KeyGenerator kg = null;
            kg = KeyGenerator.getInstance(DES_ALGORITHM);
            kg.init(_secureRandom);

            // 生成密钥
            return kg.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        }
    }

    /**
     * 加密 String 明文输入 ,String 密文输出
     */
    public static String encode(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";

        Base64.Encoder encoder = Base64.getEncoder();
        // BASE64Encoder base64en = new BASE64Encoder();
        try {
            byteMing = strMing.getBytes("UTF-8");
            byteMi = Encrypt.encryptByte(byteMing);
            //strMi = base64en.encode(byteMi);
            strMi = encoder.encodeToString(byteMi);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            encoder = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     * 解密 以 String 密文输入 ,String 明文输出
     *
     * @param strMi
     * @return
     */
    public static String decode(String strMi) {
        Base64.Decoder decoder = Base64.getDecoder();
        //BASE64Decoder base64De = new BASE64Decoder();
        System.out.println(strMi.getBytes().length);
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            //byteMi = base64De.decodeBuffer(strMi);
            byte[] result = decoder.decode(strMi);
            byteMing = Encrypt.decryptByte(result);
            strMing = new String(byteMing, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            decoder = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    /**
     * 加密以 byte[] 明文输入 ,byte[] 密文输出
     *
     * @param byteS
     * @return
     */
    public static byte[] encryptByte(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getKey("3GIASI359NSKLVIXKZUSDJISU2NK4INB"));
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
     *
     * @param byteD
     * @return
     */
    public static byte[] decryptByte(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKey("3GIASI359NSKLVIXKZUSDJISU2NK4INB"));
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        System.out.println(byteFina.length);
        return byteFina;
    }

    /**
     * 当出现乱码问题时,用来检测字符串是什么编码
     * 目前只测试"ISO8859-1","GBK","UTF-8"3种编码的组合
     *
     * @return from解密编码, to加密编码
     */
    public static void checkEncode(String text) {
        String[] encode = {"ISO8859-1", "GBK", "UTF-8"};
        try {
            for (int i = 0; i < encode.length; i++) {
                for (int j = 0; j < encode.length; j++) {
                    System.out.println("from" + encode[i] + ",to:" + encode[j] + "=" +
                            new String(text.getBytes(encode[i]), encode[j]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//		StringBuffer sb = new StringBuffer();
//		if (sb!=null) {
//			System.err.println("aa");
//		}

        // Encrypt des = new Encrypt("1234567");
//		String str1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><UserType>1</UserType><UserAccount>csxiaoyao</UserAccount><UserId>1</UserId><UserName>测试人员</UserName><ClassName>二年(1)班</ClassName><SchoolName>福建实验中学</SchoolName></request>";
        //String str4 = "OcT5wd4kxTRdr3TewhNg0RgAI2OiNPA930t/HS9pJkSSB3GdM4gNvHBh7GRX2hBVzEHXkSFyO+L3cungYs1nyTLSrkFKuaStAzxErIpX2g2k2qugzsxZh5DyYewi2Ws7feKOS0mzsYnS4XYt73ariA3uFBx67WKUlruQITu5WLY=";
        // DES 加密字符串
//		String str2 = Encrypt.encode(str1);
        // DES 解密字符串
//		String deStr = Encrypt.decode(str2);
//		System.out.println(" 加密前： " + str1);
//		System.out.println(" 加密后： " + str2);
//		System.out.println(" 解密后： " + deStr);

//		String str001 =  "{\"cpId\":\"555\",\"id\":\"cs_771883\",\"opeType\":\"1\",\"serCode\":\"001\"}";
//		String str002 =  "{\"cpId\":\"555\",\"id\":\"cs_913089\",\"opeType\":\"1\",\"serCode\":\"002\"}";
//		String str002_1 =  "{\"cpId\":\"555\",\"id\":\"TEA913089\",\"opeType\":\"2\",\"serCode\":\"002\"}";
//		String str003 =  "{\"cpId\":\"555\",\"id\":\"cs_771883\",\"opeType\":\"1\",\"serCode\":\"003\"}";
//		String str003_1 =  "{\"cpId\":\"555\",\"id\":\"TEA913089\",\"opeType\":\"2\",\"serCode\":\"003\"}";
//		String str004 =  "{\"cpId\":\"555\",\"id\":\"cs_771883\",\"opeType\":\"1\",\"serCode\":\"004\"}";
//		String str004_1 =  "{\"cpId\":\"555\",\"id\":\"TEA_913089\",\"opeType\":\"2\",\"serCode\":\"004\"}";
//
//		String str009 =  "{\"cpId\":\"555\",\"smsInfo\":{transId:\"371238721\",type:\"2\",ids:[\"cs772610\",\"cs772611\",\"cs772612\",\"cs772613\",\"cs772614\""
//				+ ",\"cs772615\",\"cs772616\",\"cs772617\",\"cs772618\",\"cs772619\",\"cs772620\",\"cs772621\",\"cs772622\",\"cs772623\",\"cs772624\",\"cs772625\""
//				+ ",\"cs772626\",\"cs772627\",\"cs772628\",\"cs772629\"],"
//				+ "content:\"一组内容\",isLong:\"1\",title:\"1\"},\"opeType\":\"2\",\"serCode\":\"009\"}";
//		String str0099 =  "{\"cpId\":\"555\",\"smsInfo\":{transId:\"371238722\",type:\"2\",ids:[\"cs772630\",\"cs772631\",\"cs772632\",\"cs772633\",\"cs772634\""
//				+ ",\"cs772635\",\"cs772636\",\"cs772637\",\"cs772638\",\"cs772639\",\"cs772640\",\"cs772641\",\"cs772642\",\"cs772643\",\"cs772644\",\"cs772645\""
//				+ ",\"cs772646\",\"cs772647\",\"cs772648\",\"cs772649\"],"
//				+ "content:\"二组内容\",isLong:\"1\",title:\"1\"},\"opeType\":\"2\",\"serCode\":\"009\"}";
//		String str00999 =  "{\"cpId\":\"555\",\"smsInfo\":{transId:\"371238723\",type:\"2\",ids:[\"cs772650\",\"cs772651\",\"cs772652\",\"cs772653\",\"cs772654\""
//				+ ",\"cs772655\",\"cs772656\",\"cs772657\",\"cs772658\",\"cs772659\",\"cs772660\",\"cs772661\",\"cs772662\",\"cs772662\",\"cs772663\",\"cs772664\""
//				+ ",\"cs772665\",\"cs772666\",\"cs772667\",\"cs772668\"],"
//				+ "content:\"三组内容\",isLong:\"1\",title:\"1\"},\"opeType\":\"2\",\"serCode\":\"009\"}";
//		String str009999 =  "{\"cpId\":\"555\",\"smsInfo\":{transId:\"371238723\",type:\"2\",ids:[\"hz1480734\",\"hz1480735\",\"hz1480736\",\"hz1480737\",\"hz1480738\""
//				+ ",\"hz1480739\",\"hz1480740\",\"hz1480741\",\"hz1480742\",\"hz1480743\",\"hz1480744\",\"hz1480745\",\"hz1480746\",\"hz1480747\",\"hz1480748\",\"hz1480749\""
//				+ ",\"hz1480750\",\"hz1480751\",\"hz1480752\",\"hz1480753\"],"
//				+ "content:\"杭州组内容\",isLong:\"1\",title:\"1\"},\"opeType\":\"2\",\"serCode\":\"009\"}";
//
//
//
//		String str009_1 =  "{\"cpId\":\"187\",\"smsInfo\":{transId:\"371238721\",type:\"2\",ids:[\"hz3180462\"],"
//				+ "content:\"测试短信二\",isLong:\"1\",title:\"1\"},\"opeType\":\"2\",\"serCode\":\"009\"}";
//		String str009_9 =  "{\"cpId\":\"555\",\"smsInfo\":{transId:\"371238721\",type:\"1\",ids:[\"TEA1299842\",\"TEA1299843\"],"
//				+ "content:\"短信内容(教师)\",isLong:\"1\",title:\"1\"},\"opeType\":\"2\",\"serCode\":\"009\"}";
//
//		String str010 = "{\"cpId\":\"555\",\"opeType\":\"1\",\"serCode\":\"010\",ids:[\"cs762912\",\"cs771883\"]}";
//		String str010_ = "{\"cpId\":\"555\",\"opeType\":\"5\",\"serCode\":\"010\"}";
//		String str012 = "{\"cpId\":\"555\",\"opeType\":\"1\",\"serCode\":\"012\"}";
//		String str013 = "{\"cpId\":\"555\",\"opeType\":\"1\",\"serCode\":\"013\",\"id\":\"cs_771883\",\"transCode\":\"czda_code2\"}";
//		String str013_1 = "{\"cpId\":\"555\",\"opeType\":\"1\",\"serCode\":\"013\",\"id\":\"cs_771883\",\"transCode\":\"71092018\"}";
//		String str013_2 = "{\"cpId\":\"555\",\"opeType\":\"2\",\"serCode\":\"013\",\"id\":\"cs_771883\",\"transCode\":\"czda_code2\"}";
//		String str013_3 = "{\"cpId\":\"555\",\"opeType\":\"2\",\"serCode\":\"013\",\"id\":\"cs_771883\",\"transCode\":\"71092018\"}";
//		String str014 = "{\"cpId\":\"555\",\"opeType\":\"1\",\"serCode\":\"014\",\"id\":\"cs_771883\",\"transCode\":\"71092018\",\"smsValiCode\":\"104532\"}";
//		String str015 = "{\"cpId\":\"555\",\"opeType\":\"1\",\"serCode\":\"015\",\"id\":\"cs_771883\",\"transCode\":\"71092018\",\"smsValiCode\":\"104532\"}";


//		System.out.println("学生基础信息:"+Encrypt.encode(str001));
//		System.out.println("区域信息(家长):"+Encrypt.encode(str002));
//		System.out.println("区域信息(教师):"+Encrypt.encode(str002_1));
//		System.out.println("教师信息:"+Encrypt.encode(str003));
//		System.out.println("教师信息(教师):"+Encrypt.encode(str003_1));
//		System.out.println("班级年级信息:"+Encrypt.encode(str004));
//		System.out.println("班级年级信息(教师):"+Encrypt.encode(str004_1));
//		System.out.println("短信服务(家长1):"+Encrypt.encode(str009));
//		System.out.println("短信服务(家长2):"+Encrypt.encode(str0099));
//		System.out.println("短信服务(家长3):"+Encrypt.encode(str00999));
//		System.out.println("短信服务(杭州家长):"+Encrypt.encode(str009999));
//		System.out.println("短信服务(教师):"+Encrypt.encode(str009_1));
//		System.out.println("同步业务关系_查询指定用户订购信息:"+Encrypt.encode(str010));
//		System.out.println("同步业务关系_同步100条订购信息"+Encrypt.encode(str010_));
//		System.out.println("查询套餐信息:"+Encrypt.encode(str012));
//		System.out.println("cp业务订购(免费):"+Encrypt.encode(str013));
//		System.out.println("cp业务订购(收费):"+Encrypt.encode(str013_1));
//		System.out.println("cp业务取消订购(免费):"+Encrypt.encode(str013_2));
//		System.out.println("cp业务取消订购(收费):"+Encrypt.encode(str013_3));
//		System.out.println("cp业务订购(免费)二次确认:"+Encrypt.encode(str014));
//		BASE64Encoder base64en = new BASE64Encoder();
//		System.out.println(encode("ABC"));
//		System.out.println("cDN4U245ZVdpWTlaMU93b091OGNpeWVXTEp0eDJBNFM2eXQ1VFMyVVdjYytqbE8wQzVGR2N5Um50Wms9".length());
//		System.out.println(Encrypt.decode("DrTULnshWrI="));
//		System.out.println(Encrypt.decode("cDN4U245ZVdpWTlaMU93b091OGNpeWVXTEp0eDJBNFM2eXQ1VFMyVVdjYytqbE8wQzVGR2N5Um50Wms9"));

//		ResourceModifReq req_21 = new ResourceModifReq();
//		req_21.setCpId("555");
//		req_21.setOpeType("1");
//		req_21.setProduct("xxx71883");
//		req_21.setSerCode("021");
//		List<ResourceInfo> items_21 = new ArrayList<ResourceInfo>();
//		ResourceInfo info_21_1 =null;
//		for (int i = 0; i < 5; i++) {
//			info_21_1 = new ResourceInfo();
//			info_21_1.setSerialNumber("测试21接口number"+i);
//			info_21_1.setTitle("标题"+i);
//			info_21_1.setDescri("描述"+i);
//			info_21_1.setRemark("remark"+i);
//			info_21_1.setFee(i);
//			info_21_1.setUrl("http://url"+i);
//			info_21_1.setThumbnail("http://url_t"+i);
//			info_21_1.setOriginal("图像"+i);
//			info_21_1.setGradeSection(String.valueOf(i));
//			info_21_1.setSubject(i);
//			info_21_1.setType(i+1);
//			info_21_1.setTags("物理"+i);
//			info_21_1.setCreator("cs"+i);
//			info_21_1.setCreateTime(String.valueOf(new Date().getTime()));
//			info_21_1.setVersionId(i);
//			items_21.add(info_21_1);
//		}
//		req_21.setItems(items_21);
//		System.out.println("接口21--"+Encrypt.encode(JSONObject.toJSONString(req_21)));
//
//		ResourceDelReq req_22 = new ResourceDelReq();
//		req_22.setCpId("555");
//		req_22.setOpeType("1");
//		req_22.setProduct("xxx71883");
//		req_22.setSerCode("022");
//		List<String> items_22 = new ArrayList<String>();
//		for (int i = 0; i < 5; i++) {
//			items_22.add("测试21接口number"+i);
//		}
//		String [] req_items_22 = new String[items_22.size()];
//		for (int i = 0; i < items_22.size(); i++) {
//			req_items_22[i] = items_22.get(i);
//		}
//		req_22.setItems(req_items_22);
//		System.out.println("接口22--"+Encrypt.encode(JSONObject.toJSONString(req_22)));
//
//		ResourceUseInfoReq req_23 = new ResourceUseInfoReq();
//		req_23.setCpId("555");
//		req_23.setOpeType("1");
//		req_23.setProduct("xxx71883");
//		req_23.setSerCode("023");
//		List<ResourceUseInfo> req_items_23 = new ArrayList<ResourceUseInfo>();
//		ResourceUseInfo info_23;
//		for (int i = 0; i < 5; i++) {
//			info_23 = new ResourceUseInfo();
//			info_23.setActionType(i+1);
//			info_23.setComment("评论"+i);
//			info_23.setCreateTime(String.valueOf(new Date().getTime()));
//			info_23.setOperatorId("cs"+(i+1));
//			info_23.setOperatorName("操作者"+i);
//			info_23.setScore(90+i);
//			info_23.setSerialNumber("测试21接口number"+i);
//			info_23.setSource(i%2==0?2:1);
//			info_23.setUserType(i%2==0?2:1);
//			req_items_23.add(info_23);
//		}
//		req_23.setItems(req_items_23);
//		System.out.println("接口23--"+Encrypt.encode(JSONObject.toJSONString(req_23)));
//
//		ResourceRecommentReq req_24 = new ResourceRecommentReq();
//		req_24.setCpId("555");
//		req_24.setOpeType("1");
//		req_24.setUserType(2);
//		req_24.setUserId("cs_771883");
//		req_24.setSerCode("024");
//		req_24.setType(7);
//		req_24.setSerialNumber("2");
//		System.out.println("接口24--"+Encrypt.encode(JSONObject.toJSONString(req_24)));
//
//		UpUserLoginReq req_25 = new UpUserLoginReq();
//		req_25.setCpId("555");
//		req_25.setOpeType("1");
//		req_25.setProduct("xxx71883");
//		req_25.setSerCode("025");
//		List<UpUserLoginInfo> req_items_25 = new ArrayList<UpUserLoginInfo>();
//		UpUserLoginInfo info_25;
//		for (int i = 0; i < 5; i++) {
//			info_25 = new UpUserLoginInfo();
//			info_25.setIp("127.0.0."+(i+1));
//			info_25.setLoginTime(String.valueOf(new Date().getTime()));
//			info_25.setOperatorId("cs"+i);
//			info_25.setOs(2);
//			info_25.setSource(i%2==0?2:1);
//			info_25.setUserType(i%2==0?2:1);
//			info_25.setVersion("3.3."+i);
//			req_items_25.add(info_25);
//		}
//		req_25.setItems(req_items_25);
//		System.out.println("接口25--"+Encrypt.encode(JSONObject.toJSONString(req_25)));
        System.out.println(decode("Mjg2MDlFNkQzMUY5NDJBOEE3NUYzMzYwNjVEOThDOUE2OTY3QzE4QzQ1OTAwNDE5MjZDQjVGMzcxMEE4MkQ2M0NFNzE1NjJDMEE0OTI0MzFFNTBGMjFDNjlDRkEzRDFGMzRDNzBFQkRGQTNCMkEzNjk2OEE5MEU3QkU2QzkxRkI0MkIyNEZFNkQ4OEJFREJGQTg3REZCQkU1Q0JENDgxMkU1MEYxQTU1QUJBNkQ4OUZDRTI2NTdBOUUzMzQyQkExRURFNjIzNTc4RjU1NEU0Mjc5MDhBNTNDNEYxRTUyMzA4NzNBNjIwMjRGQjZGODZDNDkxQkRBMjgzNDlFQzQ5OTY1REVGNDMyMTkwRkY5NDY="));

    }
}
