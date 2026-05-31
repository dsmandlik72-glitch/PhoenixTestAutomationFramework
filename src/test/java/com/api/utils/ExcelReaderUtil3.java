package com.api.utils;

import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;
import com.poiji.bind.Poiji;

public class ExcelReaderUtil3 {

	public static void main(String[] args) {
		Iterator<CreateJobBean> iterator = ExcelReaderUtil2.loadTestData("testData/PhoenixTestData.xlsx",
				"CreateJobTestData", CreateJobBean.class);

		while (iterator.hasNext()) {
			CreateJobBean bean = iterator.next();
			CreateJobPayload createJobPayload = CreateJobBeanMapper.mapper(bean);
			System.out.println(createJobPayload);
		}
	}
}
