<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<form action="${pageContext.request.contextPath}/admin/category/insert" method="post" enctype="multipart/form-data">
    <label for="categoryname">Category name:</label><br>
    <input type="text" id="categoryname" name="categoryname"><br>

    <label for="images">Images:</label><br>
    <img id="imagePreview" height="150" width="200" src="" alt="Preview"/>
    <input type="file" onchange="chooseFile(this)" id="images" name="images"><br>

    <label for="status">Status:</label><br>
    <input type="text" id="status" name="status"><br>
    
    <br>
    <input type="submit" value="Submit">
</form>