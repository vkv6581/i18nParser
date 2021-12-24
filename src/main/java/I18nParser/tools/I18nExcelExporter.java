package I18nParser.tools;

import I18nParser.constants.I18nParserOptions;
import I18nParser.vo.I18nFileVo;
import I18nParser.vo.I18nResourceVo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class I18nExcelExporter {
    private I18nFileVo i18nFileVo;
    List<String> subLangList;

    public I18nExcelExporter(I18nFileVo i18nFileVo) {
        this.i18nFileVo = i18nFileVo;
        this.subLangList = Arrays.asList(I18nParserOptions.SUB_LANG.split(";"));
    }

    public void export() {
        XSSFWorkbook xssfWb = null;
        XSSFSheet xssfSheet = null;
        XSSFRow xssfRow = null;
        XSSFCell xssfCell = null;

        try {
            int rowNo = 0; // 행의 갯수
            xssfWb = new XSSFWorkbook(); //XSSFWorkbook 객체 생성
            xssfSheet = xssfWb.createSheet("i18n list"); // 워크시트 이름 설정

            xssfRow = xssfSheet.createRow(rowNo++); // 행 객체 추가

            xssfCell = xssfRow.createCell((short) 0);
            xssfCell.setCellValue("key");

            xssfCell = xssfRow.createCell((short) 1);
            xssfCell.setCellValue(I18nParserOptions.DEFAULT_LANG);

            for (int i = 0; i < subLangList.size(); i++) {
                xssfCell = xssfRow.createCell((short) 2 + i);
                xssfCell.setCellValue(subLangList.get(i));
            }
            List<I18nResourceVo> resourceVoList = i18nFileVo.getResourceList();

            for (int j = 0; j < resourceVoList.size(); j++) {
                I18nResourceVo resourceVo = resourceVoList.get(j);

                xssfRow = xssfSheet.createRow(rowNo++);

                xssfCell = xssfRow.createCell((short) 0);
                xssfCell.setCellValue(resourceVo.getKey());

                xssfCell = xssfRow.createCell((short) 1);
                xssfCell.setCellValue(resourceVo.getValMap().get(I18nParserOptions.DEFAULT_LANG));

                for (int k = 0; k < subLangList.size(); k++) {
                    xssfCell = xssfRow.createCell((short) 2 + k);
                    xssfCell.setCellValue(resourceVo.getValMap().get(subLangList.get(k)));
                }
            }

            String excelPath = I18nParserOptions.SAVE_PATH + File.separator + i18nFileVo.getFileName() + ".xlsx";
            File file = new File(excelPath);
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            xssfWb.write(fos);
            if (fos != null) fos.close();

        } catch (Exception e) {
            System.out.println("excel export error : " + i18nFileVo.getFileName());
        }
    }
}
