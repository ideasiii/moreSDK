package com.more.sdk.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CsvUtils {
	public static byte[] exportCSV(List<String> headers, List<Object> exportData) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedWriter buffCvsWriter = null;
		try {
			buffCvsWriter = new BufferedWriter(new OutputStreamWriter(baos, "utf-8"));
			
			if(headers != null && headers.size() > 0){
				for (int i = 0; i < headers.size(); i++) {
					String header = headers.get(i);
					buffCvsWriter.write("\"" + header + "\"");
					if (i+1 < headers.size()) {
						buffCvsWriter.write(",");
					}
				}
				buffCvsWriter.newLine();
				if(exportData != null && exportData.size() > 0){
					Gson gson = new Gson();
					for(Object tmp : exportData){
						JsonObject data = gson.fromJson(gson.toJson(tmp),JsonObject.class);
						for (int i = 0; i < headers.size(); i++) {
							String header = headers.get(i);
							JsonElement tmpData = data.get(header);
							String value = "";
							if(tmpData != null){
								value = tmpData.getAsString();
							}
							buffCvsWriter.write("\"" + value + "\"");
							if (i+1 < headers.size()) {
								buffCvsWriter.write(",");
							}
						}
						buffCvsWriter.newLine();
					}
					buffCvsWriter.flush();
				}
			}else {
				return new byte[]{};
			}
			
			
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// 釋放資源
			if (buffCvsWriter != null) {
				try {
					buffCvsWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos.toByteArray();
	}
}
