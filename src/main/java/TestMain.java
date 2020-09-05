import com.github.snksoft.crc.CRC;

public class TestMain {

    public static void main(String[] args) throws Exception {
        test1();
    }

	private static void test1() {
	}

	private static void test() {
		String data = "123456789";
       	CRC tableDriven = new CRC(CRC.Parameters.XMODEM);
       	long xmodemCrc = tableDriven.calculateCRC(data.getBytes());
        System.out.printf("CRC is 0x%04X\n", xmodemCrc); 
        System.out.println(xmodemCrc);
        
        long curValue = tableDriven.init(); // initialize intermediate value
       	curValue = tableDriven.update(curValue, "123456789".getBytes()); // feed first chunk
        curValue = tableDriven.update(curValue, "01234567890".getBytes()); // feed next chunk
       	long xmodemCrc2 = tableDriven.finalCRC(curValue); // gets CRC of whole data ("12345678901234567890")
        System.out.printf("CRC is 0x%04X\n", xmodemCrc2); // prints "CRC is 0x2C89"
        
        int i = (int) xmodemCrc;
		System.out.println(i);
	}
    
    
}
