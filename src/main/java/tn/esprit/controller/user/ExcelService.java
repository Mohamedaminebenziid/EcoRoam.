package tn.esprit.controller.user;

import javafx.stage.FileChooser;
import tn.esprit.models.user;


import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import tn.esprit.controller.user.ExcelService;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelService {


        public void generateExcel(List<user> listView) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Excel");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
            );
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");


                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("email");
                headerRow.createCell(1).setCellValue("name");
                headerRow.createCell(2).setCellValue(" phone ");
                headerRow.createCell(3).setCellValue("cin ");
                headerRow.createCell(4).setCellValue("roles");
                headerRow.createCell(5).setCellValue("image");
                headerRow.createCell(6).setCellValue("password");
                headerRow.createCell(7).setCellValue("reset_token");
                headerRow.createCell(8).setCellValue("is_banned");
                headerRow.createCell(9).setCellValue("is_verified");


                for (int i = 0; i < listView.size(); i++) {
                    Row dataRow = sheet.createRow(i + 1);

                    dataRow.createCell(0).setCellValue(listView.get(i).getEmail());
                    dataRow.createCell(1).setCellValue(listView.get(i).getUsername());
                    dataRow.createCell(2).setCellValue(listView.get(i).getTel_number());
                    dataRow.createCell(4).setCellValue(listView.get(i).getRoles());
                    dataRow.createCell(6).setCellValue(listView.get(i).getPassword());
                    dataRow.createCell(8).setCellValue(listView.get(i).isIs_banned());

                }

                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                    workbook.close();
                    System.out.println("Excel generated successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

