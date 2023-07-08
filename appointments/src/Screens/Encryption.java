package Screens;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    public String hash(String password, String email){
        try{
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        String salted = password + email;

        md.update(salted.getBytes());

        byte[] resultByteArray = md.digest();

        StringBuilder sb = new StringBuilder();

            for(byte b: resultByteArray){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }
}
