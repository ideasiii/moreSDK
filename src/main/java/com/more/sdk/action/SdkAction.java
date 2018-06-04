package com.more.sdk.action;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.more.sdk.entity.Sdk;
import com.more.sdk.entity.SdkGroupDefinition;
import com.more.sdk.entity.tool.Constant;
import com.more.sdk.service.SdkService;

@Component
@Scope("prototype")
@Namespace("/function")
@ParentPackage(value = "super-default")
public class SdkAction extends RootAction {

	private static final long serialVersionUID = -2368389567220756001L;
	private List<Sdk> sdkList; 
	private int groupId;
	private SdkGroupDefinition sdkGroupDefinition;
	private int sdkId;
	private String type;
	
	@Resource(name="sdkService")
	private SdkService sdkService;
	private InputStream inputStream;
	private String fileName;
	
	/**
	 * SDK頁面
	 * 
	 * @return
	 */
	@Action(value = "sdk", results = { @Result(name = "success", type = "tiles", location = "function.sdk") })
	public String sdk() {
		
		sdkGroupDefinition = Constant.getInstance().getSdkGroupDefinitionMap().get(groupId);
		if(sdkGroupDefinition == null){
			throw new RuntimeException("查詢無此類別");
		}
		sdkList = sdkService.getSdkByGroup(groupId);
		setFunctionName(sdkGroupDefinition.getCategory());
		return SUCCESS;
	}

	@Action(value = "download", results = { @Result(name = "success", type = "stream", params = {"contentType", "application/octet-stream", "inputName", "inputStream", "contentDisposition","attachment;filename=${fileName}","bufferSize","1024"}  )})
	public String download() {
		Sdk sdk = sdkService.getSdk(sdkId);
		if(sdk == null){
			throw new RuntimeException("查詢無此SDK");
		}else{
			if("sdk".equals(type)){
				inputStream = ServletActionContext.getServletContext().getResourceAsStream(sdk.getSdkFile());
				String[] tmpData = sdk.getSdkFile().split("/");
				fileName = tmpData[tmpData.length-1];
			}else{
				inputStream = ServletActionContext.getServletContext().getResourceAsStream(sdk.getSdkDoc());
				String[] tmpData = sdk.getSdkDoc().split("/");
				fileName = tmpData[tmpData.length-1];
			}
			
			
		}
		return SUCCESS;
	}
	
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}


	public List<Sdk> getSdkList() {
		return sdkList;
	}


	public void setSdkList(List<Sdk> sdkList) {
		this.sdkList = sdkList;
	}


	public SdkGroupDefinition getSdkGroupDefinition() {
		return sdkGroupDefinition;
	}


	public void setSdkGroupDefinition(SdkGroupDefinition sdkGroupDefinition) {
		this.sdkGroupDefinition = sdkGroupDefinition;
	}

	public int getSdkId() {
		return sdkId;
	}

	public void setSdkId(int sdkId) {
		this.sdkId = sdkId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
