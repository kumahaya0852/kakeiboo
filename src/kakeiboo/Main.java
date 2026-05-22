package kakeiboo;

import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception {
		//テスト用↓
		CSVdata data = new CSVdata("data/user.csv");
		
		/*Map<String, String> aaa = new LinkedHashMap<>();
		aaa.put("id", "114");
		aaa.put("name", "tado");
		aaa.put("age", "24");
		data.addList(aaa);*/
		
		
		List<Map<String, String>> list = data.findAll();
		list.forEach(System.out::println);

	}

}
