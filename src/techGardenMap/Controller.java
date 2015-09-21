/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techGardenMap;

/**
 *
 * @author igorbashka
 */
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Controller {
    public void cehk(){
        System.out.println("you");
    }
public Double[][] getData(String excelFilePath) throws IOException, InvalidFormatException {
        FileInputStream fis = new FileInputStream(new File(excelFilePath));

  org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(fis);

  org.apache.poi.ss.usermodel.Sheet firstSheet = workbook.getSheetAt(0);
        int rownum = firstSheet.getLastRowNum();
        int colnum = firstSheet.getRow(0).getLastCellNum();
        Double[][] data = new Double[rownum][colnum];
        //String[][] stringData = new String[rownum][colnum];
        for (int i = 0; i < rownum; i++) {
            Row row = firstSheet.getRow(i);
            if (row != null) {
                for (int j = 0; j < colnum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        try {
                            //cell.setCellType(Cell.CELL_TYPE_STRING);
                            
                            data[i][j] = cell.getNumericCellValue();
                            System.out.println(cell.getDateCellValue());
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                            //
                        }
                    }
                }
            }
        }
        workbook.close();
        fis.close();
        return data;
    }
/*    catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e){
        e.printStackTrace();
    }*/
//public static void main(String args[]) throws IOException{
public void readFromFile() throws IOException, InvalidFormatException{
   Controller controller = new Controller();
   Double[][] array1 = controller.getData("/home/igorbashka/Documents/"
                        + "Hackathlon/excel.xls");
   System.out.println(Arrays.deepToString(array1));
  }
}

