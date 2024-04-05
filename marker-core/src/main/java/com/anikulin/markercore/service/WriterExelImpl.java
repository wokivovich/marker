package com.anikulin.markercore.service;

import com.anikulin.markercore.domain.Mark;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class WriterExelImpl implements Writer {

    private static final int COLUMN_WIDTH = 19;
    private static final int LINE_HEIGHT = 45;

    @Override
    public void writeMarksToFile(List<Mark> marks) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Маркировка");
            sheet.setColumnWidth(0, COLUMN_WIDTH * 256);
            sheet.setColumnWidth(1, COLUMN_WIDTH * 256);
            sheet.setColumnWidth(2, COLUMN_WIDTH * 256);
            sheet.setColumnWidth(3, COLUMN_WIDTH * 256);
            sheet.setColumnWidth(4, COLUMN_WIDTH * 256);
            int marksCount = 0;
            int rowNum = 1;
            int cellNum = 0;
            Row row = sheet.createRow(0);
            row.setHeightInPoints(LINE_HEIGHT);
            for (Mark mark : marks) {
                if (marksCount % 5 == 0) {
                    row = sheet.createRow(rowNum);
                    row.setHeightInPoints(LINE_HEIGHT);
                    rowNum++;
                    cellNum = 0;
                }
                Cell cell = row.createCell(cellNum);

                CellStyle style = workbook.createCellStyle();
                formatCell(workbook, cell, style, mark.getName());
                setCellBGColor(style, mark.getColor());

                cell.setCellStyle(style);
                marksCount++;
                cellNum++;
                try (FileOutputStream outputStream = new FileOutputStream("res.xlsx")) {
                    workbook.write(outputStream);
                }
                System.out.println("Excel файл успешно создан!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellBGColor(CellStyle style, String color) {
        switch (color) {
            case "Белый" -> setColor(style, IndexedColors.WHITE.getIndex());
            case "Красный" -> setColor(style, IndexedColors.RED.getIndex());
            case "Голубой" -> setColor(style, IndexedColors.SKY_BLUE.getIndex());
        }
    }

    private void setColor(CellStyle style, short colorIndex) {
        style.setFillForegroundColor(colorIndex);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    private void formatCell(Workbook workbook, Cell cell, CellStyle style, String cellValue) {
        Font font = workbook.createFont();
        font.setFontName("Arial");
        int fontSize;
        if(cellValue.length() < 4) {
            fontSize = 24;
        } else {
            fontSize = 12;
        }

        font.setFontHeightInPoints((short) fontSize);

        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        cell.setCellValue(cellValue);
        int textLength = cellValue.length();
        if (textLength > 255) {
            while (textLength > 255 && fontSize > 1) {
                fontSize--;
                font.setFontHeightInPoints((short) fontSize);
                style.setFont(font);
                textLength = cellValue.length();
            }
        }

    }

}
