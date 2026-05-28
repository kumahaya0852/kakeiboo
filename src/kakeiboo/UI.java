package kakeiboo;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UI extends JPanel{
	

	public  UI()  throws Exception {
		
		CardLayout card = new CardLayout();
		JPanel root = new JPanel(card);
		//P.1記録
		JPanel mainPanel = new JPanel(new BorderLayout());
		String[] columns = {"日付","カテゴリ","金額","内容"};
		DefaultTableModel model = new DefaultTableModel(columns,0);
		CSVdata csvData = new CSVdata("data/user.csv");
		
		//データ取得
		for(Map<String,String> row  : csvData.findAll()) {
			model.addRow(new Object[]{
					row.get("date"),
					row.get("category"),
					row.get("amount"),
					row.get("memo")
					
			});
		}
		JTable table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		mainPanel.add(scroll,BorderLayout.CENTER);
		//ボタン(add=追加,search=検索,total=合計,delete=削除)
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(4,1));
		
		JButton addButton = new JButton("追加");
		addButton.setBackground(Color.PINK);
		
		JButton searchButton = new JButton("検索");
		searchButton.setBackground(Color.PINK);
		
		JButton totalButton = new JButton("合計");
		totalButton.setBackground(Color.PINK);
		
		JButton deleteButton = new JButton("削除");
		deleteButton.setBackground(Color.PINK);
		
		JLabel totalLabel = new JLabel("合計：0");
		
		leftPanel.add(addButton);
		leftPanel.add(searchButton);
		leftPanel.add(totalButton);
		leftPanel.add(deleteButton);
		
		mainPanel.add(leftPanel,BorderLayout.WEST);
		mainPanel.add(totalLabel,BorderLayout.SOUTH);
		//P.2追加
		JPanel addPanel = new JPanel();
		addPanel.setLayout(new GridLayout(6,2));
		
		JLabel dateLabel = new JLabel("日付");
		JTextField  dateField = new JTextField();
		
		JLabel categoriesLabel = new JLabel("カテゴリ");
		String[] categories = {"食費","交通費","娯楽","生活"};
		JComboBox<String> categoriesbox = new JComboBox<>(categories);
		
		JLabel moneyLabel = new JLabel("金額");
		JTextField moneyField = new JTextField();
		
		JLabel contentLabel = new JLabel("内容");
		JTextField contentField = new JTextField();
		
		JButton confirmButton = new JButton("確定");
		JButton backButton = new JButton("戻る");
		
		addPanel.add(dateLabel);
		addPanel.add(dateField);
		
		addPanel.add(categoriesLabel);
		addPanel.add(categoriesbox);
		
		addPanel.add(moneyLabel);
		addPanel.add(moneyField);
		
		addPanel.add(contentLabel);
		addPanel.add(contentField);
		
		addPanel.add(confirmButton);
		addPanel.add(backButton);
		
		root.add(mainPanel,"MAIN");
		root.add(addPanel,"ADD");
		
		//データ輸入
		addButton.addActionListener(e->{card.show(root,"ADD");});
		backButton.addActionListener(e->{card.show(root,"MAIN");});
		
		confirmButton.addActionListener(e -> {

            String date = dateField.getText();

            String category =
                    categoriesbox.getSelectedItem().toString();

            String money = moneyField.getText();
            
            try {

                Integer.parseInt(money);

            } catch(NumberFormatException ex) {

            	JOptionPane.showMessageDialog(
            			
        				null,
        				"数字を入れてください。"
        				
        				);

                return;
            }
            
            String content = contentField.getText();

            // table新增資料
            model.addRow(new Object[]{
                    date,
                    category,
                    money,
                    content
            });

            
            
            try {
				Map<String, String> newData = new LinkedHashMap<>();	//記録用変数
				
				newData.put("date", dateField.getText());
				newData.put("category", categoriesbox.getSelectedItem().toString());
				newData.put("amount", moneyField.getText());
				newData.put("memo", contentField.getText());
				
				csvData.addList(newData);
				
            } catch (IOException e1) {
            		System.out.println("保存時エラー");
				e1.printStackTrace();
			} 
            
            
         // 初期化
            dateField.setText("");
            moneyField.setText("");
            contentField.setText("");

            // 記録に戻る
            card.show(root, "MAIN");

        });
		
		//削除
		deleteButton.addActionListener(e -> {
		
			int row = table.getSelectedRow();
			
			if(row !=1) {
				
				int result =
						
						JOptionPane.showConfirmDialog(
								null,
								"削除しますか？",
								"確認",
								JOptionPane.YES_NO_OPTION
								);
				if(result == JOptionPane.YES_OPTION) {
					
					model.removeRow(row);
				}
				}				
		});
		

    
        totalButton.addActionListener(e -> {

            int total = 0;

            for (int i = 0; i < model.getRowCount(); i++) {

                String money =
                        model.getValueAt(i, 2).toString();
                try {
                	
                total += Integer.parseInt(money);
                
                }catch(NumberFormatException ex) {
                	
                		JOptionPane.showMessageDialog(
                				null,
                				"数字を入れてください。"
                				);
                		
                }
            }

            totalLabel.setText("合計：" + total+"円");

        });

        setLayout(new BorderLayout());
        add(root, BorderLayout.CENTER);
}
}
