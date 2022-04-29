package com.example.cran.service.impl;


import com.example.cran.entity.BaseStation;
import com.example.cran.entity.Terminal;
import com.example.cran.service.IBaseStationService;
import com.example.cran.service.ICaseSetService;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.cran.service.ITerminalService;
import com.example.cran.utils.CoordinateTrans;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaseSetServiceImpl implements ICaseSetService {

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    @Autowired
    private IBaseStationService baseStationService;

    @Autowired
    private ITerminalService terminalService;

    @Override
    public boolean CaseSet() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        //添加基站表格到数据库
        //首先清空基站表格
        boolean bsDelete = baseStationService.deleteAll();
        String baseStationPath = "src\\main\\java\\com\\example\\cran\\assets\\caseSet\\base_station.xls";
        String[][] bsResult = getData(baseStationPath);
        List<Object> bsList = getObject(bsResult, "com.example.cran.entity.BaseStation");
        boolean bsSave = false;
        for(Object bs:bsList){
            BaseStation baseStation = (BaseStation) bs;
            baseStation.setX(CoordinateTrans.longitudeToX(baseStation.getLongitude()));
            baseStation.setY(CoordinateTrans.latitudeToY(baseStation.getLatitude()));
            bsSave=baseStationService.save(baseStation);
            if(!bsSave) break;
        }
        boolean flagBs = bsDelete&&bsSave;

        //添加终端表格到数据库
        //首先清空终端表格
        boolean terminalDelete = terminalService.deleteAll();
        String terminalPath = "src\\main\\java\\com\\example\\cran\\assets\\caseSet\\terminal.xls";
        String[][] terminalResult = getData(terminalPath);
        List<Object> terminalList = getObject(terminalResult, "com.example.cran.entity.Terminal");
        boolean terminalSave = false;
        for(Object tm:terminalList){
            Terminal terminal = (Terminal) tm;
            terminal.setX(CoordinateTrans.longitudeToX(terminal.getLongitude()));
            terminal.setY(CoordinateTrans.latitudeToY(terminal.getLatitude()));
            terminalSave = terminalService.save(terminal);
            if(!terminalSave) break;
        }
        boolean flagTerminal = terminalDelete&&terminalSave;

        return flagTerminal&&flagBs;
    }

    @Override
    public String[][] getData(String path) throws IOException {
        File file = new File(path);
        List<String[]> result = new ArrayList<String[]>();
        int rowSize = 0;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFCell cell = null;
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);

            // System.out.println("<=lastrow"+st.getLastRowNum());
            for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getPhysicalNumberOfCells();//获取不为空的列的个数
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                //System.out.println("列数为"+rowSize);
                String[] values = new String[rowSize];
                Arrays.fill(values, "");
                boolean hasValue = false;
                // System.out.println("rowindex"+rowIndex);
                // System.out.println("<lastcolumn"+row.getPhysicalNumberOfCells());
                for (short columnIndex = 0; columnIndex < row.getPhysicalNumberOfCells(); columnIndex++) {
                    // System.out.println(columnIndex);
                    String value = "";
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                value = cell.getStringCellValue();
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    if (date != null) {
                                        value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                    } else {
                                        value = "";
                                    }
                                } else {
                                    value = new DecimalFormat("#.####").format(cell.getNumericCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                // 导入时如果为公式生成的数据则无值
                                if (!cell.getStringCellValue().equals("")) {
                                    value = cell.getStringCellValue();
                                } else {
                                    value = cell.getNumericCellValue() + "";
                                }
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                value = "";
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                                break;
                            default:
                                value = "";
                        }
                    }
                    if (columnIndex == 0 && value.trim().equals("")) {
                        break;
                    }
                    values[columnIndex] = rightTrim(value);
                    hasValue = true;
                }
                if (hasValue) {
                    result.add(values);
                    // System.out.println(values);
                }
            }
        }
        in.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) result.get(i);
        }
        return returnArray;
    }

    public static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20) {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }

    @Override
    public List<Object> getObject(String[][] result, String classPath) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        List<Object> resultSet = new ArrayList<>(); //结果集

        Class clz = Class.forName(classPath);  //获取对应实体的Class类
        Constructor constructor = clz.getConstructor(); //获取对应实体的构造方法
        Field[] fields = clz.getDeclaredFields();  //获取对应实体类对象的所有属性
        //遍历所有数据行，将所有数据行转化成对应的对象并将数据传入对象，保存在结果集中
        for(int j=1;j<result.length;j++){
            Object o = constructor.newInstance();  //获取对应实体类对象
            for (Field field : fields) {
                field.setAccessible(true);
                String attrName = field.getName() ;  //获取属性名
                String typeAll =  String.valueOf(field.getGenericType().toString());  //获取属性类型
                if(typeAll.equals("long")) continue;
                String[] typeSplit = typeAll.split("\\.");
                String type = typeSplit[typeSplit.length-1];
                int i=-1;
                for (i=0;i<result[0].length;i++){
                    if(result[0][i].toLowerCase().equals(attrName.length()==1?attrName.toLowerCase():humpToLine(attrName))) break;
                }

                String methodName = "set" + attrName.substring(0,1).toUpperCase()+attrName.substring(1);
                Method method = null;
                if(type.equals("Integer")){
                    method = clz.getMethod(methodName, Integer.class);
                    method.invoke(o, Integer.valueOf(result[j][i]));
                } else if (type.equals("String") ){
                    method = clz.getMethod(methodName, String.class);
                    method.invoke(o, result[j][i]);
                } else if (type.equals("Double") ){
                    method = clz.getMethod(methodName, Double.class);
                    method.invoke(o, Double.valueOf(result[j][i]));
                }
            }
            resultSet.add(o);
        }

        return resultSet;
    }
    /**
     * 驼峰转下划线,最后转为大写
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString().toLowerCase();
    }
}
