package com.webapps.common.utils.encrypt2;

/**
 * 加密算发的枚举类
 */
public enum EncryptAlgorithm {
    AES("AES"),
    DES("DES"),
    ThreeDES("DESede"),
    ThreeDESWithPadding("DESede/ECB/PKCS5Padding"),
    RSA("RSA"),
    RSAWithPadding("RSA/ECB/PKCS1Padding");	
    
    private final String alg_code;
    EncryptAlgorithm(String code){
        alg_code = code;
    }
    
    public String getAlgorithm(){
        return alg_code;
    }
    
    public static void main(String args[]){
        for (Object obj : java.security.Security.getAlgorithms("Cipher")) {
            System.out.println(obj);
          }
          //用上面的代码打印系统算法时显示包含了上面的算法。 
        }
}
