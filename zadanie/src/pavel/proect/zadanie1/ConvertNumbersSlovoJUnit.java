//package pavel.proect.zadanie1.JUnit;
package pavel.proect.zadanie1;
import static org.junit.Assert.*;
import java.io.*;
import java.math.BigInteger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import by.minsk.piatrou.ConvertNumbersToWords;

public class ConvertNumbersSlovoJUnit {

	@Test
	public void testConvertNumbersSlovoFromExl() throws IOException {
		ConvertNumbersSlovo converter = new ConvertNumbersSlovo();
		FileInputStream fileInputStream = new FileInputStream("DataDrivenTest/text.txt");
		Workbook workbook = new HSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheetAt(0);
      
        for(int i = 0; i < sheet.getLastRowNum()+1; i++) {
        	Integer number = (int) sheet.getRow(i).getCell(0).getNumericCellValue();
        	String word = sheet.getRow(i).getCell(1).getStringCellValue();
        	
        	assertEquals("Error in number: " + number, word,
        			converter.converter(new BigInteger(String.valueOf(number))));
        }
        
        fileInputStream.close();
        workbook.close();
	}
	
	@Test(expected = NumberFormatException.class)
	public void testNumberFormatException() throws Exception {
		ConvertNumbersSlovo converter = new ConvertNumbersSlovo();
		converter.converter(new BigInteger("dfg12345"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullPointerException() throws Exception {
		ConvertNumbersSlovo converter = new ConvertNumbersSlovo();
		converter.converter(new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000"));
	}
}
