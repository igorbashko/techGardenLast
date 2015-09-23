/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package techgarden;

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
import java.util.*;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.lang.String;
import java.time.*;


public class Controller {

    private Object[][] data2;
    
    public Object[][] getData(String excelFilePath) throws IOException, InvalidFormatException {

FileInputStream fis = new FileInputStream(new File(excelFilePath));
org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(fis);
org.apache.poi.ss.usermodel.Sheet firstSheet = workbook.getSheetAt(0);
int rownum = firstSheet.getLastRowNum();
int colnum = firstSheet.getRow(0).getLastCellNum();
Object[][] data = new Object[rownum][colnum];
//String[][] stringData = new String[rownum][colnum];
for (int i = 0; i < rownum; i++) {
Row row = firstSheet.getRow(i);
if (row != null) {
for (int j = 0; j < colnum; j++) {
Cell cell = row.getCell(j);
if (cell != null) {
try {
 
    if(cell.getColumnIndex() == 0){
        cell.setCellType(Cell.CELL_TYPE_STRING);
        data[i][j] = cell.getStringCellValue();
         // System.out.println(cell.getStringCellValue());
    }else if(cell.getColumnIndex()==1){
      data[i][j] = cell.getDateCellValue();
   // System.out.println(cell.getDateCellValue());
    }else{
        data[i][j] = cell.getNumericCellValue();
    }
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
/* catch (FileNotFoundException e) {
e.printStackTrace();
} catch (IOException e){
e.printStackTrace();
}*/
//public static void main(String args[]) throws IOException{
public void readFromFile() throws IOException, InvalidFormatException{
Controller controller = new Controller();
Object[][] array1 = controller.getData("/home/igor/Documents/"
+ "Hackathlon/excel.xls");
this.data2 = array1;
System.out.println(array1[0][0]+"\n"+array1[0][1].toString());
}

public void setList(ListView<String> s, LocalDate date){
    ObservableList list = FXCollections.observableArrayList();
    Set<String> phones = new HashSet<String>();
    for (int i=0; i<this.data2.length; i++){
        for(int j=0; j<this.data2[i].length; j++){
           Date date3 = (Date) data2[i][1];
           Calendar c = Calendar.getInstance();
           c.setTime(date3);
           int yearCal = date.getYear();
           int yearEx = c.get(Calendar.YEAR);
           int monthCal = (int) date.getMonthValue();
           int monthEx = c.get(Calendar.MONTH)+1;
           int dayCal = (int) date.getDayOfMonth();
           int dayEx = c.get(Calendar.DAY_OF_MONTH);
           if(yearCal == yearEx && 
                   monthCal == monthEx && 
                                 dayCal == dayEx){
            phones.add(this.data2[i][0].toString());
        }
       }
    }
   list.addAll(phones);
  s.setItems(list);
}

}


