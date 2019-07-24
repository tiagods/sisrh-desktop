import com.plkrhone.sisrh.controller.UtilsController;
import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.repository.helper.CandidatosImp;
import com.plkrhone.sisrh.util.storage.AWSStorage;
import com.plkrhone.sisrh.util.storage.Storage;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GerarExcelComHiperlinks extends UtilsController {
    public static void main(String[] args) {
       new GerarExcelComHiperlinks().teste();
    }

    public void teste() {
        try{
            loadFactory();
            EntityManager man = getManager();

            String sql ="select a.* FROM candidato a " +
                    "inner join usuario b on a.criado_por_id=b.id " +
                    "where a.data_modificacao>'2019-04-01' and b.id=3 order by a.id";
            Query query = man.createNativeQuery(sql,Candidato.class);

            AWSStorage storage = new AWSStorage();

            List<Candidato> candidatoes = query.getResultList();

            Workbook wb = new XSSFWorkbook();
            CreationHelper createHelper = wb.getCreationHelper();
            //cell style for hyperlinks
            //by default hyperlinks are blue and underlined
            CellStyle hlink_style = wb.createCellStyle();
            Font hlink_font = wb.createFont();
            hlink_font.setUnderline(Font.U_SINGLE);
            hlink_font.setColor(IndexedColors.BLUE.getIndex());
            hlink_style.setFont(hlink_font);
            Sheet sheet = wb.createSheet("candidatos");

            for(int i = 0; i<candidatoes.size(); i++) {
                System.out.println("Listando "+(i+1)+" de "+candidatoes.size());
                Candidato c = candidatoes.get(i);

                Row row = sheet.createRow(i);

                Cell cellData = row.createCell(0);
                cellData.setCellValue(c.getUltimaModificacao());
                Cell cellUsuario = row.createCell(1);
                cellUsuario.setCellValue(c.getCriadoPor().getNome());
                Cell cellCandidato = row.createCell(2);
                cellCandidato.setCellValue(c.getNome());

                if (c.getFormulario() != null && c.getFormulario().trim().length() > 0){
                    Cell cellCurriculo = row.createCell(3);
                    cellCurriculo.setCellValue("Abrir Curriculo");
                    Hyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
                    File file = storage.downloadFile(c.getFormulario());
                    link.setAddress("curriculo/"+file.getName());
                    cellCurriculo.setHyperlink(link);
                    cellCurriculo.setCellStyle(hlink_style);
                }
            }
            FileOutputStream out = new FileOutputStream("candidatos_sisrh.xlsx");
            wb.write(out);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            //close();
        }
    }
}
