package com.zycx.frame.util.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


import com.zycx.frame.file.FileMan;
import com.zycx.frame.struts.GlobalParameters;
import com.zycx.frame.util.DPUtil;
import com.zycx.frame.util.FileUtil;





public class ExcelParser {
	
	public static final int MAX_PAGE_SIZE = 65536;
	
	public final static String CELL_TYPE_STRING 	 = "1";
	public final static String CELL_TYPE_NUMERIC 	 = "2";
	public final static String CELL_TYPE_DATETIME 	 = "3";
	public final static String FILE_TYPE_EXCEL_BAT 	 = "1";//批量导出多个xls文件
	public final static String FILE_TYPE_TXT_BAT	 = "2";//批量导出多个txt文件
	public final static String FILE_TYPE_TXT_SINGLE  = "3";//批量导出单个txt文件
	
	protected final static Logger log = Logger.getLogger(ExcelParser.class);
	
	/**
	 * get sheets
	 * @param in
	 * @return List
	 * @throws Exception
	 */
	public static List getSheets(InputStream in) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(in);
		in.close();
		Element book = document.getRootElement();
		return book.getChildren();
	}
	
	/**
	 * get sheets
	 * @param path
	 * @param xml
	 * @return List
	 * @throws Exception
	 */
	public static List getSheets(String path, String xml) throws Exception {
		String excelFile = path + xml;
		
		List sheets = getSheets(FileUtil.getClassResourceStream(excelFile));

		ArrayList list = new ArrayList();
		list.addAll(sheets);

		return sheets;
	}
	
	/**
	 * get sheets
	 * @param Lists
	 * @return List
	 * @throws Exception
	 */
	public static List getSheets(List[] isheets) throws Exception {
		Document document = getDocument(isheets);
		return document.getRootElement().getChildren();
	}
	
	/**
	 * get document
	 * @param isheets
	 * @return Document
	 * @throws Exception
	 */
	private static Document getDocument(List[] isheets) throws Exception {
		Element root = new Element("workbook");
		Document document = new Document(root);
		for (int i=0; i<isheets.length; i++) {
			List isheet = isheets[i];
			
			Element sheet = new Element("sheet");
			sheet.setAttribute("name", "Sheet" + (i + 1));
			sheet.setAttribute("desc", "Sheet" + (i + 1));
			root.addContent(sheet);
			
			Element header = new Element("header");
			header.setAttribute("isshow", "true");
			header.setAttribute("height", "300");
			sheet.addContent(header);
			
			for (int j=0; j<isheet.size(); j++) {
				Map icell = (Map) isheet.get(j);
				
				Element cell = new Element("cell");

				String[]  icellnames = (String[]) icell.keySet().toArray(new String[0]);
				 Arrays.sort(icellnames);
				
						
						
				for (int k=0; k<icellnames.length; k++) {
					cell.setAttribute(icellnames[k], (String) icell.get(icellnames[k]));
				}
				header.addContent(cell);
			}
		}
		return document;
	}
	

	
	/**
 	 * import excel
 	 * @param bd
 	 * @param xml
 	 * @param excel
 	 * @return List[]
  	 * @throws Exception
 	 */
	public static List[] importExcel( String xml, String excel) throws Exception {
		return importExcel( xml, new FileInputStream(excel));
	}


	/**
 	 * import excel
 	 * @param bd
 	 * @param xml
 	 * @param input
 	 * @return List[]
  	 * @throws Exception
 	 */
	public static List[] importExcel( String xml, InputStream input) throws Exception {
		List sheets = getSheets("resources/imptpl/", xml);
		return importExcel( sheets, input, 0, 0);
	}
	
	public static List[] importExcel(String xml, InputStream input, int pos_x, int pos_y) throws Exception {
		List sheets = getSheets("resources/imptpl/", xml);
		return importExcel(sheets, input, pos_x, pos_y);
	}
	
	/**
 	 * import excel
 	 * @param bd
 	 * @param isheets
 	 * @param input
 	 * @return List[]
  	 * @throws Exception
 	 */
	public static List[] importExcel( List[] isheets, InputStream input) throws Exception {
		List sheets = getSheets(isheets);
		return importExcel( sheets, input, 0, 0);
	}
	
	/**
 	 * import excel
 	 * @param bd
 	 * @param xml
 	 * @param input
 	 * @return List[]
  	 * @throws Exception
 	 */
	private static List[] importExcel( List sheets, InputStream input, int pos_x, int pos_y) throws Exception {
	
		
		POIFSFileSystem fs = new POIFSFileSystem(input);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		
		List[] datasets = new List[sheets.size()];
		for (int i=0; i<datasets.length; i++) {
			HSSFSheet worksheet = workbook.getSheetAt(i);
			datasets[i] = new ArrayList();
			
			Element header = ((Element) sheets.get(i)).getChild("header");
			boolean isshow = Boolean.valueOf(header.getAttributeValue("isshow")).booleanValue();
			
			List cells = header.getChildren();
			for (int j = pos_y; j < worksheet.getPhysicalNumberOfRows() + pos_y; j++) {
				if (j == pos_y && isshow) continue;
				
				HSSFRow workrow = worksheet.getRow(j);
				if (workrow == null) continue;
				
				Map data = new HashMap();
				StringBuffer error = new StringBuffer();
				
				for (int k = pos_x; k < cells.size() + pos_x; k++) {
					Element cell = (Element) cells.get(k);
					String cell_name = cell.getAttributeValue("name");
					
//					HSSFCell workcell = workrow.getCell(k);
					HSSFCell workcell = workrow.getCell(k);
					if (workcell == null) {
						String value = data.get(cell_name) == null ? "": data.get(cell_name).toString();
						error.append(Validate.verifyCell( cell, value));
						continue;
					}
					
					int cell_type = workcell.getCellType();
					switch (cell_type) {
						case HSSFCell.CELL_TYPE_STRING :
							String cell_value = workcell.getStringCellValue().trim();
							if (!"".equals(cell_value)) data.put(cell_name, cell_value);
						 	break;
						case HSSFCell.CELL_TYPE_NUMERIC :
							data.put(cell_name, String.valueOf(DPUtil.formatDecimal("#.##", workcell.getNumericCellValue())));
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN :
							data.put(cell_name, String.valueOf(workcell.getBooleanCellValue()));
							break;
						case HSSFCell.CELL_TYPE_BLANK :
							break;
						case HSSFCell.CELL_TYPE_FORMULA :
							data.put(cell_name, String.valueOf(workcell.getCellFormula()));
							break;
						case HSSFCell.CELL_TYPE_ERROR :
							data.put(cell_name, String.valueOf(workcell.getErrorCellValue()));
							break;
					}
					String value = data.get(cell_name) == null ? "": data.get(cell_name).toString(); 
					error.append(Validate.verifyCell( cell,  value));
				}
				
				if (data.size() != 0) {
					data.put("IMPORT_RESULT", String.valueOf(error.length() == 0));
					data.put("IMPORT_ERROR", error.toString());
					datasets[i].add(data);
				}
			}
		}
		return datasets;
	}
	

	
	/**
	 * export excel
	 * @param xml
	 * @param excel
	 * @param datasets
	 * @throws Exception
	 */
	public static InputStream exportExcel( String xml, String excel, List[] datasets) throws Exception {
		List sheets = getSheets("resources/export/", xml);
		return exportExcel(sheets, excel, datasets, 0, 0);
	}
	
	
	public static InputStream exportExcel(String xml, String excel,List[] datasets,HttpServletResponse resp) throws Exception
	{
		List sheets = getSheets("resources/export/", xml);
		 return exportExcel(sheets, excel, datasets, 0, 0);
	}

	
	/**
	 * export excel
	 * @param bd
	 * @param isheets
	 * @param excel
	 * @param datasets
	 * @throws Exception
	 */
	public static void exportExcel( List[] isheets, String excel, List[] datasets) throws Exception {
		List sheets = getSheets(isheets);
		exportExcel( sheets, excel, datasets, 0, 0);
	}
	
	

	/**
	 * write excel
	 * @param bd
	 * @param xml
	 * @param full_name
	 * @param datasets
	 * @return File
	 * @throws Exception
	 */
	public static File writeExcel( String xml, String full_name, List[] datasets) throws Exception {
		return writeExcel(xml, full_name ,null, datasets);
	}
	
	/**
	 * write excel
	 * @param bd
	 * @param isheets
	 * @param full_name
	 * @param datasets
	 * @return File
	 * @throws Exception
	 */
	public static File writeExcel( List[] isheets, String full_name, List[] datasets) throws Exception {
		return writeExcel(isheets, full_name ,null, datasets);
	}

	/**
	 * write excel
	 * @param bd
	 * @param xml
	 * @param full_name
	 * @param real_name
	 * @param datasets
	 * @return File
	 * @throws Exception
	 */
	public static File writeExcel( String xml, String full_name, String real_name, List[] datasets) throws Exception {
		return writeExcel(xml, full_name, real_name, datasets, null);
	}
	
	/**
	 * write excel
	 * @param bd
	 * @param isheets
	 * @param full_name
	 * @param real_name
	 * @param datasets
	 * @return File
	 * @throws Exception
	 */
	public static File writeExcel( List[] isheets, String full_name, String real_name, List[] datasets) throws Exception {
		return writeExcel(isheets, full_name, real_name, datasets, null);
	}

	/**
	 * write excel
	 * @param bd
	 * @param sheets
	 * @param full_name
	 * @param real_name
	 * @param datasets
	 * @param sheets_cells
	 * @return File
	 * @throws Exception
	 */
	public static File writeExcel( String xml, String full_name, String real_name, List[] datasets, List[] sheets_cells) throws Exception {
		List sheets = getSheets("resources/export/", xml);
		return writeExcel(sheets, full_name, real_name, datasets, sheets_cells, 0, 0);
	}
	
	
	/**
	 * write txt
	 * @param bd
	 * @param xml
	 * @param full_name
	 * @param real_name
	 * @param datasets
	 * @param sheets_cells
	 * @return
	 * @throws Exception
	 */
	public static File writeTxt( String xml, String full_name, String real_name, List[] datasets, List[] sheets_cells, String file_type) throws Exception {
		List sheets = getSheets("resources/export/", xml);
		return writeFile( sheets, full_name, real_name, datasets, sheets_cells, file_type, 0, 0);
	}
	
	/**
	 * write excel
	 * @param bd
	 * @param usheets
	 * @param full_name
	 * @param real_name
	 * @param datasets
	 * @param sheets_cells
	 * @return File
	 * @throws Exception
	 */
	public static File writeExcel( List[] isheets, String full_name, String real_name, List[] datasets, List[] sheets_cells) throws Exception {
		List sheets = getSheets(isheets);
		return writeExcel(sheets, full_name, real_name, datasets, sheets_cells, 0, 0);
	}
	
	/**
	 * write excel
	 * @param bd
	 * @param sheets
	 * @param full_name
	 * @param real_name
	 * @param datasets
	 * @param sheets_cells
	 * @return File
	 * @throws Exception
	 */
	private static File writeExcel( List sheets, String full_name, String real_name, List[] datasets, List[] sheets_cells, int pos_x, int pos_y) throws Exception {
		return writeFile(sheets, full_name, real_name, datasets, sheets_cells, ExcelParser.FILE_TYPE_EXCEL_BAT, pos_x, pos_y);
	}
	
	private static File writeFile(List sheets, String full_name, String real_name, List[] datasets, List[] sheets_cells, String file_type, int pos_x, int pos_y) throws Exception {
		if (real_name == null) real_name = FileMan.getFileName(full_name);
		String main_name = real_name.indexOf(".") == -1 ? real_name : real_name.substring(0, real_name.indexOf("."));
		File file = new File(full_name);
		System.out.println(file.getAbsolutePath());
		
		OutputStream out = new FileOutputStream(file);
		ZipOutputStream zipout = new ZipOutputStream(out);
		String file_subfix = FILE_TYPE_EXCEL_BAT.equals(file_type) ? ".xls" : ".txt";
		
		for (int i=0; i<datasets.length; i++) {
				List dataset = datasets[i];
		
				String store_path = GlobalParameters.UPLOAD_FILE_PATH_TEMP  +  "/" + dataset.hashCode();
				File store_dir = new File(store_path);
				if(!store_dir.exists())
				{
					store_dir.mkdirs();
				}
				
				System.out.println(store_dir.getAbsolutePath());
				if (store_dir.exists() && store_dir.isDirectory()) 
				{
					if (FILE_TYPE_EXCEL_BAT.equals(file_type) || FILE_TYPE_TXT_BAT.equals(file_type)) {
						int page = dataset.size()/GlobalParameters.EXCEL_PAGE_COUNT ;
						if(dataset.size()%GlobalParameters.EXCEL_PAGE_COUNT >0)
						{
							page = page + 1;
						}
						
						for (int j=0; j<page; j++) 
						{

								String file_realname = store_path + "/" + main_name + "_" + (i + 1) + "_" + (j + 1) + file_subfix;
								int lastIndex = dataset.size()%GlobalParameters.EXCEL_PAGE_COUNT ==0? GlobalParameters.EXCEL_PAGE_COUNT : dataset.size()%GlobalParameters.EXCEL_PAGE_COUNT ;
								List sublist = dataset.subList(j*GlobalParameters.EXCEL_PAGE_COUNT, lastIndex);
								File real_file = null;
								if (FILE_TYPE_TXT_BAT.equals(file_type)) {
									real_file = new File(file_realname);
									if(!real_file.exists()){
										real_file.createNewFile();
									}
									FileWriter fw = new FileWriter(real_file);
									writeTxtBySingle(fw, sublist, (String[])sheets_cells[0].toArray());
									fw.flush();
									fw.close();
								} else if (FILE_TYPE_EXCEL_BAT.equals(file_type)) {
									real_file = writeExcelBySingle( file_realname, sheets, new List[] { sublist }, new List[] { dataset }, sheets_cells, pos_x, pos_y);
								}
								
								zipout.putNextEntry(new ZipEntry(real_file.getName()));
								writeFile(real_file, zipout);
								
								
								real_file.delete();
							}
						}
					} else if (FILE_TYPE_TXT_SINGLE.equals(file_type)) {
						String file_realname = store_path + "/" + main_name + file_subfix;
						File real_file = new File(file_realname);
						
						if(!real_file.exists()){
							real_file.createNewFile();
						}
						FileWriter fw = new FileWriter(real_file);
						for (int j=0; j<GlobalParameters.EXCEL_PAGE_COUNT; j++) {
							File store_file = new File(store_path + "/" + j);
							if (store_file.exists()) {
								List storeset = (List) FileMan.readObject(store_file);
								writeTxtBySingle(fw, storeset, (String[])sheets_cells[0].toArray());
								store_file.delete();
							}
						}
						fw.flush();
						fw.close();
						zipout.putNextEntry(new ZipEntry(real_file.getName()));
						writeFile(real_file, zipout);
						real_file.delete();
					}
					store_dir.delete();
				}
			 
		zipout.close();
		
		return file;
	}
	
	
	/**
	 * writer excel by import
	 * @param bd
	 * @param xml
	 * @param datasets
	 * @return String
	 * @throws Exception
	 */
	public static String writeExcelByImport( String xml, List[] datasets) throws Exception {
		return writeExcelByImport( xml, datasets, null, false);
	}
	
	
	/**
	 * write excel by import
	 * @param bd
	 * @param xml
	 * @param datasets
	 * @param record_name
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public static String writeExcelByImport( String xml, List[] datasets, String record_name, boolean record) throws Exception {
		String file_name = DPUtil.getUniqeName();
		String file_path = GlobalParameters.UPLOAD_FILE_PATH + "/" + FileMan.getUploadPath(FileMan.UPLOAD_TYPE_IMPORT);
		String full_name = file_path + "/" + file_name;
		
		List sheets = getSheets("resources/export/", xml);
		uploadExcelBySingle(full_name, sheets, datasets, null, null, record_name == null ? file_name : record_name, record);
		
		return file_name;
	}

	/**
	 * write excel by import
	 * @param bd
	 * @param sheets
	 * @param datasets
	 * @param record_name
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public static String writeExcelByImport( List sheets, List[] datasets, String record_name, boolean record) throws Exception {
		String file_name = DPUtil.getUniqeName();
		String file_path = GlobalParameters.UPLOAD_FILE_PATH + "/" + FileMan.getUploadPath(FileMan.UPLOAD_TYPE_IMPORT);
		String full_name = file_path + "/" + file_name;
		
		uploadExcelBySingle(full_name, sheets, datasets, null, null, record_name == null ? file_name : record_name, record);
		
		return file_name;
	}
	
	/**
	 * get failed link by import
	 * @param file_id
	 * @param file_name
	 * @return String
	 * @throws Exception
	 */
	public static String getFailedLinkByImport(String file_id, String file_name) throws Exception {
		return "<a href='attach?file_name=" + file_name + "&file_path=" +GlobalParameters.UPLOAD_FILE_PATH + "/" + FileMan.getUploadPath(FileMan.UPLOAD_TYPE_IMPORT) + "/" + file_id + "&file_source=domain' target='printframe'>" + file_name + "</a>";	
	}
	
	/**
	 * get work book
	 * @param bd
	 * @param xml
	 * @param datasets
	 * @param orgdatasets
	 * @param sheets_cells
	 * @return HSSFWorkbook
	 * @throws Exception
	 */
	private static HSSFWorkbook getWorkbook( List sheets, List[] datasets, List[] orgdatasets, List[] sheets_cells, int pos_x, int pos_y) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFDataFormat format = workbook.createDataFormat();
		
		List allsheets = new ArrayList();
		List alldatasets = new ArrayList();
		
		for (int i=0; i<datasets.length; i++) {
			int sheetCount = (int) Math.ceil((double) datasets[i].size() / (double) MAX_PAGE_SIZE);
			List dataset = datasets[i];
			List orgdataset = orgdatasets == null ? dataset : orgdatasets[i];
			for (int j=0; j<sheetCount; j++) {
				Map allsheet = new HashMap();
				allsheet.put("SHEET_DATA", sheets.get(i));
				
				if (sheets_cells != null) {
					allsheet.put("SHEET_CELLS", sheets_cells[i]);
				}
				allsheets.add(allsheet);
				alldatasets.add(dataset.subList(j * MAX_PAGE_SIZE, (j + 1) * MAX_PAGE_SIZE > dataset.size() ? dataset.size() : (j + 1) * MAX_PAGE_SIZE));
			}
		}
		
		for (int i=0; i<allsheets.size(); i++) {
			Map allsheet = (Map) allsheets.get(i);
			Element sheet = (Element) allsheet.get("SHEET_DATA");
			List sheet_cells = (List) allsheet.get("SHEET_CELLS");
			
			Map transactKeys = (Map) allsheet.get("TRANSACT_KEYS");
			
			String sheet_desc = sheet.getAttributeValue("desc");
			
			HSSFSheet worksheet = workbook.createSheet();
			workbook.setSheetName(i, sheet_desc + "_" + (i + 1));
			
			
			Element header = sheet.getChild("header");
			boolean isshow = Boolean.valueOf(header.getAttributeValue("isshow")).booleanValue();

			int rows = 0;
		  	while (rows < pos_y) {
		  		HSSFRow rowH = worksheet.createRow(rows++);
		  		
		  		short indexH = 0;
		  		List cells = header.getChildren();
		  		for (short h = 0; h < cells.size() + pos_x; h++) {					
					HSSFCell cellH = rowH.createCell(indexH++);
					cellH.setCellValue("");
				}
			}
			
			if (isshow) {
				HSSFCellStyle styleH = workbook.createCellStyle();
				styleH.setWrapText(true);
				styleH.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				styleH.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				HSSFFont fontH = workbook.createFont();
				fontH.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				fontH.setColor(HSSFColor.WHITE.index);
				styleH.setFont(fontH);
				styleH.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
				styleH.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				styleH.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				styleH.setBottomBorderColor(HSSFColor.WHITE.index);
				styleH.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				styleH.setLeftBorderColor(HSSFColor.WHITE.index);
				styleH.setBorderRight(HSSFCellStyle.BORDER_THIN);
				styleH.setRightBorderColor(HSSFColor.WHITE.index);
				styleH.setBorderTop(HSSFCellStyle.BORDER_THIN);
				styleH.setTopBorderColor(HSSFColor.WHITE.index);
				
				HSSFRow rowH = worksheet.createRow(rows++);
				rowH.setHeight(Short.parseShort(header.getAttributeValue("height")));
				
				short indexH = 0;
				List cells = header.getChildren();
				for (short h = 0; h < cells.size() + pos_x; h++) {
					if (h < pos_x) {
						HSSFCell cellH = rowH.createCell(indexH++);
						cellH.setCellValue("");
					} else {
						Element cell = (Element) cells.get(h - pos_x);
						String cell_name = cell.getAttributeValue("name");
						if (sheet_cells != null && !sheet_cells.contains(cell_name)) {
							continue;
						}
						String cell_desc = cell.getAttributeValue("desc");
						String cell_width = cell.getAttributeValue("width");
						
						HSSFCell cellH = rowH.createCell(indexH);
//						cellH.setEncoding(HSSFCell.ENCODING_UTF_16);
						cellH.setCellValue(cell_desc);
						worksheet.setColumnWidth(indexH, Integer.parseInt(cell_width));
//						worksheet.setColumnWidth(indexH, Short.parseShort(cell_width));
						cellH.setCellStyle(styleH);
						
						indexH ++;	
					}
				}
			}
			
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);
			
			List styles = new ArrayList();
			
			List cells = header.getChildren();
			List dataset = (List) alldatasets.get(i);
			for (int j=0; j<dataset.size(); j++) {
				Map data = (Map) dataset.get(j);
				HSSFRow workrow = worksheet.createRow(rows++);
				
				short indexH = 0;
				for (short h = 0; h < cells.size() + pos_x; h++) {
					if (h < pos_x) {
						HSSFCell cellH = workrow.createCell(indexH++);
						cellH.setCellValue("");
					} else {
						Element cell = (Element) cells.get(h - pos_x);
						String cell_name = cell.getAttributeValue("name");
						if (sheet_cells != null && !sheet_cells.contains(cell_name)) {
							continue;
						}
						
						String cell_type = cell.getAttributeValue("type");
						String cell_align = cell.getAttributeValue("align");
						String cell_scale = cell.getAttributeValue("scale");
						String cell_format = cell.getAttributeValue("format");
						String cell_datasrc = cell.getAttributeValue("datasrc");
						String cell_dataid = cell.getAttributeValue("dataid");
						
						if (transactKeys != null && transactKeys.containsKey(cell_name)) {
							String transactValue = (String) transactKeys.get(cell_name);
							String[] transactParams = transactValue.split(";");
							cell_datasrc = transactParams[0];
							cell_dataid = transactParams[1];
						}
						
						String cell_value = (String) data.get(cell_dataid == null ? cell_name : cell_dataid);

						
						HSSFCell workcell = workrow.createCell(indexH);
						
						HSSFCellStyle style = null;
						if (j == 0) {
							style = workbook.createCellStyle();
							style.setFont(font);
							style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
							if (cell_align != null) style.setAlignment(Short.parseShort(cell_align));	
							styles.add(style);
						} else {
							style = (HSSFCellStyle) styles.get(indexH - pos_x);
						}
						
						if (cell_value != null) {					
							if (CELL_TYPE_STRING.equals(cell_type)) {	
//								workcell.s(HSSFCell.ENCODING_UTF_16);
								workcell.setCellValue(cell_value);
							}
							if (CELL_TYPE_NUMERIC.equals(cell_type)) {
								if (cell_format != null) style.setDataFormat(format.getFormat(cell_format));
								workcell.setCellValue(cell_scale == null ? Double.parseDouble(cell_value) : Double.parseDouble(cell_value) / Double.parseDouble(cell_scale));
							}
							if (CELL_TYPE_DATETIME.equals(cell_type)) {
								workcell.setCellValue(cell_format == null ? cell_value : DPUtil.decodeTimestamp(cell_format, cell_value));
							}
							workcell.setCellStyle(style);
						}
						indexH ++;
					}
				}
			}
		}
		return workbook;
	}
	
	/**
	 * export excel
	 * @param bd
	 * @param response
	 * @param sheets
	 * @param real_name
	 * @param datasets
	 * @throws Exception
	 */
	private static InputStream exportExcel( List sheets, String real_name, List[] datasets, int pos_x, int pos_y) throws Exception {
		String file_path = GlobalParameters.UPLOAD_FILE_PATH + "/" + FileMan.getUploadPath(FileMan.UPLOAD_TYPE_EXPORT);
		String full_name = file_path + "/" + DPUtil.getUniqeName();
		String main_name = real_name.indexOf(".") == -1 ? real_name : real_name.substring(0, real_name.indexOf("."));
//		
		File file = writeExcel(sheets, full_name, real_name, datasets, null, pos_x, pos_y);
		InputStream ins =  FileMan.downFile(full_name, real_name);
//		FileMan.downFile(bd == null ? response : bd.getResponse(), full_name, main_name + ".zip");
		file.delete();
		return ins;
	}
	
	private static void exportExcel( List sheets, String real_name, List[] datasets, int pos_x, int pos_y,HttpServletResponse response) throws Exception {
		String file_path = GlobalParameters.UPLOAD_FILE_PATH + "/" + FileMan.getUploadPath(FileMan.UPLOAD_TYPE_EXPORT);
		String full_name = file_path + "/" + DPUtil.getUniqeName();
		String main_name = real_name.indexOf(".") == -1 ? real_name : real_name.substring(0, real_name.indexOf("."));
//		
		File file = writeExcel(sheets, full_name, real_name, datasets, null, pos_x, pos_y);
		FileMan.downFile(response, full_name, main_name + ".zip");
		file.delete();
	}
	

	
	/**
	 * write excel by single
	 * @param bd
	 * @param full_name
	 * @param sheets
	 * @param excel
	 * @param datasets
	 * @param orgdatasets
	 * @param sheets_cells
	 * @return File
	 * @throws Exception
	 */
	private static File writeExcelBySingle( String full_name, List sheets, List[] datasets, List[] orgdatasets, List[] sheets_cells) throws Exception {
		HSSFWorkbook workbook = getWorkbook( sheets, datasets, orgdatasets, sheets_cells, 0, 0);
		
		File file = new File(full_name);
		
		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.close();
		
		return file;
	}
	
	/**
	 * write excel by single, specify position x and y
	 * @param bd
	 * @param full_name
	 * @param sheets
	 * @param excel
	 * @param datasets
	 * @param orgdatasets
	 * @param sheets_cells
	 * @return File
	 * @throws Exception
	 */
	private static File writeExcelBySingle( String full_name, List sheets, List[] datasets, List[] orgdatasets, List[] sheets_cells, int pos_x, int pos_y) throws Exception {
		HSSFWorkbook workbook = getWorkbook( sheets, datasets, orgdatasets, sheets_cells, pos_x, pos_y);
		
		File file = new File(full_name);
		file.createNewFile();
		System.out.println("====1111=====" + file.getAbsolutePath());
		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.close();
		
		return file;
	}
	
	/**
	 * write txt by single
	 * @param bd
	 * @param file_path
	 * @param full_name
	 * @param dataset
	 * @param sheets_cells
	 * @return
	 * @throws Exception
	 */
	private static void writeTxtBySingle( FileWriter fw, List dataset, String[] sheets_cells) throws Exception {
		int datacnt = dataset.size();
		for (int i=0; i<datacnt; i++) {
			Object data = dataset.get(i);
			if (data instanceof Map) {
				for (int j = 0; j < sheets_cells.length; j++) 
				{
					String value = ((Map)data).get(sheets_cells[j]) == null? "" : ((Map)data).get(sheets_cells[j]).toString(); 
					
					fw.append( value + "\t");
				}
			} else {
				fw.append(data.toString() + "\t");
			}
			fw.append("\r\n");
		}
	}
	
	/**
	 * upload excel by single
	 * @param bd
	 * @param full_name
	 * @param sheets
	 * @param excel
	 * @param datasets
	 * @param orgdatasets
	 * @param sheets_cells
	 * @throws Exception
	 */
	private static void uploadExcelBySingle( String full_name, List sheets, List[] datasets, List[] orgdatasets, List[] sheets_cells) throws Exception {
		uploadExcelBySingle( full_name, sheets, datasets, orgdatasets, sheets_cells, null, false);
	}
	
	/**
	 * upload excel by single
	 * @param bd
	 * @param full_name
	 * @param sheets
	 * @param datasets
	 * @param orgdatasets
	 * @param sheets_cells
	 * @param record
	 * @throws Exception
	 */
	private static void uploadExcelBySingle( String full_name, List sheets, List[] datasets, List[] orgdatasets, List[] sheets_cells, String record_name, boolean record) throws Exception {
		File file = writeExcelBySingle(full_name + "_temp", sheets, datasets, orgdatasets, sheets_cells);
		/*if (file != null) {
			FileUtil fileutil = new FileUtil();
			fileutil.uploadFile(new FileInputStream(file), full_name);
			
			if (record) {
				 insert file info 
				BaseEntity entity = new BaseEntity(bd, BaseFactory.CENTER_CONNECTION_NAME);
				String file_id = entity.getSequence("SEQ_FILE_ID");
				String file_type = FileMan.UPLOAD_TYPE_EXPORT_FAIL;
				String file_kind = FileMan.UPLOAD_KIND_USER;
				Map filedata = new HashMap();
				filedata.put("FILE_ID", file_id);
				filedata.put("FILE_TYPE", file_type);
				filedata.put("FILE_KIND", file_kind);
				filedata.put("FILE_NAME", record_name);
				filedata.put("FILE_PATH", full_name);
				filedata.put("FILE_SIZE", String.valueOf(file.length()));
				filedata.put("CREA_STAFF", bd.getContext().getStaffId());
				filedata.put("CREA_TIME", entity.getSysTime());
				entity.insert("TD_M_FILE", filedata);
			}
			
			file.delete();
		}*/
	}
	
	/**
	 * write file
	 * @param file
	 * @param out
	 * @param full_name
	 * @param real_name
	 * @param xml
	 * @param dataset
	 * @return File
	 * @throws Exception
	 */
	private static void writeFile(File file, OutputStream zipout) throws Exception {
		FileInputStream in = new FileInputStream(file);
		FileMan.writeInputToOutput(in, zipout, true);
		in.close();
	}
		
	/* ------------------------------- trash begin ------------------------------- */

	
	/**
	 * write text
	 * @param bd
	 * @param xml
	 * @param full_name
	 * @param datasets
	 * @return File
	 * @throws Exception
	 */
	private static File writeText(String xml, String full_name, List[] datasets) throws Exception {
		File file = new File(full_name);
		
		FileOutputStream out = new FileOutputStream(file);
		writeContentByText(out,FileUtil.getClassResourceStream("export/" + xml), datasets);
		out.close();
		
		return file;
	}
	
	/**
	 * export text
	 * @param response
	 * @param xml
	 * @param file_name
	 * @param datasets
	 * @throws Exception
	 */
	private static void exportText(HttpServletResponse response, String xml, String file_name, List[] datasets) throws Exception {
		String file_path = GlobalParameters.UPLOAD_FILE_PATH + "/" + FileMan.getUploadPath(FileMan.UPLOAD_TYPE_TEMP);
		String full_name = file_path + "/" + DPUtil.getUniqeName();
		
		File file = writeText(full_name, xml, datasets);
		
	/*	OutputStream out = FileMan.getOutputStreamByDown(response, file_name);
		FileMan.writeInputToOutput(new FileInputStream(file), out);*/
		
		file.delete();
	}
	
	/**
	 * write content by text
	 * @param bd
	 * @param out
	 * @param in
	 * @param datasets
	 * @throws Exception
	 */
	private static void writeContentByText( OutputStream out, InputStream in, List[] datasets) throws Exception {
    	BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
    	
		List sheets = getSheets(in);
		
		for (int i=0; i<sheets.size(); i++) {
			Element sheet = (Element) sheets.get(i);
			Element header = sheet.getChild("header");
			boolean isshow = Boolean.valueOf(header.getAttributeValue("isshow")).booleanValue();
			if (isshow) {
				List cells = header.getChildren();
				for (short h=0; h<cells.size(); h++) {
					Element cell = (Element) cells.get(h);
					String cell_desc = cell.getAttributeValue("desc");
					
					writer.write(cell_desc);
					if (h != cells.size() - 1) writer.write(",");
				}
				writer.newLine();
			}
			
			List cells = header.getChildren();
			for (int j=0; j<datasets[i].size(); j++) {
				Map data = (Map) datasets[i].get(j);
				
				for (short h=0; h<cells.size(); h++) {
					Element cell = (Element) cells.get(h);
					String cell_name = cell.getAttributeValue("name");
					String cell_type = cell.getAttributeValue("type");
					String cell_scale = cell.getAttributeValue("scale");
					String cell_format = cell.getAttributeValue("format");
					String cell_datasrc = cell.getAttributeValue("datasrc");
					String cell_value = (String) (data.get(cell_name) == null ? "":data.get(cell_name)) ;
					
//					if (!"".equals(cell_value) && bd != null) {
//						if (cell_datasrc != null) {
//							cell_value = Validate.getTranslateValue(bd, data, cell_value, cell_datasrc);
//							if (cell_value == null) cell_value = "";
//						}
//					}
					
					if (CELL_TYPE_STRING.equals(cell_type)) {
						writer.write(cell_value);
					}
					if (CELL_TYPE_NUMERIC.equals(cell_type)) {
						writer.write(String.valueOf(cell_scale == null ? Double.parseDouble(cell_value) : Double.parseDouble(cell_value) / Double.parseDouble(cell_scale)));
					}
					if (CELL_TYPE_DATETIME.equals(cell_type)) {
						writer.write(cell_format == null ? cell_value :DPUtil.decodeTimestamp(cell_format, cell_value));
					}
					
					if (h != cells.size() - 1) writer.write(",");
				}
				
				writer.newLine();
			}
		}
		
		writer.close();
	}
	/* ------------------------------- trash end ------------------------------- */
	
	
	public static void main(String[] args)
	{
		try {
			List imp = ExcelParser.getSheets("resources/export/", "VipcustImport.xml");
			List[] custList = ExcelParser.importExcel("VipcustImport.xml","C:\\VipcustTemplet.xls");
			System.out.println(custList);
			ExcelParser.exportExcel("VipcustImport.xml", "test", custList);
			
			
//			String store_path = GlobalParameters.UPLOAD_FILE_PATH + "tmp" +  "/" + "984351481";
//			File store_dir = new File(store_path);
//			store_dir.mkdirs();
//			System.out.println(store_dir.getAbsolutePath());
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}