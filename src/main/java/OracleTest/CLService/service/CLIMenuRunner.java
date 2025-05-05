package OracleTest.CLService.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CLIMenuRunner implements CommandLineRunner {

    private final AretoArticleCharacteristicsService characteristicsService;
    private final AretoArticleHeadService articleHeadService;
    private final AretoItemNumberClassNameService itemNumberClassNameService;
    private final AretoStaticCriteriaService staticCriteriaService;
    private final DomdetService domdetService;
    private final FimstService fimstService;
    private final O2kAretoQualityCriteriaService qualityCriteriaService;
    private final ParamService paramService;
    private final PartfilService partfilService;
    private final TimeComparisonService timeComparisonService;
    private final ArticleDataService articleDataService;
    private final FreshProduceService freshProduceService;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Areto Article Characteristics");
            System.out.println("2. Areto Article Head");
            System.out.println("3. Areto Item Number Class Name");
            System.out.println("4. Areto Static Criteria");
            System.out.println("5. Domdet");
            System.out.println("6. Fimst");
            System.out.println("7. O2K Quality Criteria");
            System.out.println("8. Param");
            System.out.println("9. Partfil");
            System.out.println("10. Compare UTC with Local Time");
            System.out.println("11. Article Data Service (Masterdata)");
            System.out.println("12. Masterdata for Fresh Produce  Article Query");
            System.out.println("0. Exit");
            System.out.print("Select option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> menuCharacteristics();
                case "2" -> menuArticleHead();
                case "3" -> menuItemNumberClassName();
                case "4" -> menuStaticCriteria();
                case "5" -> print(domdetService.getDomdetByPartSysTyp());
                case "6" -> print(fimstService.getFimstByFirmaWT());
                case "7" -> menuQualityCriteria();
                case "8" -> menuParam();
                case "9" -> print(partfilService.getByFirmaGX());
                case "10" -> print(timeComparisonService.compareUtcWithLocalTime());
                case "11" -> menuArticleData();
                case "12" -> menuMasterdataQuery();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }



    private void menuCharacteristics() {
        while (true) {
            System.out.println("\n--- Areto Article Characteristics ---");
            System.out.println("1. Get by Head UUID");
            System.out.println("2. Get by Type");
            System.out.println("3. Get by Group Description & Sort Index");
            System.out.println("4. Get by ID");
            System.out.println("5. Get by Sort Index");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> { System.out.print("Head UUID: "); print(characteristicsService.getArticleCharacteristicsByHeadUuid(scanner.nextLine())); }
                case "2" -> { System.out.print("Type: "); print(characteristicsService.getArticleCharacteristicsByTypeText(scanner.nextLine())); }
                case "3" -> {
                    System.out.print("Group Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Sort Index: ");
                    int sortIndex = Integer.parseInt(scanner.nextLine());
                    print(characteristicsService.getCharacteristicsByGroupAndSortIndex(desc, sortIndex));
                }
                case "4" -> { System.out.print("ID: "); print(characteristicsService.getArticleCharacteristicsById(scanner.nextLine())); }
                case "5" -> { System.out.print("Sort Index: "); print(characteristicsService.getArticleCharacteristicsBySortIndex(Integer.parseInt(scanner.nextLine()))); }
                case "0" -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void menuArticleHead() {
        while (true) {
            System.out.println("\n--- Areto Article Head ---");
            System.out.println("1. Get by WAGR");
            System.out.println("2. Get by Supplier ID");
            System.out.println("3. Get by Quality Class");
            System.out.println("4. Get by Item Number");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> { System.out.print("WAGR: "); print(articleHeadService.getAretoArticleByWagr(scanner.nextLine())); }
                case "2" -> { System.out.print("Supplier ID: "); print(articleHeadService.getAretoArticleBySupplierId(scanner.nextLine())); }
                case "3" -> { System.out.print("Quality Class: "); print(articleHeadService.getAretoArticleWithQualityClass(scanner.nextLine())); }
                case "4" -> { System.out.print("Item Number: "); print(articleHeadService.getAretoArticleHeadResults(scanner.nextLine())); }
                case "0" -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void menuItemNumberClassName() {
        while (true) {
            System.out.println("\n--- Areto Item Number Class Name ---");
            System.out.println("1. Get by Item Number");
            System.out.println("2. Get by Class Name");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> { System.out.print("Item Number: "); print(itemNumberClassNameService.getItemNumberClassNameByItemNumber(scanner.nextLine())); }
                case "2" -> { System.out.print("Class Name: "); print(itemNumberClassNameService.getItemNumberClassNameByClassName(scanner.nextLine())); }
                case "0" -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void menuStaticCriteria() {
        while (true) {
            System.out.println("\n--- Areto Static Criteria ---");
            System.out.println("1. Get by Class Name");
            System.out.println("2. Get by Class Code");
            System.out.println("3. Get by Name ID");
            System.out.println("4. Get DIA by Product Group ID");
            System.out.println("5. Get DIA by Internal ID");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> { System.out.print("Class Name: "); print(staticCriteriaService.getStaticCriteriaByClassName(scanner.nextLine())); }
                case "2" -> { System.out.print("Class Code: "); print(staticCriteriaService.getStaticCriteriaByClassCode(scanner.nextLine())); }
                case "3" -> { System.out.print("Name ID: "); print(staticCriteriaService.getStaticCriteriaByNameId(scanner.nextLine())); }
                case "4" -> { System.out.print("Product Group ID: "); print(staticCriteriaService.getDiaStaticCriteriaByProductGroupId(Integer.parseInt(scanner.nextLine()))); }
                case "5" -> { System.out.print("Internal ID: "); print(staticCriteriaService.getDiaStaticCriteriaByInternalId(Integer.parseInt(scanner.nextLine()))); }
                case "0" -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void menuQualityCriteria() {
        while (true) {
            System.out.println("\n--- O2K Quality Criteria ---");
            System.out.println("1. Get by TXN ID");
            System.out.println("2. Get by Schema ID");
            System.out.println("3. Get by Record ID");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> { System.out.print("TXN ID: "); print(qualityCriteriaService.getQualityCriteriaByTxnId(Integer.parseInt(scanner.nextLine()))); }
                case "2" -> { System.out.print("Schema ID: "); print(qualityCriteriaService.getQualityCriteriaBySchemaId(Integer.parseInt(scanner.nextLine()))); }
                case "3" -> { System.out.print("Record ID: "); print(qualityCriteriaService.getQualityCriteriaByRecordId(Integer.parseInt(scanner.nextLine()))); }
                case "0" -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void menuParam() {
        while (true) {
            System.out.println("\n--- PARAM Service ---");
            System.out.println("1. Get All Params");
            System.out.println("2. Get Params by Firma = 'EZ'");
            System.out.println("3. Get Params by param_art like '%MIN_DATUM%'");
            System.out.println("4. Describe PARAM table");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> print(paramService.getAllParams());
                case "2" -> print(paramService.getParamsByFirmaEZ());
                case "3" -> print(paramService.getParamsByMinDatum());
                case "4" -> print(paramService.describeParamTable());
                case "0" -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void menuArticleData() {
        while (true) {
            System.out.println("\n--- Article Data (Masterdata) ---");
            System.out.println("1. Get Article by ID");
            System.out.println("2. Get Article Supplier by UUID");
            System.out.println("3. Get All Suppliers");
            System.out.println("4. Get All Price References");
            System.out.println("5. Get All ARTLI_F");
            System.out.println("6. Get ARTST_A by artnr_A");
            System.out.println("7. Get ARTLI_A by artnr_A");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> { System.out.print("ID: "); print(articleDataService.getArticleById(Integer.parseInt(scanner.nextLine()))); }
                case "2" -> { System.out.print("UUID: "); print(articleDataService.getArticleSupplierByUuid(scanner.nextLine())); }
                case "3" -> print(articleDataService.getAllSuppliers());
                case "4" -> print(articleDataService.getAllPriceReferences());
                case "5" -> print(articleDataService.getAllArtliF());
                case "6" -> { System.out.print("artnr_A: "); print(articleDataService.getArtstByArtnrA(Integer.parseInt(scanner.nextLine()))); }
                case "7" -> { System.out.print("artnr_A: "); print(articleDataService.getArtliAByArtnrA(Integer.parseInt(scanner.nextLine()))); }
                case "0" -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }


    private void menuMasterdataQuery() {
        while (true) {
            System.out.println("\n--- Master data fpr Fresh Produce Service  Article Full Query ---");
            System.out.println("1. Get by ARTNR");
            System.out.println("2. Get by WAGR");
            System.out.println("3. Get by Quality Class");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.print("ARTNR: ");
                    int artnr = Integer.parseInt(scanner.nextLine());
                    print(freshProduceService.getByArtnr(artnr));
                }
                case "2" -> {
                    System.out.print("WAGR: ");
                    String wagr = scanner.nextLine();
                    print(freshProduceService.getByWagr(wagr));
                }
                case "3" -> {
                    System.out.print("Quality Class: ");
                    String quality = scanner.nextLine();
                    print(freshProduceService.getByQualityClass(quality));
                }
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }
    private void print(String data) {
        System.out.println("\n================== Result ==================\n" + data);
    }
}