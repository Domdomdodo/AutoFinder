package finder;

import finder.controller.FileSelectionController;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;

public class CarReader {

    public CarReader(){

    }

    public static HashSet<Car> fetchCars(){
        HashSet<Car> cars = new HashSet<>();

        try {
            FileInputStream stream = new FileInputStream(FileSelectionController.SELECTED_FILE);
            XSSFWorkbook wb = new XSSFWorkbook(stream);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()){
                rowIterator.next();
                //skip first row
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String colour = "";
                if (row.getCell(8) != null) {
                    colour = row.getCell(8).getStringCellValue();
                }



                cars.add(new Car(
                        row.getCell(0).getStringCellValue(), //License
                        row.getCell(1).getStringCellValue(), // Brand
                        getStringFromCell(row.getCell(2)), // Model
                        getStringFromCell(row.getCell(3)), //Type
                        row.getCell(4).getStringCellValue(), // Body
                        (int)row.getCell(5).getNumericCellValue(), // Construction year
                        (int)row.getCell(6).getNumericCellValue(), // KM
                        (int)row.getCell(7).getNumericCellValue(), // Doors
                        getStringFromCell(row.getCell(8)), //Colour
                        row.getCell(24).getNumericCellValue(), // price
                        getFuelType(row.getCell(11).getStringCellValue()) //fuelType
                ));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return cars;
    }



    public static Car.FuelType getFuelType(String fuelString){

        switch (fuelString){
            case "benzine":
                return Car.FuelType.GASOLINE;

            case "diesel":
                return Car.FuelType.DIESEL;

            case "hybride benzine":
                return Car.FuelType.HYBRID_GASOLINE;

            case "hybride diesel":
                return Car.FuelType.HYBRID_DIESEL;

            case "elektriciteit":
                return Car.FuelType.ELECTRIC;

            default:
                return Car.FuelType.UNKNOWN;
        }
    }

    public static String getStringFromCell(Cell cell){
        String value = "";
        if (cell != null) {
            if (cell.getCellType() == CellType.STRING) {
                value = cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                value = String.valueOf(cell.getNumericCellValue());
            }
        }
        return value;
    }
}
