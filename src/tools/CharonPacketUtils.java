package tools;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Charon 
 */

public class CharonPacketUtils {
	
	static Path newFile = Paths.get("M:/PSWG/LOGS/PSWG_PacketLog.txt");
	private static boolean writeToFile = false;
	private CharonPacketLogger logger;
	
	
	public static void createLog(){
		
	    try {
	      Files.deleteIfExists(newFile);
	      newFile = Files.createFile(newFile);
	    } catch (IOException ex) {
	      System.out.println("Error creating file");
	    }
	    System.out.println(Files.exists(newFile)); 
	    writeToFile=true;
	}
	
	public static void appendToLog(String content, Path newFile){
	    //Writing to file
	    try(BufferedWriter writer = Files.newBufferedWriter(
	            newFile, Charset.defaultCharset(),new OpenOption[] {StandardOpenOption.APPEND})){
	      writer.append(content);
	      writer.append("\n");
	      writer.flush();
	    }catch(IOException exception){
	      System.out.println("Error writing to file");
	    }	
	}
	
	public static void printAnalysis(IoBuffer pack){
		printAnalysis(pack, "");
	}
	
	public static void printAnalysis(IoBuffer pack, String packetName){
		
		if (!NGECore.PACKET_DEBUG)
			return;
		
		//appendToLog(packetName+" :");
		int hexLineNumber = 0;
		byte[] packArray = pack.array();
		int lineCount = packArray.length/16;
		int actualIndex = 0;
		String buffString = "";
		String asciiString = "";
		String logString = packetName+" :\n";
		for (int lineIndex = 0; lineIndex <= lineCount; lineIndex++)
        {
			byte[] ASCIIArray = new byte[16];
			for (int x=0;x<16;x++)
				ASCIIArray[x] = 32;
			
			buffString = "";
			asciiString = "";
            // remaining bytes per line
            int remaining = packArray.length - actualIndex;
            int byteColumnSize = 0;
            if (remaining < 16) byteColumnSize = remaining;
            else byteColumnSize = 16;
            for (int byteColumnIndex = 0; byteColumnIndex < byteColumnSize; byteColumnIndex++)
            {
                if (byteColumnIndex == 8)
                    buffString += "   ";
                else buffString += " ";
                	 
                String addString = ((Integer.toString( ( packArray[actualIndex] & 0xff ) + 0x100, 16).substring( 1 )).toUpperCase() + "");	 
                char character = addString.charAt(0);
				if (character==0)
					addString = " ";
                buffString += addString;
                if (packArray[actualIndex] < 31 || packArray[actualIndex] == 13)
                    ASCIIArray[byteColumnIndex] = 46;
                else
                    ASCIIArray[byteColumnIndex] = packArray[actualIndex];
                
                
                if (ASCIIArray[byteColumnIndex]==0)
                	ASCIIArray[byteColumnIndex]=32;

                actualIndex++;
            }
            try {
				//asciiString +=  new String(ASCIIArray, "US-ASCII");
				asciiString +=  new String(ASCIIArray, "UTF-8");
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			asciiString.replaceAll(" ",".");
			String number = Integer.toHexString(hexLineNumber); 
			if (number.length()<3)
				number = "00" + number;
			if (number.length()<4)
				number = "0" + number;
				
			String lineNumberString = number + ":   ";
			hexLineNumber += 0x10; 
            //System.out.println(lineNumberString + buffString + "   " + asciiString);
           // if (writeToFile)
           // 	appendToLog(lineNumberString + buffString + "   " + asciiString);
			
			
			logString += lineNumberString + buffString + "   " + asciiString + "\n";
        }
        
        logString += "\n";

        if (writeToFile){
        	CharonPacketLogger logger = CharonPacketLogger.getLogger();   
        	logger.log(logString,newFile);
        }
        
		//System.out.println("#########################################################################");
		System.out.println("");
		//if (writeToFile)
		//	appendToLog("");
	}
}
