package capstone.tcp.server.common;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;

import org.apache.commons.lang3.math.NumberUtils;

public class ConvertUtil {
    
    private ConvertUtil() {}

    /**
     * @param str - String
     * @return byte[]
     */
    public static byte[] getString2Byte(String str) {
        BigInteger big = new BigInteger(str);
        return big.toByteArray();
    }
    
    /**
     * @param str - String
     * @return byte[]
     */
    public static byte[] getString2Byte16(String str) {
        BigInteger big = new BigInteger(str, 16);
        return big.toByteArray();
    }

    /**
     * @param i - int
     * @return byte[]
     */
    public static byte[] getInt2Byte(int i) {
        byte[] buf = new byte[4];

        buf[0] = (byte) (0xff & (i >> 24));
        buf[1] = (byte) (0xff & (i >> 16));
        buf[2] = (byte) (0xff & (i >> 8));
        buf[3] = (byte) (0xff & i);

        return buf;
    }
    
    /**
     * @param i - int
     * @return byte[]'s 4th
     */
    public static byte getInt2Byte4(int i) {
        byte[] buf = new byte[4];

        buf[0] = (byte) (0xff & (i >> 24));
        buf[1] = (byte) (0xff & (i >> 16));
        buf[2] = (byte) (0xff & (i >> 8));
        buf[3] = (byte) (0xff & i);

        return buf[3];
    }

    /**
     * @param s - short
     * @return byte[]
     */
    public static byte[] getShort2Byte(short s) {
        byte buf[] = new byte[2];

        buf[1] = (byte) (s & 0xff);
        buf[0] = (byte) (s >>> 8 & 0xff);

        return buf;
    }
    
    /**
     * @param s - short
     * @return byte[]
     */
    public static byte[] getShort2ByteLittle(short s) {
        byte buf[] = new byte[2];

        buf[0] = (byte) (s & 0xff);
        buf[1] = (byte) (s >>> 8 & 0xff);

        return buf;
    }

    /**
     * @param buf - byte[]
     * @param offset - int
     * @param length - int
     * @return String
     */
    public static String getByte2String(byte[] buf, int offset, int length) {
        String str = null;

        if (length > 1) {
            str = new String(buf, offset, length);
        } else {
            str = "" + getByte2Int(buf[offset]);
        }

        return str.trim();
    }

    /**
     * @param b - byte
     * @return int
     */
    public static int getByte2Int(byte b) {
        byte[] buf = new byte[4];
        buf[3] = b;

        return getByte2Int(buf, 0);
    }

    /**
     * @param buf - byte[]
     * @param offset - int
     * @return int
     */
    public static int getByte2Int(byte[] buf, int offset) {
        return (buf[offset + 3] & 0xff) 
                | ((buf[offset + 2] & 0xff) << 8) 
                | ((buf[offset + 1] & 0xff) << 16)
                | ((buf[offset] & 0xff) << 24);
    }
    
    /**
     * @param buf - byte[]
     * @param offset - int
     * @return short
     */
    public static short getByte2SignedShortLittle(byte[] buf, int offset) {
        return (short) ((buf[offset + 1] << 8) | (buf[offset] & 0xff));
    }
    
    /**
     * @param buf - byte[]
     * @param offset - int
     * @return short
     */
    public static short getByte2SignedShortBig(byte[] buf, int offset) {
        return (short) ((buf[offset] << 8) | (buf[offset + 1] & 0xff));
    }

    /**
     * @param buf - byte[]
     * @param offset - int
     * @return short
     */
    public static short getByte2UnSignedShortLittle(byte[] buf, int offset) {
        return (short) ((buf[offset + 1] & 0xff << 8) | (buf[offset] & 0xff));
    }
    
    /**
     * @param buf - byte[]
     * @param offset - int
     * @return short
     */
    public static short getByte2UnSignedShortBig(byte[] buf, int offset) {
        return (short) ((buf[offset] & 0xff << 8) | (buf[offset + 1] & 0xff));
    }
    
    /**
     * @param buf - byte[]
     * @param offset - int
     * @return int
     */
    public static int getByte2UnSignedIntBig(byte[] buf, int offset) {
        return (buf[offset] & 0xFF << 8) | (buf[offset + 1] & 0xFF);
    }
    
    /**
     * @param buf - byte[]
     * @param offset - int
     * @return int
     */
    public static int getByte2SignedIntBig(byte[] buf, int offset) {
        return (buf[offset] << 8) | (buf[offset + 1] & 0xff);
    }
    
    /**
     * @param src
     * @param offset
     * @return
     */
    public static int byteArrayToInt(byte[] src, int offset) {
        final int size = Integer.SIZE / 8;    //4
        final byte[] dst = new byte[size];
        
        dst[0] = (byte) 0x00;
        dst[1] = (byte) 0x00;
        dst[2] = src[offset];
        dst[3] = src[offset+1];
        
        ByteBuffer buff = ByteBuffer.allocate(size);
        buff = ByteBuffer.wrap(dst);
        buff.order(ByteOrder.BIG_ENDIAN); // Endian에 맞게 세팅
        
        return buff.getInt();
    }
    
