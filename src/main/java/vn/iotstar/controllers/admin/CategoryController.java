package vn.iotstar.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.impl.CategoryService;
import vn.iotstar.utils.Constant;

@MultipartConfig(
		fileSizeThreshold = 1024 * 1024, // 1 MB
	    maxFileSize = 1024 * 1024 * 5,  // 10 MB
	    maxRequestSize = 1024 * 1024 * 5 * 5 // 50 MB
	    )
@WebServlet(urlPatterns = {"/admin/categories" , "/admin/category/add" , "/admin/category/insert" , "/admin/category/edit" 
		, "/admin/category/update" , "/admin/category/delete" , "/admin/category/search"})
public class CategoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public ICategoryService cateService = new CategoryService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String url=req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		if(url.contains("categories")) {
			List<Category> list = cateService.findAll();
			req.setAttribute("listcate", list);
			req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
		}else if(url.contains("add")) {
			req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);
		}else if(url.contains("edit")) {
			int id = Integer.parseInt(req.getParameter("id"));					
			Category category = cateService.findById(id);
			req.setAttribute("cate", category);	
			
			req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);
		}else if(url.contains("delete")) {
			try {
	            String id = req.getParameter("id");
	            cateService.delete(Integer.parseInt(id));  // Ném ngoại lệ
	            resp.sendRedirect(req.getContextPath() + "/admin/categories");
	        } catch (Exception e) {
	            throw new ServletException(e);  // Ném lại ngoại lệ dưới dạng ServletException
	        }
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url=req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		if(url.contains("insert")) {
			Category category = new Category();
			String categoryname = req.getParameter("categoryname");
			String status = req.getParameter("status");
			int statuss = Integer.parseInt(status);
			
			category.setCategoryname (categoryname); 
			category.setStatus(statuss);
			
			String fname = "";
			String uploadPath = Constant.UPLOAD_DIRECTORY;

			File uploadDir = new File(uploadPath); 
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("images");
				if(part.getSize()>0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString()	;
					//đổi tên file
					int index = filename.lastIndexOf("."); 
					String ext = filename.substring(index+1); 
					fname = System.currentTimeMillis() + ext;
					//up load file
					part.write(uploadPath + "/" + fname);
					//ghi tên file vào data
					category.setImages(fname);
				}else{
					category.setImages("avatar.png");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			cateService.insert(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}else if(url.contains("update")) {
			int categoryid = Integer.parseInt(req.getParameter("categoryid")); 	
			String categoryname = req.getParameter("categoryname");
			String status = req.getParameter("status");
			int statuss = Integer.parseInt(status);	
			Category category = new Category();
			category.setCategoryId(categoryid);
			category.setCategoryname (categoryname); 
			category.setStatus(statuss);
			//lưu hình cũ
			Category cateold = cateService.findById(categoryid);
			String fileold = cateold.getImages();
			//xử lí image
			String fname = "";
			String uploadPath = Constant.UPLOAD_DIRECTORY;
			File uploadDir = new File(uploadPath); 
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("images");
				if(part.getSize()>0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString()	;
					//đổi tên file
					int index = filename.lastIndexOf("."); 
					String ext = filename.substring(index+1); 
					fname = System.currentTimeMillis() + ext;
					//up load file
					part.write(uploadPath + "/" + fname);
					//ghi tên file vào data
					category.setImages(fname);
				}else{
					category.setImages(fileold);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			cateService.update(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}
	}
}
