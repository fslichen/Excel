package evolution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import evolution.annotation.Alias;
import evolution.pojo.AnyPojo;

public class Excel {
	@Test
	public void run() {
		Workbook workbook = new XSSFWorkbook();
		List<AnyPojo> list = Arrays.asList(new AnyPojo("Chen", "M"), new AnyPojo("Ling", "F"));
		writeList2Sheet(workbook, "anySheet", list, AnyPojo.class);
		save(workbook, "/Users/chenli/Desktop/anyFile.xlsx");
	}
	
	public <T> void writeList2Sheet(Workbook workbook, String sheetName, List<T> ts, Class<T> clazz) {
		Sheet sheet = workbook.createSheet(sheetName);
		Field[] fields = clazz.getDeclaredFields();
		int i = 0;
		int j = 0;
		Row row = sheet.createRow(i++);
		for (Field field : fields) {
			Cell cell = row.createCell(j++);
			field.setAccessible(true);
			Alias alias = field.getAnnotation(Alias.class);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(alias != null ? alias.value() : field.getName());
		}
		for (T t : ts) {
			j = 0;
			row = sheet.createRow(i++);
			for (Field field : fields) {
				Cell cell = row.createCell(j++);
				try {
					Object fieldValue = field.get(t);
					Class<?> fieldType = field.getType();
					if (fieldType == String.class) {
						cell.setCellType(CellType.STRING);
						cell.setCellValue((String) fieldValue);
					} else if (Number.class.isAssignableFrom(fieldType)) {
						cell.setCellType(CellType.NUMERIC);
						cell.setCellValue(new Double(fieldValue + ""));
					}
				} catch (Exception e) {}
			}
		}
	}
	
	public boolean save(Workbook workbook, String filePath) {
		try {
			FileOutputStream out = new FileOutputStream(new File(filePath));
			workbook.write(out);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
