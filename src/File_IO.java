import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class File_IO {
	private GUI win;
	
	ArrayList<String[]> file_read(String file_name) {

		String ret=null;
		ArrayList<String[]> tlist= new ArrayList<String[]>();
		try {
			File where = new File(file_name);
			BufferedReader br =Files.newBufferedReader(Paths.get(file_name),Charset.forName("UTF-8"));
			String line = "";
			int i=0;
			while ((line = br.readLine()) != null) {
				String[] token = line.split(",");
				tlist.add(token);
				i++;
			}
			br.close();
		}catch(Exception e) {
			e.getStackTrace();
		}
		
		return tlist;
	}

	void file_write(String[] sample, String file_name) {
		
		ArrayList<String[]> originData=null;
		originData = file_read(file_name);
		int flag_row_index=0;
		try {
			File file = new File(file_name);
			BufferedWriter fw=Files.newBufferedWriter(Paths.get(file_name),Charset.forName("UTF-8"));

			for (String[] newLine: originData) {
				String[] list = newLine;
				int flag_col_index =0;
				
				for(String data : list) {
					fw.write(data);
					
					if(file_name.equals("reservation.csv") &&flag_col_index != 10) //reservation 老嫐 10 train 老嫐 8捞备唱
						fw.write(",");
					
					if(file_name.equals("train.csv") &&flag_col_index != 8) //reservation 老嫐 10 train 老嫐 8捞备唱
						fw.write(",");
					
					flag_col_index++;
				}
				fw.newLine();
				flag_row_index++;
			}
			fw.write(sample[0]);
			fw.write("\n");
			fw.close();
		}catch(Exception e){
			e.getStackTrace();
		}

	}
	
	void file_point_replace(String file_name,int point_index_row,int point_index_col,String replace_value) {
		ArrayList<String[]> originData=null;
		originData = file_read(file_name);

		System.out.println(point_index_row+"/"+point_index_col+"/"+replace_value);
		int flag_row_index=0;
		try {
			File file = new File(file_name);
			BufferedWriter fw=Files.newBufferedWriter(Paths.get(file_name),Charset.forName("UTF-8"));

			for (String[] newLine: originData) {
				String[] list = newLine;
				int flag_col_index =0;
				
				for(String data : list) {
					if(flag_row_index == point_index_row &&flag_col_index == point_index_col) {
						fw.write(replace_value);
						if(file_name.equals("reservation.csv") &&flag_col_index != 10) 
							fw.write(",");
						
						if(file_name.equals("train.csv") &&flag_col_index != 8)
							fw.write(",");
					}else {
						fw.write(data);
						if(file_name.equals("reservation.csv") &&flag_col_index != 10) 
							fw.write(",");
						
						if(file_name.equals("train.csv") &&flag_col_index != 8) 
							fw.write(",");
					}
					flag_col_index++;
				}
				
				fw.newLine();
				flag_row_index++;
			}


			fw.close();
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	void file_delete_reservation(String file_name,int point_index) {

		ArrayList<String[]> originData=null;
		originData = file_read(file_name);
		int flag_row_index=0;
		try {
			File file = new File("reservation.csv");
			BufferedWriter fw=Files.newBufferedWriter(Paths.get("reservation.csv"),Charset.forName("UTF-8"));

			for (String[] newLine: originData) {
				String[] list = newLine;
				int flag_col_index =0;
				
				for(String data : list) {
					if(flag_row_index == point_index ) {
						
					}else {
						fw.write(data);
					}
					if(flag_col_index != 10 && flag_row_index !=point_index)
					fw.write(",");
					
					flag_col_index++;
				}if(flag_row_index == point_index ) {
					
				}else {
				fw.newLine();
				}
				flag_row_index++;
			}
			fw.close();
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	


	ArrayList<String[]> readById(String file_name, String ID){
		ArrayList<String[]> ret= new ArrayList<String[]>();
		ArrayList<String[]> originData= new ArrayList<String[]>();
		originData= file_read(file_name);
		for(int i=0; i<originData.size(); i++) {
			if(originData.get(i)[9].equals(ID)) {
				ret.add(originData.get(i));
			}
		}
		return ret;
	}
}