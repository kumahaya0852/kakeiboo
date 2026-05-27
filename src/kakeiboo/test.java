/*package kakeiboo;

import java.util.LinkedHashMap;
import java.util.Map;

public class test {

	public static void main(String[] args) throws Exception {
		//テスト用↓入力内容date(日付)
		CSVdata aaa = new CSVdata("data/user.csv");

		Map<String, String> newData = new LinkedHashMap<>();
		newData.put("date", "2026-05-25");
		newData.put("amount", "1500");
		newData.put("memo", "昼食");
		newData.put("category", "食費");

		aaa.addList(newData);

		// 確認
		aaa.findAll().forEach(System.out::println);
		
		
		

	}

}*/
