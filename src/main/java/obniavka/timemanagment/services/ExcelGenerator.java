package obniavka.timemanagment.services;

import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.helper.AmountOfDays;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ExcelGenerator {
    private List<ReportDto> reports;
    private ResourceBundle labels;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List <ReportDto> reports) {
        labels = ResourceBundle.getBundle("i18n/labels", new Locale("uk"));
        this.reports = reports;
        workbook = new XSSFWorkbook();
    }
    private void writeHeader() {

        sheet = workbook.createSheet("Report");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        createCell(row, 0, labels.getString("REPORT.DAY"), style);
        createCell(row, 1, labels.getString("REPORT.DAYOFWEEK"), style);
        createCell(row, 2, labels.getString("REPORT.BEGIN"), style);
        createCell(row, 3, labels.getString("REPORT.END"), style);
        createCell(row, 4, labels.getString("REPORT.PAUSE"), style);
        createCell(row, 5, labels.getString("REPORT.TOTAL"), style);
        createCell(row, 6, labels.getString("TASK"), style);
        createCell(row, 7, labels.getString("REPORT.DESCRIPTION"), style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof LocalTime) {
            LocalTime localTime = (LocalTime) valueOfCell;
            cell.setCellValue(localTime.toString());
        } else if (valueOfCell instanceof LocalDate) {
            LocalDate localDate = (LocalDate) valueOfCell;
            LocalDateTime dateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
            cell.setCellValue(dateTime.toString());
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        }  else if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        }  else if (valueOfCell instanceof Double) {
            cell.setCellValue((Double) valueOfCell);
        }
        else {
            cell.setCellValue((String) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (ReportDto record: reports) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getWorkDay().getDayOfMonth(), style);
            createCell(row, columnCount++, labels.getString(record.getWorkDay().getDayOfWeek().toString()), style);
            createCell(row, columnCount++, record.getBegin(), style);
            createCell(row, columnCount++, record.getEnd(), style);
            createCell(row, columnCount++, record.getPause(), style);
            createCell(row, columnCount++, record.getAmountOfHours(), style);
            createCell(row, columnCount++, record.getTask()==null? "":record.getTask().getName(), style);
            createCell(row, columnCount++, record.getDescription(), style);
        }

        Row totalRow = sheet.createRow(rowCount);
        createCell(totalRow, 0, labels.getString("REPORT.TOTALSUM"), style);
        createCell(totalRow, 1, AmountOfDays.getSalaryPerMonth(reports), style);
    }
    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
