package kakeiboo;

import java.util.List;
import java.util.Map;

public class search {

    private CSVdata csvData;

    public search(CSVdata csvData) {
        this.csvData = csvData;
    }


    public String searchByCategory(String category) {
        List<Map<String, String>> results = csvData.findByCategory(category);

        if (results.isEmpty()) {
            return "「" + category + "」の記録はありません。";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("【").append(category).append("】の検索結果（").append(results.size()).append("件）\n");
        sb.append("─────────────────────────\n");

        int total = 0;
        for (Map<String, String> row : results) {
            sb.append("日付  : ").append(row.get("date")).append("\n");
            sb.append("金額  : ").append(row.get("amount")).append("円\n");
            sb.append("内容  : ").append(row.get("memo")).append("\n");
            sb.append("─────────────────────────\n");

            try {
                total += Integer.parseInt(row.get("amount"));
            } catch (NumberFormatException e) {
                
            }
        }

        sb.append("合計金額 : ").append(total).append("円");
        return sb.toString();
    }
}
