package kakeiboo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVdata {
	static List<Map<String, String>> records = new ArrayList<>();
	String filePath;
	String[] headers; 
	
	public CSVdata(String filePath) throws IOException {
		this.filePath = filePath;
		load();
	}
	
	//csv読み込み
	private void load() {          
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line = br.readLine();
			if(line == null) return;
			headers = line.split(",");
				
			while((line = br.readLine()) != null) {
				String[] value = line.split(",");
				Map<String, String> row = new LinkedHashMap<>();
				for(int i = 0; i < headers.length; i++) {
					row.put(headers[i], i < value.length ? value[i] : "");
				}
				records.add(row);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//全取得
	public static List<Map<String, String>> findAll(){ 
		return Collections.unmodifiableList(records);
	}
	
	//追加
	public void addList(Map<String, String> adddata) throws IOException{ 
		records.add(adddata);
		save();
	}
	
	//削除
	public boolean delete(int index) throws IOException {
		if(index < 0 || index >= records.size()) return false;
		records.remove(index);
		save();
		return true;
	}
	
	//保存
	public void save() throws IOException {
		if(headers == null) return; 
		
		try(PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
			pw.println(String.join(",", headers));
			for(Map<String, String> row : records) {
				pw.println(String.join(",", row.values()));
			}
		}
	}
	
	//条件検索
	public List<Map<String, String>> findBy(String column, String value) {
		List<Map<String, String>> result = new ArrayList<>();
		for(Map<String, String> row : records) {
			if(value.equals(row.get(column))) {
				result.add(row);
			}
		}
		return result;
	}
	
	//検索（カテゴリ）
	public List<Map<String, String>> findByCategory(String category) {
		return findBy("category", category);
	}
	
	//合計金額
	public int total(int num) {
		int sum = 0;
			switch(num) {
				case 1 -> {
					for(Map<String, String> row : findAll()) {
						if("食費".equals(row.get("category"))) {
							String amount = row.get("amount");
							sum = sum + Integer.parseInt(amount);
						}
					}
				}
				case 2 -> {
					
				}
				
				default -> {
				for(Map<String, String> row : findAll()) {
					sum += Integer.parseInt(row.get("amount"));
				}
			}
					
		} 
		return sum;
					
	}
				
}


