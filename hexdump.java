import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class hexdump {
    public static void main(String[] args) throws IOException {

        FileInputStream in = null; //input file
        FileWriter out = null; //output file
		
        try {
            in = new FileInputStream(args[0]); //we need at least 1 arg
			
			if(args.length > 1) //if second arg is available, save output to file
				out = new FileWriter(args[1]);
				
			int counter = 0;	
            int c;
			int pos = 0;
			String chared = "";
			String hexed = "";
			
            while ((c = in.read()) != -1) {
				if(counter == 15){	//Every 16 bytes
					hexed += String.format(" %1$02X", c);	//2 character hex val of byte, divided with 1 space
					chared += (c < 32) ? '.' : (char)c;			//ASCII representation, '.' instead of non-printable characters
					
					if(args.length > 1) {	//Output to file if second arg present or to console if not
						out.write(String.format("0x%1$08X ", pos) + "|" + hexed + " | " + chared);				//Addres in hex | bytes in hex | ASCII representation
						out.write("\r\n");
					} else {
						System.out.println(String.format("0x%1$08X ", pos) + "|" + hexed + " | " + chared);	//Addres in hex | bytes in hex | ASCII representation
					}
					
					hexed = "";
					chared = "";
					counter = 0;
					pos += 16;
				} else {
					hexed = hexed + String.format(" %1$02X", c);	//2 character hex val of byte, divided with 1 space
					chared += (c < 32) ? '.' : (char)c;					//ASCII representation, '.' instead of non-printable characters
					counter++;
				}
            }
			
			if(counter != 0) {		//Final bytes that don't make full 16
				int i;
				for(i = counter; i <= 15; i++)
					hexed += " ..";
				if(args.length > 1) {
					out.write(String.format("0x%1$08X ", pos) + "|" + hexed + " | " + chared);
					out.write("\r\n");
				} else {
					System.out.println(String.format("0x%1$08X ", pos) + "|" + hexed + " | " + chared);
				}
			}
        } finally {
            if (in != null)
                in.close();
			if(args.length > 1)
				if (out != null)
					out.close();
        }
    }
}