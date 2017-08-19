package com.fly.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.Test;

import com.fly.domain.Good;
import com.fly.domain.Hot;
import com.fly.domain.PageBean;
import com.fly.service.GoodService;
import com.fly.service.Impl.GoodServiceImpl;
import com.fly.utils.DirUtils;
import com.fly.utils.UUIDUtils;
import com.google.gson.Gson;

import flexjson.JSONSerializer;
import redis.clients.jedis.Jedis;

@SuppressWarnings("all")
public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private GoodService goodService = new GoodServiceImpl();

	public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Good> list = goodService.findAll();
		Gson gson = new Gson();
		String listStr = gson.toJson(list).toString();
		System.out.println(listStr);
		response.getWriter().write(listStr);
	}

	public void pageGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Integer page = Integer.parseInt(request.getParameter("page"));
		
		PageBean p = goodService.findGoodByPage(rows, page);
		
		Map map = new HashMap();
		map.put("total", p.getTotalPage());
		map.put("rows", p.getList());
		String serialize = new JSONSerializer().include("rows").serialize(map);
		response.getWriter().write(serialize);
		//获取请求参数（page  rows）
//				//用户想要查询的页码
//				int page = Integer.parseInt(request.getParameter("page"));
//				//一页显示多少行数据
//				int rows = Integer.parseInt(request.getParameter("rows"));
//				GoodService goodService = new GoodServiceImpl();
//				PageBean p = goodService.findGoodByPage(rows, page);
//				//当前总数
//				int total = p.getTotalPage();
//				//需要分页数据
//				List<Good> data = p.getList();
//				
//				//将数据转换成json格式
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("total", total);
//				map.put("rows", data);
//				
//				String serialize = new JSONSerializer().include("rows").serialize(map);
//				System.out.println(serialize);
//				//发出响应
//				response.getWriter().write(serialize);
	}
	
	public void addGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文件数据处理
		DiskFileItemFactory factory = new DiskFileItemFactory();

		ServletFileUpload upload = new ServletFileUpload(factory);
		Map map = new HashMap<String, String>();
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				//将FileItem中的数据保存到服务器硬盘

			    if (item.isFormField()) { //是普通文本框
			    	String name = item.getFieldName();
			    	String value = item.getString("utf-8"); // 使用指定编码
			    	map.put(name, value);
			    } else { //文件上传
			    	String realPath = getServletContext().getRealPath("/");
			    	realPath = realPath.replace("\\webapps\\Estore_raw", "");
			    	realPath += "picture";
			    	//获取文件名
			    	String fileName = item.getName();
			    	fileName = UUIDUtils.getUUID() + fileName;
			    	//根据文件名目录打散
			    	String dir = DirUtils.getDir(fileName);
			    	new File(realPath, dir).mkdirs();
			    	File uploadedFile = new File(realPath + dir, fileName);
			    	try {
						item.write(uploadedFile);
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException("文件上传失败");
					}
			    	map.put("imgurl", "/picture" + dir + "/" + fileName);
			    }
			}
			
		} catch (FileUploadException e) {
			e.printStackTrace();
			throw new RuntimeException("文件上传失败");
		}
		
		// 添加数据
		Good good = new Good();
		try {
			BeanUtils.populate(good, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(good);
		GoodService goodService = new GoodServiceImpl();
		goodService.add(good);
		response.sendRedirect(request.getContextPath() + "/admin_index.jsp");
	}
	
	public void picture(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imgurl = request.getParameter("imgurl");
		String realPath = getServletContext().getRealPath("/"); // 获取服务器项目所在的路径
		realPath = realPath.replace("\\webapps\\Estore_raw", "");
		File file = new File(realPath, imgurl);
		FileInputStream in = new FileInputStream(file);
		ServletOutputStream os = response.getOutputStream();
		byte[] bys = new byte[8192];
		int len = 0;
		while((len = in.read(bys)) != -1){
			os.write(bys, 0, len);
		}
	}
	
	public void getHot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Jedis jedis = new Jedis("192.168.48.109", 6379);
		String hot = jedis.get("hot");
		if (hot == null) {
			GoodService goodService = new GoodServiceImpl();
			List<Hot> hotList = goodService.getHot();
			System.out.println(hotList);
			Gson gson = new Gson();
		    hot = gson.toJson(hotList).toString();
			jedis.set("hot", hot);
			jedis.expire("hot", 20);
		}
		response.getWriter().write(hot);
		
	}
}
