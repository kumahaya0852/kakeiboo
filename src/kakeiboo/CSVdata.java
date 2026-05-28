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
	
	//削除  idにしてるけどのち変更予定
	public boolean delete(String id) throws IOException {
		boolean removed = records.removeIf(r -> id.equals(r.get("id")));
		if (removed) save();
		return removed;
	}
	
	//保存(一応)
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

}
