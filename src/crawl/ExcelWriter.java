package crawl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelWriter {

	private static String[] columns = { "Trade Up", "Trade Down", "Trade Ticker", "Trade Type", "Name", "Date",
			"Wall Text", "Karma Count", "Like Count", "Dislike Count" };
	private static List<Data> data = new ArrayList<>();

	
	//url & pages detail added manually here
	static {
		String url1 = "https://profit.ly/user/kroyrunner/trades?page=";
		String url2 = "&size=25";
		for (int i = 1; i <= 280; i++) {
			String url = url1 + i + url2;
			getInfo(url);
		}

	}
	
	//css selector 
	public static void getInfo(String txt) {
		try {
			final Document doc = Jsoup.connect(txt).get();
			ArrayList<Element> headerElements = doc.select("div.card-header");
			ArrayList<Element> bodyElements = doc.select("div.feed-info");
			ArrayList<Element> feedElements = doc.select("section.trade-feed-body");
			ArrayList<Element> reactionElements = doc.select("div.trade-detail");

			for (int i = 0; i < headerElements.size(); i++) {
				String tradeUp = headerElements.get(i).select("a.trade-up").text();
				String tradeDown = headerElements.get(i).select("a.trade-down").text();
				String tradeTicker = headerElements.get(i).select("a.trade-ticker").text();
				String tradeType = headerElements.get(i).select("span.trade-type").text();
				String name = bodyElements.get(i).select("a.name").text();
				String date = bodyElements.get(i).select("date").text();
				String wallText = feedElements.get(i).select("p.wall-text").text();
				String karmaCount = reactionElements.get(i).select("div#karma-data + span").text();
				String likeCount = reactionElements.get(i).select("div#vouch-data + span").text();
				String dislikeCount = reactionElements.get(i).select("a.vouch + span").text();

				data.add(new Data(tradeUp, tradeDown, tradeTicker, tradeType, name, date, wallText, karmaCount,
						likeCount, dislikeCount));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, InvalidFormatException {
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Data");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create Other rows and cells with data
		int rowNum = 1;
		for (Data d : data) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(d.getTradeUp());
			row.createCell(1).setCellValue(d.getTradeDown());
			row.createCell(2).setCellValue(d.getTradeTicker());
			row.createCell(3).setCellValue(d.getTradeType());
			row.createCell(4).setCellValue(d.getName());
			row.createCell(5).setCellValue(d.getDate());
			row.createCell(6).setCellValue(d.getWallText());
			row.createCell(7).setCellValue(d.getKarmaCount());
			row.createCell(8).setCellValue(d.getLikeCount());
			row.createCell(9).setCellValue(d.getDislikeCount());

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("kroyrunner.xlsx");
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();
	}
}
