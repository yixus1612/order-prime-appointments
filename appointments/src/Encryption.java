

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    public String hash(String password, String email){
        try{
        // initialize algorithm
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        // adding a salt to the password to make it more difficult to use a rainbow table attack
        String salted = password + "CSCE3444";

        md.update(salted.getBytes());
        
        // get the output of the algorithm
        byte[] resultByteArray = md.digest();

        // rebuild string from bytes
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
