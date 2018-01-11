package com.dv.test;

import com.converter.docConverter.DocConverter;

public class TestJacob {
	public static void main(String[]args){
//	    long c1 = System.currentTimeMillis();
		DocConverter converter = new DocConverter();
		String docxFile = "C:\\work\\test\\input\\无锡市政务信息平台服务外包(实施计划).docx";
		String docFile1 = "C:\\work\\test\\input\\DCS.doc";
		String docFile2 = "C:\\work\\test\\input\\给女儿的一封信.doc";
		String docFile3 = "C:\\work\\test\\input\\文档转换产品说明书.doc";
		String docFile4 = "C:\\work\\test\\input\\竞业禁止协议和保密协议.doc";
		String docFile5 = "C:\\work\\test\\input\\劳动合同.doc";
		String xlsFile1 = "C:\\work\\test\\input\\2016.xlsx";
		String xlsFile2 = "C:\\work\\test\\input\\2017.xlsx";
		String pptFile = "C:\\work\\test\\input\\DingtalkManual.pptx";
//		String htmlFile = "C:\\work\\test\\output\\无锡市政务信息平台服务外包(实施计划).html";
		converter.convert(docxFile, "C:\\work\\test\\output", 1);
		converter.convert(docFile1, "C:\\work\\test\\output", 1);
		converter.convert(docFile2, "C:\\work\\test\\output", 1);
		converter.convert(docFile3, "C:\\work\\test\\output", 1);
		converter.convert(docFile4, "C:\\work\\test\\output", 1);
        converter.convert(docFile5, "C:\\work\\test\\output", 1);
		converter.convert(xlsFile1, "C:\\work\\test\\output", 1);
		converter.convert(xlsFile2, "C:\\work\\test\\output", 1);
		converter.convert(pptFile, "C:\\work\\test\\output", 1);
//		converter.convert(htmlFile, "C:\\work\\test\\output", 2);
//		
		String[] sourceFiles = new String[]{"C:\\work\\test\\input\\大瓦在线文档转换系统建设方案v0.1.doc","C:\\work\\test\\input\\DCS.doc",
		    "C:\\work\\test\\input\\给女儿的一封信.doc","C:\\work\\test\\input\\文档转换产品说明书.doc"
		    ,"C:\\work\\test\\input\\竞业禁止协议和保密协议.doc"};
		converter.mergePDF(sourceFiles, "C:\\work\\test\\output\\merge.pdf", null);
//		long c2 = System.currentTimeMillis();
//		System.out.println("times:"+(c2-c1)/1000);
		//new TestJacob().createBookmarks("C:\\work\\test\\output\\无锡市政务信息平台服务外包(实施计划).pdf");
	}

}