    /**
     * @param src
     * @param offset
     * @return
     */
    public static int byteArrayToInt4(byte[] src, int offset) {
        final int size = Integer.SIZE / 8;    //4
        final byte[] dst = new byte[size];
        
        dst[0] = src[offset];
        dst[1] = src[offset+1];
        dst[2] = src[offset+2];
        dst[3] = src[offset+3];
        
        ByteBuffer buff = ByteBuffer.allocate(size);
        buff = ByteBuffer.wrap(dst);
        buff.order(ByteOrder.BIG_ENDIAN); // Endian에 맞게 세팅
        
        return buff.getInt();
    }
    
    /**
     * @param buf - byte[]
     * @return log
     */
    public static String getLogByte2HexString(byte[] buf) {
        return getLogByte2HexString(buf, buf.length);
    }

    /**
     * @param buf - byte[]
     * @param length - int
     * @return log
     */
    public static String getLogByte2HexString(byte[] buf, int length) {
        StringBuffer result = new StringBuffer();
        String temp = "";

        for (int i = 0; i < length; i++) {
            temp = Integer.toHexString(buf[i]);

            if (temp.length() >= 3) {
                result.append(temp.substring(temp.length() - 2).toUpperCase());
            } else {
                if (temp.length() == 1)
                    temp = "0" + temp;
                result.append(temp);
            }

            if (i < length - 1) {
                result.append(" ");
            }

        } // for

        return result.toString();
    }

    /**
     * @param buf - byte[]
     * @param offset - int
     * @param length - int
     * @return String
     */
    public static String getByte2HexStringUpper(byte[] buf, int offset, int length) {
        StringBuffer result = new StringBuffer();
        String temp = "";

        for (int i = offset; i < offset + length; i++) {
            temp = Integer.toHexString(buf[i]);

            if (temp.length() >= 3) {
                result.append(temp.substring(temp.length() - 2));
            } else {
                if (temp.length() == 1)
                    temp = "0" + temp;
                result.append(temp);
            }

        } // for

        return result.toString().toUpperCase();
    }
    
    /**
     * @param buf - byte[]
     * @param offset - int
     * @param length - int
     * @return String
     */
    public static String getByte2HexString(byte[] buf, int offset, int length) {
        StringBuffer result = new StringBuffer();
        String temp = "";

        for (int i = offset; i < offset + length; i++) {
            temp = Integer.toHexString(buf[i]);

            if (temp.length() >= 3) {
                result.append(temp.substring(temp.length() - 2));
            } else {
                if (temp.length() == 1)
                    temp = "0" + temp;
                result.append(temp);
            }

        } // for

        return result.toString();
    }

    public static int getSignedByte(String src) {
        int i = NumberUtils.toInt(src);
        return (i >= 128) ? i - 256 : i;
    }

    public static String byteArrayToBinaryString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    public static String byteToBinaryString(byte n) {
        StringBuffer sb = new StringBuffer("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for (int i = 1; i < count; ++i) {
            String t = s.substring((i - 1) * 8, i * 8);
            b[i - 1] = binaryStringToByte(t);
        }
        return b;
    }

    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }

    public static int binaryToInteger(String binary) {
        char[] numbers = binary.toCharArray();
        int result = 0;
        for (int i = numbers.length - 1; i >= 0; i--)
            if (numbers[i] == '1')
                result += Math.pow(2, (numbers.length - i - 1));
        return result;
    }
    
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }
        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }
    
    public static String stringToHex(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
          result += String.format("%02X ", (int) s.charAt(i));
        }
        return result;
      }
    
    public static String stringToHex0x(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
          result += String.format("0x%02X ", (int) s.charAt(i));
        }
        return result;
    }
    
    public static String getEncryptSHA256(String s_origin) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(s_origin.getBytes(), 0, s_origin.length());
        return new BigInteger(1, md.digest()).toString(16); 
    }
    
    public static String getEncryptSHA256(byte[] b_origin) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(b_origin, 0, b_origin.length);
        return new BigInteger(1, md.digest()).toString(16); 
    }
    
    public static String hexToAscii(String hexStr) {
        StringBuffer output = new StringBuffer("");
         
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
         
        return output.toString();
    }
    
    public static String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
 
        for (int i = 0; i < b.length; i++) {
            sb.append(String.format("%02X", b[i]));
            if ((i + 1) % 16 == 0 && ((i + 1) != b.length)) {
                sb.append(" ");
            }
        }
 
        return sb.toString();
    }
    
    /**
     * Integer.toBinaryString
     * </br> lpad 0 by 16 length
     * @param i
     * @return
     */
    public static String toBinaryString(int i) {
        String str = Integer.toBinaryString(i);
        if (str != null && str.length() < 16) {
            while (str.length() < 16) {
                str = "0" + str;
            }
        }
        return str;
    }
    
}
